package com.lgyun.system.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.RealnameVerifyConstant;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.*;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.IdcardOcrSaveDto;
import com.lgyun.system.user.dto.MakerAddDto;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.mapper.MakerMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service 实现
 *
 * @author liangfeihu
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
    private RedisUtil redisUtil;
    private IUserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MakerEntity makerSave(String purePhoneNumber, String name, String idcardNo, String idcardPic, String idcardPicBack, String idcardHand, String idcardBackHand, Long enterpriseId) {
        return makerSave("", "", purePhoneNumber, "", name, idcardNo, "", "", "", idcardPic, idcardPicBack, idcardHand, idcardBackHand, enterpriseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MakerEntity makerSave(String openid, String sessionKey, String purePhoneNumber, String loginPwd) {
        return makerSave(openid, sessionKey, purePhoneNumber, loginPwd, "", "", "", "", "", "", "", "", "", null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MakerEntity makerSave(String purePhoneNumber, String name, String idcardNo, String bankCardNo, String bankName, String subBankName, Long enterpriseId) {
        return makerSave("", "", purePhoneNumber, "", name, idcardNo, bankCardNo, bankName, subBankName, "", "", "", "", enterpriseId);
    }

    @Transactional(rollbackFor = Exception.class)
    public MakerEntity makerSave(String openid, String sessionKey, String purePhoneNumber, String loginPwd, String name,
                                 String idcardNo, String bankCardNo, String bankName, String subBankName, String idcardPic,
                                 String idcardPicBack, String idcardHand, String idcardBackHand, Long enterpriseId) {

        MakerEntity makerEntity;
        MakerEntity makerEntityPhoneNumber = findByPhoneNumber(purePhoneNumber);
        MakerEntity makerEntityIdcardNo = findByIdcardNo(idcardNo);
        if (makerEntityPhoneNumber == null && makerEntityIdcardNo == null) {
            //新建管理员
            User user = new User();
            user.setUserType(UserType.MAKER);
            user.setAccount(purePhoneNumber);
            user.setPassword(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
            user.setPhone(purePhoneNumber);
            iUserService.save(user);

            //新建创客
            makerEntity = new MakerEntity();
            makerEntity.setOpenid(openid);
            makerEntity.setUserId(user.getId());
            makerEntity.setSessionKey(sessionKey);
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
            makerEntity.setIdcardPic(idcardPic);
            makerEntity.setIdcardPicBack(idcardPicBack);
            makerEntity.setIdcardHand(idcardHand);
            makerEntity.setIdcardBackHand(idcardBackHand);
            makerEntity.setRelDate(new Date());
            makerEntity.setCertificationState(CertificationState.UNCERTIFIED);
            makerEntity.setJoinSignState(SignState.UNSIGN);
            makerEntity.setEmpowerSignState(SignState.UNSIGN);
            makerEntity.setMakerState(AccountState.NORMAL);
            makerEntity.setIdcardVerifyStatus(VerifyStatus.TOVERIFY);
            makerEntity.setFaceVerifyStatus(VerifyStatus.TOVERIFY);
            makerEntity.setPhoneNumberVerifyStatus(VerifyStatus.TOVERIFY);
            makerEntity.setBankCardVerifyStatus(VerifyStatus.TOVERIFY);
            makerEntity.setVideoAudit(VideoAudit.TOAUDIT);
            save(makerEntity);

        } else if (makerEntityPhoneNumber != null && makerEntityIdcardNo == null) {
            if (!(VerifyStatus.VERIFYPASS.equals(makerEntityPhoneNumber.getIdcardVerifyStatus()))) {
                makerEntityPhoneNumber.setName(name);
                makerEntityPhoneNumber.setIdcardNo(idcardNo);
                makerEntityPhoneNumber.setBankCardNo(bankCardNo);
                makerEntityPhoneNumber.setBankName(bankName);
                makerEntityPhoneNumber.setSubBankName(subBankName);
                makerEntityPhoneNumber.setIdcardPic(idcardPic);
                makerEntityPhoneNumber.setIdcardPicBack(idcardPicBack);
                makerEntityPhoneNumber.setIdcardHand(idcardHand);
                makerEntityPhoneNumber.setIdcardBackHand(idcardBackHand);
            } else {
                if (!(VerifyStatus.VERIFYPASS.equals(makerEntityPhoneNumber.getBankCardVerifyStatus()))) {
                    makerEntityPhoneNumber.setBankCardNo(bankCardNo);
                    makerEntityPhoneNumber.setBankName(bankName);
                    makerEntityPhoneNumber.setSubBankName(subBankName);
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
                makerEntityIdcardNo.setIdcardPic(idcardPic);
                makerEntityIdcardNo.setIdcardPicBack(idcardPicBack);
                makerEntityIdcardNo.setIdcardHand(idcardHand);
                makerEntityIdcardNo.setIdcardBackHand(idcardBackHand);
            } else {
                if (!(VerifyStatus.VERIFYPASS.equals(makerEntityIdcardNo.getBankCardVerifyStatus()))) {
                    makerEntityIdcardNo.setBankCardNo(bankCardNo);
                    makerEntityIdcardNo.setBankName(bankName);
                    makerEntityIdcardNo.setSubBankName(subBankName);
                }
            }

            makerEntity = makerEntityIdcardNo;
        }

        if (enterpriseId != null) {
            //商户-创客关联
            makerEnterpriseService.makerEnterpriseEntitySave(enterpriseId, makerEntity.getId());

            //添加创客合同和授权
            //TODO
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
        JSONObject jsonObject = RealnameVerifyUtil.idCardOCR(idcardPic);
        if (jsonObject == null) {
            return R.fail("身份证实名认证失败");
        }

        //获取姓名和身份证号码
        String name = jsonObject.getString("name");
        String idNo = jsonObject.getString("idNo");

        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("idNo", idNo);

        return R.data(result);
    }

    @Override
    public R<String> idcardOcrSave(IdcardOcrSaveDto idcardOcrSaveDto, MakerEntity makerEntity) {

        //查看创客是否已经身份证实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus())) {
            return R.fail("身份证已实名认证");
        }

        //查询身份证号码是否已被使用
        MakerEntity makerEntityIdcardNo = findByIdcardNo(idcardOcrSaveDto.getIdcardNo());
        if (makerEntityIdcardNo != null) {
            return R.fail("身份证号码已被使用");
        }

        BeanUtils.copyProperties(idcardOcrSaveDto, makerEntity);
        makerEntity.setIdcardVerifyStatus(VerifyStatus.VERIFYPASS);
        makerEntity.setIdcardVerifyType(IdcardVerifyType.SYSTEMVERIFY);
        makerEntity.setIdcardVerifyDate(new Date());

        updateById(makerEntity);

        return R.success("身份证实名认证信息保存成功");
    }

    @Override
    public R<String> faceOcr(MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经身份实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
            return R.fail("已身份实名认证");
        }

        R result = RealnameVerifyUtil.faceOCR(makerEntity.getId(), makerEntity.getName(), makerEntity.getIdcardNo());
        log.info("人脸识别请求返回参数", result);
        if (!(result.isSuccess())) {
            return result;
        }

        //通过短信发送人脸识别URL
        JSONObject jsonObject = (JSONObject) result.getData();
        String shortLink = jsonObject.getString("shortLink");
        R smsResult = smsUtil.sendLink(makerEntity.getPhoneNumber(), shortLink);
        if (!(smsResult.isSuccess())) {
            return result;
        }

        return R.success("身份识别请求已通过短信发送，请及时进行操作");
    }

    @Override
    public R<String> faceOcrNotify(HttpServletRequest request) {

        try {
            //获取body的数据进行验签
            String rbody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            boolean res = RealnameVerifyUtil.checkPass(request, rbody, RealnameVerifyConstant.APPKEY);
            if (!res) {
                return R.fail("验签失败");
            }

            // 业务逻辑处理 ****************************
            //回调参数转json
            JSONObject jsonObject = JSONObject.parseObject(rbody);
            log.info("身份实名认证异步通知回调参数", jsonObject);
            boolean boolSuccess = jsonObject.getBooleanValue("success");
            if (!boolSuccess) {
                return R.fail("身份实名认证失败");
            }

            Long makerId = jsonObject.getLong("contextId");
            MakerEntity makerEntity = getById(makerId);
            if (makerEntity == null) {
                log.info("创客不存在");
                return R.fail("身份实名认证回调处理失败");
            }

            //查看创客是否已经身份实名认证
            if (VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
                return R.success("已身份实名认证");
            }

            //查询认证信息
            JSONObject detail = RealnameVerifyUtil.detail(jsonObject.getString("flowId"));
            if (detail == null) {
                return R.fail("查询认证信息失败");
            }

            //获取个人信息
            JSONObject indivInfo = detail.getJSONObject("indivInfo");
            //人脸截图base64请求地址
            String facePhotoUrl = indivInfo.getString("facePhotoUrl");
            //获取人脸截图base64
            String facePhotoBase64 = HttpUtil.get(facePhotoUrl);
            //上传人脸截图base64到阿里云存储
            byte[] bytes = Base64Util.decodeFromString(facePhotoBase64.trim());
            String url = ossService.uploadSuffix(bytes, ".jpg");

            makerEntity.setPicVerify(url);
            makerEntity.setFaceVerifyStatus(VerifyStatus.VERIFYPASS);
            makerEntity.setFaceVerifyDate(new Date());

            updateById(makerEntity);

            return R.success("身份实名认证成功");

        } catch (Exception e) {
            log.error("身份实名认证异步回调处理异常", e);
        }

        return R.fail("身份实名认证回调处理失败");
    }

    @Override
    public R<JSONObject> bankCardOcr(String bankCardNo, MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经身份实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行身份实名认证");
        }

        //查看创客是否已经手机号实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus()))) {
            return R.fail("请先进行手机号实名认证");
        }

        //查看创客是否已经身份实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getBankCardVerifyStatus())) {
            return R.fail("银行卡已实名认证");
        }

        return RealnameVerifyUtil.bankCardOCR(makerEntity.getId(), makerEntity.getName(), makerEntity.getIdcardNo(), bankCardNo, makerEntity.getPhoneNumber());
    }

    @Override
    public R<String> bankCardOcrNotify(HttpServletRequest request) {

        try {
            //获取body的数据进行验签
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

            //获取个人信息
            JSONObject indivInfo = detail.getJSONObject("indivInfo");
            //获取银行卡号
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
    public R<JSONObject> mobileOcr(MakerEntity makerEntity) throws Exception {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经身份实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行身份实名认证");
        }

        //查看创客是否已经手机号实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus())) {
            return R.fail("手机号已实名认证");
        }

        return RealnameVerifyUtil.mobileOCR(makerEntity.getId(), makerEntity.getName(), makerEntity.getIdcardNo(), makerEntity.getPhoneNumber());
    }

    @Override
    public R<String> mobileOcrNotify(HttpServletRequest request) {

        try {
            //获取body的数据进行验签
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
    public String getName(Long makerId) {

        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getId, makerId);
        MakerEntity makerEntity = baseMapper.selectOne(queryWrapper);
        if (makerEntity == null) {
            return null;
        }

        return makerEntity.getName();
    }

    @Override
    public MakerEntity findByUserId(Long userId) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<MakerInfoVO> getInfo(Long makerId) {

        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEntity::getId, makerId);

        MakerEntity makerEntity = baseMapper.selectOne(queryWrapper);
        if (makerEntity == null) {
            return R.fail("创客不存在");
        }

        MakerInfoVO makerInfoVO = BeanUtil.copy(makerEntity, MakerInfoVO.class);
        return R.data(makerInfoVO);
    }

    @Override
    public R<MakerEnterpriseNumIncomeVO> getEnterpriseNumIncome(Long makerId) {
        return R.data(baseMapper.getEnterpriseNumIncome(makerId, makerId));
    }

    @Override
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("账号未登陆");
        }

        User user = userService.getById(bladeUser.getUserId());
        if (user == null){
            return R.fail("用户不存在");
        }

        if (!(UserType.ENTERPRISE.equals(user.getUserType()))) {
            return R.fail("用户类型有误");
        }

        MakerEntity makerEntity = findByUserId(bladeUser.getUserId());
        if (makerEntity == null) {
            return R.fail("账号未注册");
        }

        if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(makerEntity);
    }

    @Override
    public R<String> uploadMakerVideo(MakerEntity makerEntity, String applyShortVideo) {
        if (StringUtil.isBlank(applyShortVideo)) {
            R.fail("视频连接不能为空");
        }
        makerEntity.setApplyShortVideo(applyShortVideo);
        makerEntity.setVideoAudit(VideoAudit.TOAUDIT);
        saveOrUpdate(makerEntity);
        return R.success("成功");
    }

    @Override
    public R getMakerName(Integer current, Integer size, String makerName) {
        QueryWrapper<MakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(makerName != null, MakerEntity::getName, makerName);

        IPage<MakerEntity> pages = this.page(new Page<>(current, size), queryWrapper);

        List<MakerDetailVO> records = pages.getRecords().stream().map(MakerEntity -> BeanUtil.copy(MakerEntity, MakerDetailVO.class)).collect(Collectors.toList());

        IPage<MakerDetailVO> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
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
    public void importMaker(List<MakerExcel> list) {
        list.forEach(makerExcel -> {
            try {
                //新建创客
                makerSave(makerExcel.getPhoneNumber(), makerExcel.getName(), makerExcel.getIdcardNo(), makerExcel.getBankCardNo(),
                        makerExcel.getBankName(), makerExcel.getBankCardNo(), makerExcel.getEnterpriseId());
            } catch (Exception e) {
                log.error("新建创客异常", e);
            }

        });
    }

    @Override
    public R<String> updatePassword(UpdatePasswordDto updatePasswordDto) {

        MakerEntity makerEntity = findByPhoneNumber(updatePasswordDto.getPhoneNumber());
        if (makerEntity == null) {
            return R.fail("创客不存在");
        }

        //获取缓存短信验证码
        String redisCode = (String) redisUtil.get(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, updatePasswordDto.getSmsCode())) {
            return R.fail("短信验证码不正确");
        }

        makerEntity.setLoginPwd(DigestUtil.encrypt(updatePasswordDto.getNewPassword()));
        save(makerEntity);

        //删除缓存短信验证码
        redisUtil.del(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());

        return R.success("修改密码成功");
    }

    @Override
    public R<String> makerAdd(MakerAddDto makerAddDto, Long enterpriseId) {
        //新建创客
        makerSave(makerAddDto.getPhoneNumber(), makerAddDto.getName(), makerAddDto.getIdcardNo(), makerAddDto.getBankCardNo(),
                makerAddDto.getBankName(), makerAddDto.getBankCardNo(), enterpriseId);

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
    public R<EnterpriseMakerDetailVO> getMakerDetailById(Long enterpriseId, Long makerId) {
        return R.data(baseMapper.getMakerDetailById(enterpriseId, makerId));
    }

}
