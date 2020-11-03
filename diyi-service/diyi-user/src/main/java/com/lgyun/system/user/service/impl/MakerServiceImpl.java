package com.lgyun.system.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.RealnameVerifyConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.*;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IdcardOcrSaveDTO;
import com.lgyun.system.user.dto.ImportMakerListDTO;
import com.lgyun.system.user.dto.MakerAddDTO;
import com.lgyun.system.user.dto.MakerListIndividualDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.excel.ExcelUtils;
import com.lgyun.system.user.excel.MakerExcel;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    private AliyunOssService ossService;
    private IUserService iUserService;
    private IMakerEnterpriseService makerEnterpriseService;
    private SmsUtil smsUtil;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private IOnlineAgreementTemplateService onlineAgreementTemplateService;

    @Override
    public int queryCountById(Long id) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getId, id);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        MakerEntity makerEntity = findByUserId(bladeUser.getUserId());
        if (makerEntity == null) {
            return R.fail("创客不存在");
        }

        if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(makerEntity);
    }

    @Override
    public R<MakerInfoVO> queryMakerInfo(Long makerId) {
        return R.data(baseMapper.queryMakerInfo(makerId));
    }

    @Override
    public R<MakerDetailVO> queryCurrentMakerDetail(Long makerId) {
        return R.data(baseMapper.queryCurrentMakerDetail(makerId));
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
            //新建管理员
            User user = new User();
            user.setUserType(UserType.MAKER);
            user.setAccount(purePhoneNumber);
            user.setPhone(purePhoneNumber);
            iUserService.save(user);

            //新建创客
            makerEntity = new MakerEntity();
            //微信登陆新建创客
            if (StringUtils.isNotBlank(openid) && StringUtils.isNotBlank(sessionKey)) {
                makerEntity.setOpenid(openid);
                makerEntity.setSessionKey(sessionKey);
                makerEntity.setRelDate(new Date());
            }

            makerEntity.setUserId(user.getId());
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
            OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = onlineAgreementTemplateService.findTemplateType(AgreementType.MAKERJOINAGREEMENT);
            if (onlineAgreementTemplateEntity != null) {
                onlineAgreementNeedSignService.OnlineAgreementNeedSignAdd(onlineAgreementTemplateEntity.getId(), ObjectType.MAKERPEOPLE, SignPower.PARTYB, makerEntity.getId());
            }

            //新建创客授权协议需要签署的模板
            onlineAgreementTemplateEntity = onlineAgreementTemplateService.findTemplateType(AgreementType.MAKERPOWERATTORNEY);
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
    public MakerEntity findByPhoneNumberAndLoginPwd(String phoneNumber, String loginPwd) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getPhoneNumber, phoneNumber)
                .eq(MakerEntity::getLoginPwd, loginPwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<JSONObject> idcardOcr(String idcardPic, MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus())) {
            return R.fail("身份证已实名认证");
        }

        //身份证实名认证
        JSONObject jsonObject = RealnameVerifyUtil.idcardOCR(idcardPic);
        if (jsonObject == null) {
            return R.fail("身份证实名认证失败");
        }

        //查询姓名和身份证号码
        String name = jsonObject.getString("name");
        String idNo = jsonObject.getString("idNo");

        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("idNo", idNo);

        return R.data(result);
    }

    @Override
    public R<String> idcardOcrSave(IdcardOcrSaveDTO idcardOcrSaveDTO, MakerEntity makerEntity) {

        //查看创客是否已经身份证实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus())) {
            return R.fail("身份证已实名认证");
        }

        //查询身份证号码是否已被使用
        MakerEntity makerEntityIdcardNo = findByIdcardNo(idcardOcrSaveDTO.getIdcardNo());
        if (makerEntityIdcardNo != null) {
            return R.fail("身份证号码已被使用");
        }

        BeanUtils.copyProperties(idcardOcrSaveDTO, makerEntity);
        makerEntity.setIdcardVerifyStatus(VerifyStatus.VERIFYPASS);
        makerEntity.setIdcardVerifyType(IdcardVerifyType.SYSTEMVERIFY);
        makerEntity.setIdcardVerifyDate(new Date());

        updateById(makerEntity);

        return R.success("身份证实名认证信息保存成功");
    }

    @Override
    public R faceOcr(MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经活体认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
            return R.fail("已活体认证");
        }

        R<JSONObject> result = RealnameVerifyUtil.faceOCR(makerEntity.getId(), makerEntity.getName(), makerEntity.getIdcardNo());
        log.info("活体认证请求返回参数", result);
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
    public R<String> faceOcrNotify(HttpServletRequest request) {

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
            log.info("活体认证异步通知回调参数", jsonObject);
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
                return R.success("已活体认证");
            }

            //查询认证信息
            JSONObject detail = RealnameVerifyUtil.detail(jsonObject.getString("flowId"));
            if (detail == null) {
                return R.fail("查询认证信息失败");
            }

            //查询个人信息
            JSONObject indivInfo = detail.getJSONObject("indivInfo");
            //人脸截图base64请求地址
            String facePhotoUrl = indivInfo.getString("facePhotoUrl");
            if (StringUtils.isNotBlank(facePhotoUrl)) {
                //查询人脸截图base64
                String facePhotoBase64 = HttpUtil.get(facePhotoUrl);
                //上传人脸截图base64到阿里云存储
                byte[] bytes = Base64Util.decodeFromString(facePhotoBase64.trim());
                String url = ossService.uploadSuffix(bytes, ".jpg");
                makerEntity.setPicVerify(url);
            }

            makerEntity.setFaceVerifyStatus(VerifyStatus.VERIFYPASS);
            makerEntity.setFaceVerifyDate(new Date());
            updateById(makerEntity);

            return R.success("活体认证成功");

        } catch (Exception e) {
            log.error("活体认证异步回调处理异常", e);
        }

        return R.fail("活体认证回调处理失败");
    }

    @Override
    public R mobileOcr(MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经活体认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行活体认证");
        }

        //查看创客是否已经手机号实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus())) {
            return R.fail("手机号已实名认证");
        }

        R<JSONObject> result = RealnameVerifyUtil.mobileOCR(makerEntity.getId(), makerEntity.getName(), makerEntity.getIdcardNo(), makerEntity.getPhoneNumber());
        log.info("手机号实名认证请求返回参数", result);
        if (!(result.isSuccess())) {
            return result;
        }

        //手机号实名认证URL
        JSONObject jsonObject = result.getData();
        String shortLink = jsonObject.getString("shortLink");

        return R.data(shortLink);
    }

    @Override
    public R<String> mobileOcrNotify(HttpServletRequest request) {

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
            log.info("手机号实名认证异步通知回调参数", jsonObject);
            boolean boolSuccess = jsonObject.getBooleanValue("success");
            if (!boolSuccess) {
                return R.fail("手机号实名认证失败");
            }

            Long makerId = jsonObject.getLong("contextId");
            MakerEntity makerEntity = getById(makerId);
            if (makerEntity == null) {
                log.info("创客不存在");
                return R.fail("手机号实名认证回调处理失败");
            }

            //查看创客手机号是否已经实名认证
            if (VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus())) {
                return R.success("手机号已实名认证");
            }

            makerEntity.setPhoneNumberVerifyStatus(VerifyStatus.VERIFYPASS);
            makerEntity.setPhoneNumberVerifyDate(new Date());
            updateById(makerEntity);

            return R.success("手机号实名认证成功");

        } catch (Exception e) {
            log.error("手机号实名认证异步回调处理异常", e);
        }

        return R.fail("手机号实名认证回调处理失败");
    }

    @Override
    public R bankCardOcr(String bankCardNo, MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经活体认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行活体认证");
        }

        //查看创客是否已经手机号实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus()))) {
            return R.fail("请先进行手机号实名认证");
        }

        //查看创客是否已经活体认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getBankCardVerifyStatus())) {
            return R.fail("银行卡已实名认证");
        }

        R<JSONObject> result = RealnameVerifyUtil.bankCardOCR(makerEntity.getId(), makerEntity.getName(), makerEntity.getIdcardNo(), bankCardNo, makerEntity.getPhoneNumber());
        log.info("银行卡实名认证请求返回参数", result);
        if (!(result.isSuccess())) {
            return result;
        }

        //银行卡实名认证URL
        JSONObject jsonObject = result.getData();
        String shortLink = jsonObject.getString("shortLink");

        return R.data(shortLink);
    }

    @Override
    public R<String> bankCardOcrNotify(HttpServletRequest request) {

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
            log.info("银行卡实名认证异步通知回调参数", jsonObject);
            boolean boolSuccess = jsonObject.getBooleanValue("success");
            if (!boolSuccess) {
                return R.fail("银行卡实名认证失败");
            }

            Long makerId = jsonObject.getLong("contextId");
            MakerEntity makerEntity = getById(makerId);
            if (makerEntity == null) {
                log.info("创客不存在");
                return R.fail("银行卡实名认证回调处理失败");
            }

            //查看创客银行卡是否已实名认证
            if (VerifyStatus.VERIFYPASS.equals(makerEntity.getBankCardVerifyStatus())) {
                return R.success("银行卡已实名认证");
            }

            //查询认证信息
            JSONObject detail = RealnameVerifyUtil.detail(jsonObject.getString("flowId"));
            if (detail == null) {
                return R.fail("查询认证信息失败");
            }

            //查询个人信息
            JSONObject indivInfo = detail.getJSONObject("indivInfo");
            //查询银行卡号
            String bankCardNo = indivInfo.getString("bankCardNo");

            makerEntity.setBankCardNo(bankCardNo);
            makerEntity.setBankCardVerifyStatus(VerifyStatus.VERIFYPASS);
            makerEntity.setBankCardVerifyDate(new Date());

            //判断是否已认证
            if (CertificationState.UNCERTIFIED.equals(makerEntity.getCertificationState()) && SignState.SIGNED.equals(makerEntity.getJoinSignState())
                    && SignState.SIGNED.equals(makerEntity.getEmpowerSignState())) {
                makerEntity.setCertificationState(CertificationState.CERTIFIED);
            }

            updateById(makerEntity);

            return R.success("银行卡实名认证成功");

        } catch (Exception e) {
            log.error("银行卡实名认证异步回调处理异常", e);
        }

        return R.fail("银行卡实名认证回调处理失败");
    }

    @Override
    public R<IdcardOcrVO> queryIdcardOcr(MakerEntity makerEntity) {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("未进行身份证实名认证");
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
    public MakerEntity findByUserId(Long userId) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<String> uploadMakerVideo(MakerEntity makerEntity, String applyShortVideo) {
        if (StringUtil.isBlank(applyShortVideo)) {
            R.fail("请选择视频");
        }
        makerEntity.setVideoAudit(VideoAudit.AUDITPASS);
        makerEntity.setApplyShortVideo(applyShortVideo);
        updateById(makerEntity);
        return R.success("成功");
    }

    @Override
    public R<IPage<MakerWorksheetVO>> getMakerName(Query query, String makerName) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(makerName != null, MakerEntity::getName, makerName);

        IPage<MakerEntity> pages = this.page(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);

        List<MakerWorksheetVO> records = pages.getRecords().stream().map(MakerEntity -> BeanUtil.copy(MakerEntity, MakerWorksheetVO.class)).collect(Collectors.toList());
        for (MakerWorksheetVO makerWorksheetVO : records) {
            if (SignState.SIGNED.equals(makerWorksheetVO.getEmpowerSignState()) && SignState.SIGNED.equals(makerWorksheetVO.getJoinSignState())) {
                makerWorksheetVO.setProtocolAuthentication(CertificationState.CERTIFIED);
            }
            if (VerifyStatus.VERIFYPASS.equals(makerWorksheetVO.getBankCardVerifyStatus())) {
                makerWorksheetVO.setRealNameAuthentication(CertificationState.CERTIFIED);
            }
        }

        IPage<MakerWorksheetVO> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);

        return R.data(pageVo);
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
        return R.success("添加成功");
    }

    @Override
    public MakerEntity findByIdcardNo(String idcardNo) {

        if (StringUtil.isBlank(idcardNo)) {
            return null;
        }

        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getIdcardNo, idcardNo);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<EnterpriseMakerDetailVO> queryMakerDetail(Long makerId) {
        return R.data(baseMapper.queryMakerDetail(makerId));
    }

    @Override
    public R saveAdminMakerVideo(Long makerId, String videoUrl) {
        MakerEntity makerEntity = getById(makerId);
        if (null == makerEntity) {
            return R.fail("不存在此创客");
        }
        makerEntity.setApplyShortVideo(videoUrl);
        makerEntity.setVideoAudit(VideoAudit.AUDITPASS);
        makerEntity.setVideoAuditDate(new Date());
        saveOrUpdate(makerEntity);
        return R.success("上传视频成功");
    }

    @Override
    public R getMakerAll(Long makerId,String makerName,IPage<MakerEntity> page) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        if(makerId != null && StringUtils.isNotEmpty(makerName)){
            queryWrapper.lambda().eq(MakerEntity::getId, makerId)
                    .like(MakerEntity::getName, makerName);
        }
        if(makerId != null && StringUtils.isEmpty(makerName)){
            queryWrapper.lambda().eq(MakerEntity::getId, makerId);
        }
        if(StringUtils.isNotEmpty(makerName) && makerId == null){
            queryWrapper.lambda().like(MakerEntity::getName, makerName);
        }
        IPage<MakerEntity> makerEntityIPage = baseMapper.selectPage(page, queryWrapper);

        return R.data(makerEntityIPage);
    }

    @Override
    public R<List<MakerExcel>> readMakerListExcel(MultipartFile file) throws IOException {
        //判断文件内容是否为空
        if (file.isEmpty()) {
            return R.fail("Excel文件内容为空");
        }
        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename();
        if ((!org.springframework.util.StringUtils.endsWithIgnoreCase(suffix, ".xls") && !org.springframework.util.StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
            return R.fail("请选择Excel文件");
        }

        List<MakerExcel> makerExcelList = ExcelUtils.importExcel(file, 0, 1, MakerExcel.class);
        return R.data(makerExcelList);
    }

    @Override
    public R<IPage<MakerListIndividualVO>> queryMakerListIndividual(Long enterpriseId, MakerListIndividualDTO makerListIndividualDTO, IPage<MakerListIndividualVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerListIndividual(enterpriseId, makerListIndividualDTO, page)));
    }

    @Override
    public R<IPage<MakerListVO>> queryMakerList(Long enterpriseId, Long serviceProviderId, RelationshipType relationshipType, CertificationState certificationState, String keyword, IPage<MakerListVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerList(enterpriseId, serviceProviderId, relationshipType, certificationState, keyword, page)));
    }

    @Override
    public String queryMakerName(Long payMakerId) {
        return baseMapper.queryMakerName(payMakerId);
    }

}
