package com.lgyun.system.user.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.constant.RealnameVerifyConstant;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.*;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.*;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.excel.MakerReadListener;
import com.lgyun.system.user.mapper.MakerMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class MakerServiceImpl extends BaseServiceImpl<MakerMapper, MakerEntity> implements IMakerService {

    private SmsUtil smsUtil;
    private RedisUtil redisUtil;
    private AliyunOssService aliyunOssService;
    private IAgreementService agreementService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private IOnlineAgreementTemplateService onlineAgreementTemplateService;

    @Override
    public int queryCountById(Long makerId) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getId, makerId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        if (!(UserType.MAKER.equals(bladeUser.getUserType()))) {
            return R.fail("用户类型有误");
        }

        MakerEntity makerEntity = getById(bladeUser.getUserId());
        if (makerEntity == null) {
            return R.fail("创客不存在");
        }

        if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(makerEntity);
    }

    @Override
    public R<BaseInfoVO> queryMakerInfo(Long makerId) {
        return R.data(baseMapper.queryMakerInfo(makerId));
    }

    @Override
    public R<MakerDetailVO> queryCurrentMakerDetail(Long makerId) {
        return R.data(baseMapper.queryCurrentMakerDetail(makerId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateMakerDetail(UpdateMakerDeatilDTO updateMakerDeatilDTO, MakerEntity makerEntity) {

        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus())) {
            updateMakerDeatilDTO.setName(makerEntity.getName());
            updateMakerDeatilDTO.setIdcardNo(makerEntity.getIdcardNo());
        } else {
            if (StringUtils.isNotBlank(updateMakerDeatilDTO.getIdcardNo()) && !(updateMakerDeatilDTO.getIdcardNo().equals(makerEntity.getIdcardNo()))) {
                int idcardNoMakerNum = count(Wrappers.<MakerEntity>query().lambda().eq(MakerEntity::getIdcardNo, updateMakerDeatilDTO.getIdcardNo()));
                if (idcardNoMakerNum > 0) {
                    return R.fail("身份证号码已存在");
                }
            }
        }

        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getBankCardVerifyStatus())) {
            updateMakerDeatilDTO.setBankCardNo(makerEntity.getBankCardNo());
        }

        BeanUtils.copyProperties(updateMakerDeatilDTO, makerEntity);
        updateById(makerEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updatePhoneNumber(UpdatePhoneNumberDTO updatePhoneNumberDTO, MakerEntity makerEntity) {

        if (updatePhoneNumberDTO.getMobile().equals(makerEntity.getPhoneNumber())) {
            return R.fail("该手机号与当前创客手机号一致");
        }

        //查询手机号
        String mobile = updatePhoneNumberDTO.getMobile();
        //查询缓存短信验证码
        String key = SmsConstant.AVAILABLE_TIME + mobile + "_" + UserType.MAKER + "_" + CodeType.UPDATEPHONE;
        String redisCode = (String) redisUtil.get(key);
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, updatePhoneNumberDTO.getSmsCode())) {
            return R.fail("短信验证码不正确");
        }

        makerEntity.setPhoneNumber(updatePhoneNumberDTO.getMobile());
        makerEntity.setPhoneNumberVerifyStatus(VerifyStatus.TOVERIFY);
        makerEntity.setPhoneNumberVerifyDate(null);
        updateById(makerEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MakerEntity makerSave(String openid, String sessionKey, String purePhoneNumber, String loginPwd) {
        return makerSave(openid, sessionKey, purePhoneNumber, loginPwd, "", "", "", "", "", null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MakerEntity makerSave(String purePhoneNumber, String name, String idcardNo, String bankCardNo, String bankName, String subBankName, Long enterpriseId) {
        return makerSave("", "", purePhoneNumber, "", name, idcardNo, bankCardNo, bankName, subBankName, enterpriseId);
    }

    @Transactional(rollbackFor = Exception.class)
    public MakerEntity makerSave(String openid, String sessionKey, String purePhoneNumber, String loginPwd, String name,
                                 String idcardNo, String bankCardNo, String bankName, String subBankName, Long enterpriseId) {

        MakerEntity makerEntity;
        MakerEntity makerEntityPhoneNumber = findByPhoneNumber(purePhoneNumber);
        MakerEntity makerEntityIdcardNo = findByIdcardNo(idcardNo);
        if (makerEntityPhoneNumber == null && makerEntityIdcardNo == null) {

            //新建创客
            makerEntity = new MakerEntity();
            //微信登陆新建创客
            if (StringUtils.isNotBlank(openid) && StringUtils.isNotBlank(sessionKey)) {
                makerEntity.setOpenid(openid);
                makerEntity.setSessionKey(sessionKey);
                makerEntity.setRelDate(new Date());
            }

            makerEntity.setPhoneNumber(purePhoneNumber);
            if (StringUtil.isNotBlank(loginPwd)) {
                makerEntity.setLoginPwd(loginPwd);
            } else {
                makerEntity.setLoginPwd(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
            }
            makerEntity.setName(name);
            makerEntity.setIdcardNo(idcardNo);
            makerEntity.setBankCardNo(bankCardNo);
            makerEntity.setBankName(bankName);
            makerEntity.setSubBankName(subBankName);
            save(makerEntity);

            //新建创客添加加盟合同需要签署的模板
            OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = onlineAgreementTemplateService.findTemplateType(AgreementType.MAKERJOINAGREEMENT, true);
            if (onlineAgreementTemplateEntity != null) {
                onlineAgreementNeedSignService.OnlineAgreementNeedSignAdd(onlineAgreementTemplateEntity.getId(), ObjectType.MAKERPEOPLE, SignPower.PARTYB, makerEntity.getId());
            }

            //新建创客授权书需要签署的模板
            onlineAgreementTemplateEntity = onlineAgreementTemplateService.findTemplateType(AgreementType.MAKERPOWERATTORNEY, true);
            if (onlineAgreementTemplateEntity != null) {
                onlineAgreementNeedSignService.OnlineAgreementNeedSignAdd(onlineAgreementTemplateEntity.getId(), ObjectType.MAKERPEOPLE, SignPower.PARTYB, makerEntity.getId());
            }

        } else if (makerEntityPhoneNumber != null && makerEntityIdcardNo == null) {
            if (!(VerifyStatus.VERIFYPASS.equals(makerEntityPhoneNumber.getIdcardVerifyStatus()))) {
                makerEntityPhoneNumber.setName(name);
                makerEntityPhoneNumber.setIdcardNo(idcardNo);
                makerEntityPhoneNumber.setBankCardNo(bankCardNo);
                makerEntityPhoneNumber.setBankName(bankName);
                makerEntityPhoneNumber.setSubBankName(subBankName);
                updateById(makerEntityPhoneNumber);
            } else {
                if (!(VerifyStatus.VERIFYPASS.equals(makerEntityPhoneNumber.getBankCardVerifyStatus()))) {
                    makerEntityPhoneNumber.setBankCardNo(bankCardNo);
                    makerEntityPhoneNumber.setBankName(bankName);
                    makerEntityPhoneNumber.setSubBankName(subBankName);
                    updateById(makerEntityPhoneNumber);
                }
            }

            makerEntity = makerEntityPhoneNumber;
        } else {

            if (!(VerifyStatus.VERIFYPASS.equals(makerEntityIdcardNo.getIdcardVerifyStatus()))) {
                makerEntityIdcardNo.setName(name);
                makerEntityIdcardNo.setIdcardNo(idcardNo);
                makerEntityIdcardNo.setBankCardNo(bankCardNo);
                makerEntityIdcardNo.setBankName(bankName);
                makerEntityIdcardNo.setSubBankName(subBankName);
                updateById(makerEntityIdcardNo);
            } else {
                if (!(VerifyStatus.VERIFYPASS.equals(makerEntityIdcardNo.getBankCardVerifyStatus()))) {
                    makerEntityIdcardNo.setBankCardNo(bankCardNo);
                    makerEntityIdcardNo.setBankName(bankName);
                    makerEntityIdcardNo.setSubBankName(subBankName);
                    updateById(makerEntityIdcardNo);
                }
            }

            makerEntity = makerEntityIdcardNo;
        }

        if (enterpriseId != null) {
            //商户-创客关联
            makerEnterpriseService.makerEnterpriseEntitySave(enterpriseId, makerEntity.getId());
        }

        return makerEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void makerUpdate(MakerEntity makerEntity, String openid, String sessionKey) {
        //更新微信信息
        makerEntity.setOpenid(openid);
        makerEntity.setSessionKey(sessionKey);
        updateById(makerEntity);
    }

    @Override
    public MakerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int findCountByPhoneNumber(String phoneNumber) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public MakerEntity findByAccountAndPwd(String account, String loginPwd) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getPhoneNumber, account)
                .eq(MakerEntity::getLoginPwd, loginPwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IdcardOcrVO> queryIdcardOcr(MakerEntity makerEntity) {

        //查看创客是否已经身份证认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("未进行身份证认证");
        }

        IdcardOcrVO idcardOcrVO = new IdcardOcrVO();
        idcardOcrVO.setIdcardPic(makerEntity.getIdcardPic());
        idcardOcrVO.setIdcardPicBack(makerEntity.getIdcardPicBack());
        idcardOcrVO.setName(makerEntity.getName());
        idcardOcrVO.setIdNo(makerEntity.getIdcardNo());
        idcardOcrVO.setIdcardHand(makerEntity.getIdcardHand());
        idcardOcrVO.setIdcardBackHand(makerEntity.getIdcardBackHand());

        return R.data(idcardOcrVO);

    }

    @Override
    public R<JSONObject> idcardOcr(String idcardPic, MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus())) {
            return R.fail("身份证已认证");
        }

        //身份证信息获取
        R<JSONObject> result = RealnameVerifyUtil.idcardOCR(idcardPic);
        log.info("身份证信息获取请求返回参数：{}", result);
        if (!(result.isSuccess())) {
            return result;
        }

        JSONObject jsonObject = result.getData();
        if (StringUtils.isBlank(jsonObject.getString("name")) || StringUtils.isBlank(jsonObject.getString("idNo"))) {
            return R.fail("身份证识别信息缺失");
        }

        //查询姓名和身份证号码
        JSONObject returnResult = new JSONObject();
        returnResult.put("name", jsonObject.getString("name"));
        returnResult.put("idNo", jsonObject.getString("idNo"));

        return R.data(returnResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R idcardVerify(IdcardVerifyDTO idcardVerifyDTO, MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus())) {
            return R.fail("身份证已认证");
        }

        //身份证信息获取
        R<JSONObject> OcrResult = RealnameVerifyUtil.idcardOCR(idcardVerifyDTO.getIdcardPic());
        log.info("身份证识别信息请求返回参数：{}", OcrResult);
        if (!(OcrResult.isSuccess())) {
            return OcrResult;
        }

        JSONObject jsonObject = OcrResult.getData();
        if (StringUtils.isBlank(jsonObject.getString("name")) || StringUtils.isBlank(jsonObject.getString("idNo"))) {
            return R.fail("身份证识别信息缺失");
        }

        //身份证号码
        String idNo = jsonObject.getString("idNo");
        //真实姓名
        String name = jsonObject.getString("name");

        //查询身份证号码是否已被使用
        MakerEntity makerEntityIdcardNo = findByIdcardNo(idNo);
        if (makerEntityIdcardNo != null && !(makerEntityIdcardNo.getId().equals(makerEntity.getId()))) {
            return R.fail("身份证号码已被使用");
        }

        //身份证认证
        R<JSONObject> verifyResult = RealnameVerifyUtil.idcardVerify(idNo, name);
        log.info("身份证信息获取请求返回参数：{}", verifyResult);
        if (!(verifyResult.isSuccess())) {
            return verifyResult;
        }

        makerEntity.setIdcardNo(idNo);
        makerEntity.setName(name);
        BeanUtils.copyProperties(idcardVerifyDTO, makerEntity);
        makerEntity.setIdcardVerifyStatus(VerifyStatus.VERIFYPASS);
        makerEntity.setIdcardVerifyType(IdcardVerifyType.SYSTEMVERIFY);
        makerEntity.setIdcardVerifyDate(new Date());
        updateById(makerEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R mobileVerify(MakerEntity makerEntity) throws Exception {

        //查看创客是否已经手机号认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus())) {
            return R.fail("手机号已认证");
        }

        //查看创客是否已经身份证认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证认证");
        }

        R<JSONObject> result = RealnameVerifyUtil.mobileVerify(makerEntity.getIdcardNo(), makerEntity.getName(), makerEntity.getPhoneNumber());
        log.info("手机号认证请求返回参数：{}", result);
        if (!(result.isSuccess())) {
            return result;
        }

        makerEntity.setPhoneNumberVerifyStatus(VerifyStatus.VERIFYPASS);
        makerEntity.setPhoneNumberVerifyDate(new Date());
        updateById(makerEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R bankCardVerify(String bankCardNo, MakerEntity makerEntity) throws Exception {

        //查看创客是否已经活体认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getBankCardVerifyStatus())) {
            return R.fail("银行卡已认证");
        }

        //查看创客是否已经身份证认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证认证");
        }

        R<JSONObject> result = RealnameVerifyUtil.bankcardVerify(makerEntity.getIdcardNo(), makerEntity.getName(), bankCardNo);
        log.info("银行卡认证请求返回参数：{}", result);
        if (!(result.isSuccess())) {
            return result;
        }

        makerEntity.setBankCardNo(bankCardNo);
        makerEntity.setBankCardVerifyStatus(VerifyStatus.VERIFYPASS);
        makerEntity.setBankCardVerifyDate(new Date());
        updateById(makerEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R faceOcr(MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证认证");
        }

        //查看创客是否已经活体认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
            return R.fail("已活体认证");
        }

        R<JSONObject> result = RealnameVerifyUtil.faceOCR(String.valueOf(makerEntity.getId()), makerEntity.getName(), makerEntity.getIdcardNo());
        log.info("活体认证请求返回参数：{}", result);
        if (!(result.isSuccess())) {
            return result;
        }

        //通过短信发送活体认证URL
        JSONObject jsonObject = result.getData();
        String shortLink = jsonObject.getString("shortLink");
        R<String> smsResult = smsUtil.sendLink(makerEntity.getPhoneNumber(), shortLink, UserType.MAKER, MessageType.FACEOCRLINK);
        if (!(smsResult.isSuccess())) {
            return result;
        }

        return R.success("短信已发送，请及时处理");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R faceOcrNotify(HttpServletRequest request) {

        try {
            //查询body的数据进行验签
            String rbody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            boolean res = RealnameVerifyUtil.checkPass(request, rbody, RealnameVerifyConstant.APPKEY);
            if (!res) {
                return R.fail("验签失败");
            }

            // 业务逻辑处理 ****************************
            //回调参数转json
            JSONObject jsonObject = JSONObject.parseObject(rbody);
            log.info("活体认证异步通知回调参数：{}", jsonObject);
            boolean boolSuccess = jsonObject.getBooleanValue("success");
            if (!boolSuccess) {
                return R.fail("活体认证失败");
            }

            Long makerId = jsonObject.getLong("contextId");
            MakerEntity makerEntity = getById(makerId);
            if (makerEntity == null) {
                log.info("创客不存在");
                return R.fail("活体认证回调处理失败");
            }

            //查看创客是否已经活体认证
            if (VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
                return R.fail("已活体认证");
            }

            //查询认证信息
            R<JSONObject> detailResult = RealnameVerifyUtil.detail(jsonObject.getString("flowId"));
            log.info("查询认证信息请求返回参数：{}", detailResult);
            if (!(detailResult.isSuccess())) {
                return detailResult;
            }

            //查询个人信息
            JSONObject indivInfo = detailResult.getData().getJSONObject("indivInfo");
            //活体截图base64请求地址
            String facePhotoUrl = indivInfo.getString("facePhotoUrl");
            if (StringUtils.isNotBlank(facePhotoUrl)) {
                //查询活体截图base64
                String facePhotoBase64 = HttpUtil.get(facePhotoUrl);
                //上传活体截图base64到阿里云存储
                byte[] bytes = Base64Util.decodeFromString(facePhotoBase64.trim());
                String url = aliyunOssService.uploadSuffix(bytes, ".jpg");
                makerEntity.setPicVerify(url);
            }

            makerEntity.setFaceVerifyStatus(VerifyStatus.VERIFYPASS);
            makerEntity.setFaceVerifyDate(new Date());
            //判断是否已认证
            if (CertificationState.UNCERTIFIED.equals(makerEntity.getCertificationState())) {
                //判断创客是否有有效的创客加盟协议
                int makerJoinAgreementNum = agreementService.queryValidAgreementNum(null, null, ObjectType.MAKERPEOPLE, makerId, AgreementType.MAKERJOINAGREEMENT);
                //判断创客是否有有效的创客授权书
                int makerPowerAttorneyNum = agreementService.queryValidAgreementNum(null, null, ObjectType.MAKERPEOPLE, makerId, AgreementType.MAKERPOWERATTORNEY);
                if (makerJoinAgreementNum > 0 && makerPowerAttorneyNum > 0) {
                    makerEntity.setCertificationState(CertificationState.CERTIFIED);
                    makerEntity.setCertificationDate(new Date());
                }
            }
            updateById(makerEntity);

            return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);

        } catch (Exception e) {
            log.error("活体认证异步回调处理异常", e);
        }

        return R.fail("活体认证回调处理失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> uploadMakerVideo(MakerEntity makerEntity, String applyShortVideo) {

        makerEntity.setVideoAudit(VideoAudit.AUDITPASS);
        makerEntity.setApplyShortVideo(applyShortVideo);
        updateById(makerEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<MakerRealNameAuthenticationStateVO> getRealNameAuthenticationState(Long makerId) {

        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getId, makerId);

        MakerEntity makerEntity = baseMapper.selectOne(queryWrapper);
        if (makerEntity == null) {
            return R.fail("创客不存在");
        }

        MakerRealNameAuthenticationStateVO makerRealNameAuthenticationStateVO = BeanUtil.copy(makerEntity, MakerRealNameAuthenticationStateVO.class);
        return R.data(makerRealNameAuthenticationStateVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R importMaker(List<ImportMakerListDTO> importMakerListDTOList, Long enterpriseId) {

        List<ImportMakerListDTO> importMakerDTOS = new ArrayList<>();
        importMakerListDTOList.forEach(importMakerListDTO -> {
            try {
                //新建创客
                makerSave(importMakerListDTO.getPhoneNumber(), importMakerListDTO.getName(), importMakerListDTO.getIdcardNo(), importMakerListDTO.getBankCardNo(),
                        importMakerListDTO.getBankName(), importMakerListDTO.getBankCardNo(), enterpriseId);
            } catch (Exception e) {
                importMakerDTOS.add(importMakerListDTO);
                log.error("新建创客异常", e);
            }
        });

        return R.data(importMakerDTOS, "导入成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> makerAdd(MakerAddDTO makerAddDto, Long enterpriseId) {
        //新建创客
        makerSave(makerAddDto.getPhoneNumber(), makerAddDto.getName(), makerAddDto.getIdcardNo(), makerAddDto.getBankCardNo(), makerAddDto.getBankName(), makerAddDto.getBankCardNo(), enterpriseId);
        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public MakerEntity findByIdcardNo(String idcardNo) {

        if (StringUtils.isBlank(idcardNo)) {
            return null;
        }

        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getIdcardNo, idcardNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<MakerDetailWebVO> queryMakerDetail(Long makerId) {
        return R.data(baseMapper.queryMakerDetail(makerId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveAdminMakerVideo(Long makerId, String videoUrl) {
        MakerEntity makerEntity = getById(makerId);
        if (null == makerEntity) {
            return R.fail("不存在此创客");
        }
        makerEntity.setApplyShortVideo(videoUrl);
        makerEntity.setVideoAudit(VideoAudit.AUDITPASS);
        saveOrUpdate(makerEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<IPage<MakerSelectListVO>> queryMakerSelectList(String keyWord, IPage<MakerSelectListVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerSelectList(keyWord, page)));
    }

    @Override
    public R<List<MakerExcel>> readMakerListExcel(MultipartFile file) throws IOException {
        //判断文件内容是否为空
        if (file == null || file.isEmpty()) {
            return R.fail("文件为空");
        }

        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename();
        if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
            return R.fail("请选择Excel文件");
        }

        //MultipartFile转InputStream
        InputStream inputStream = file.getInputStream();

        //根据总包支付清单生成分包
        MakerReadListener makerReadListener = new MakerReadListener();
        ExcelReader excelReader = EasyExcelFactory.read(inputStream, MakerExcel.class, makerReadListener).headRowNumber(1).build();
        excelReader.readAll();
        List<MakerExcel> makerExcelList = makerReadListener.getList();
        excelReader.finish();

        return R.data(makerExcelList);
    }

    @Override
    public R<IPage<MakerListIndividualVO>> queryMakerListIndividual(Long enterpriseId, Long partnerId, MakerListIndividualDTO makerListIndividualDTO, IPage<MakerListIndividualVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerListIndividual(enterpriseId, partnerId, makerListIndividualDTO, page)));
    }

    @Override
    public R<IPage<MakerListWebVO>> queryMakerList(Long enterpriseId, Long serviceProviderId, Long relBureauId, RelationshipType relationshipType, CertificationState certificationState, String keyword, IPage<MakerListWebVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerList(enterpriseId, serviceProviderId, relBureauId, relationshipType, certificationState, keyword, page)));
    }

    @Override
    public R<IPage<MakerWorkSheetListVO>> queryWorkMakerList(Long enterpriseId, String makerName, IPage<MakerWorkSheetListVO> page) {
        return R.data(page.setRecords(baseMapper.queryWorkMakerList(enterpriseId, makerName, page)));
    }

    @Override
    public R<String> downloadDocument(Long makerId) {
        MakerEntity makerEntity = getById(makerId);
        WordExportTest test = new WordExportTest();
        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = onlineAgreementTemplateService.findTemplateType(AgreementType.OTHERAGREEMENT, true);
        String doc;
        try {
            Map map = test.testWrite(makerEntity.getName(), onlineAgreementTemplateEntity.getAgreementTemplate(), makerEntity.getIdcardNo());
            FileInputStream fileInputStream = (FileInputStream) map.get("fileInputStream");
            File file = (File) map.get("htmlFile");
            doc = aliyunOssService.uploadSuffix(fileInputStream, ".doc");
            fileInputStream.close();
            file.delete();
        } catch (Exception e) {
            return R.fail("下载失败");
        }
        return R.data(doc);
    }

}
