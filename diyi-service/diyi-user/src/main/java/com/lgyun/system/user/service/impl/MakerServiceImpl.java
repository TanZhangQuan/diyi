package com.lgyun.system.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.RealnameVerifyConstant;
import com.lgyun.common.enumeration.IdcardVerifyType;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.common.tool.HttpUtil;
import com.lgyun.common.tool.ImageUtil;
import com.lgyun.common.tool.RealnameVerifyUtil;
import com.lgyun.system.user.dto.IdcardOcrSaveDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.MakerMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.IdcardOcrVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MakerServiceImpl extends ServiceImpl<MakerMapper, MakerEntity> implements IMakerService {
    private Logger logger = LoggerFactory.getLogger(MakerServiceImpl.class);

    private final AliyunOssService ossService;

    @Override
    public MakerEntity findByOpenid(String openid) {
        return baseMapper.findByOpenid(openid);
    }

    @Override
    public MakerEntity findByPhoneNumber(String phoneNumber) {
        return baseMapper.findByPhoneNumber(phoneNumber);
    }

    @Override
    public R idcardOcr(String idcardPic) throws Exception {

        //TODO
        MakerEntity makerEntity = getById(1278969988057903106L);
        //查看创客是否已经身份证实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus())) {
            return R.fail("身份证已实名认证");
        }

        //转化base64
        String idcardPicBase64 = ImageUtil.ImageToBase64ByOnline(idcardPic);

        //身份证实名认证
        JSONObject jsonObject = RealnameVerifyUtil.idCardOCR(idcardPicBase64);
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
    public R idcardOcrSave(IdcardOcrSaveDto idcardOcrSaveDto) {

        //TODO
        MakerEntity makerEntity = getById(1278969988057903106L);
        //查看创客是否已经身份证实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus())) {
            return R.fail("身份证已实名认证");
        }

        BeanUtils.copyProperties(idcardOcrSaveDto, makerEntity);
        makerEntity.setIdcardVerifyStatus(VerifyStatus.VERIFYPASS);
        makerEntity.setIdcardVerifyType(IdcardVerifyType.SYSTEMVERIFY);
        makerEntity.setIdcardVerifyDate(new Date());

        save(makerEntity);

        return R.success("身份证实名认证信息保存成功");
    }

    @Override
    public R faceOcr() throws Exception {

        //TODO
        MakerEntity makerEntity = getById(1278969988057903106L);
        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经刷脸实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
            return R.fail("已刷脸实名认证");
        }

        return RealnameVerifyUtil.faceOCR(makerEntity.getMakerId(), makerEntity.getName(), makerEntity.getIdcardNo());
    }

    @Override
    public R faceOcrNotify(HttpServletRequest request) {

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
            logger.info("刷脸实名认证异步通知回调参数", jsonObject);
            boolean boolSuccess = jsonObject.getBooleanValue("success");
            if (!boolSuccess) {
                return R.fail("刷脸实名认证失败");
            }

            Long makerId = jsonObject.getLong("contextId");
            MakerEntity makerEntity = getById(makerId);
            if (makerEntity == null) {
                logger.info("创客不存在");
                return R.fail("刷脸实名认证回调处理失败");
            }

            //查看创客是否已经刷脸实名认证
            if (VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
                return R.success("已刷脸实名认证");
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
            byte[] bytes = new BASE64Decoder().decodeBuffer(facePhotoBase64.trim());
            String url = ossService.uploadSuffix(bytes, ".jpg");

            makerEntity.setPicVerify(url);
            makerEntity.setFaceVerifyStatus(VerifyStatus.VERIFYPASS);
            makerEntity.setFaceVerifyDate(new Date());
            saveOrUpdate(makerEntity);

            return R.success("刷脸实名认证成功");

        } catch (Exception e) {
            logger.error("刷脸实名认证异步回调处理异常", e);
        }

        return R.fail("刷脸实名认证回调处理失败");
    }

    @Override
    public R detail(String flowId) throws Exception {
        JSONObject jsonObject = RealnameVerifyUtil.detail(flowId);
        return R.data(jsonObject);
    }

    @Override
    public R bankCardOcr(String bankCardNo) throws Exception {

        //TODO
        MakerEntity makerEntity = getById(1278969988057903106L);
        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经刷脸实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行刷脸实名认证");
        }

        //查看创客是否已经手机号实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus()))) {
            return R.fail("请先进行手机号实名认证");
        }

        //查看创客是否已经刷脸实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getBankCardVerifyStatus())) {
            return R.fail("银行卡已实名认证");
        }

        return RealnameVerifyUtil.bankCardOCR(makerEntity.getMakerId(), makerEntity.getName(), makerEntity.getIdcardNo(), bankCardNo, makerEntity.getPhoneNumber());
    }

    @Override
    public R bankCardOcrNotify(HttpServletRequest request) {

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
            logger.info("银行卡实名认证异步通知回调参数", jsonObject);
            boolean boolSuccess = jsonObject.getBooleanValue("success");
            if (!boolSuccess) {
                return R.fail("银行卡实名认证失败");
            }

            Long makerId = jsonObject.getLong("contextId");
            MakerEntity makerEntity = getById(makerId);
            if (makerEntity == null) {
                logger.info("创客不存在");
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
            saveOrUpdate(makerEntity);

            return R.success("银行卡实名认证成功");

        } catch (Exception e) {
            logger.error("银行卡实名认证异步回调处理异常", e);
        }

        return R.fail("银行卡实名认证回调处理失败");
    }

    @Override
    public R mobileOcr() throws Exception {

        //TODO
        MakerEntity makerEntity = getById(1278969988057903106L);
        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经刷脸实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行刷脸实名认证");
        }

        //查看创客是否已经手机号实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus())) {
            return R.fail("手机号已实名认证");
        }

        return RealnameVerifyUtil.mobileOCR(makerEntity.getMakerId(), makerEntity.getName(), makerEntity.getIdcardNo(), makerEntity.getPhoneNumber());
    }

    @Override
    public R mobileOcrNotify(HttpServletRequest request) {

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
            logger.info("手机号实名认证异步通知回调参数", jsonObject);
            boolean boolSuccess = jsonObject.getBooleanValue("success");
            if (!boolSuccess) {
                return R.fail("手机号实名认证失败");
            }

            Long makerId = jsonObject.getLong("contextId");
            MakerEntity makerEntity = getById(makerId);
            if (makerEntity == null) {
                logger.info("创客不存在");
                return R.fail("手机号实名认证回调处理失败");
            }

            //查看创客手机号是否已经实名认证
            if (VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus())) {
                return R.success("手机号已实名认证");
            }

            makerEntity.setPhoneNumberVerifyStatus(VerifyStatus.VERIFYPASS);
            makerEntity.setPhoneNumberVerifyDate(new Date());
            saveOrUpdate(makerEntity);

            return R.success("手机号实名认证成功");

        } catch (Exception e) {
            logger.error("手机号实名认证异步回调处理异常", e);
        }

        return R.fail("手机号实名认证回调处理失败");
    }

    @Override
    public R queryIdcardOcr() {

        //TODO
        MakerEntity makerEntity = getById(1278969988057903106L);
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
    public R checkIdcardFaceVerify() {

        //TODO
        MakerEntity makerEntity = getById(1278969988057903106L);
        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经刷脸实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行刷脸实名认证");
        }

        return R.success("身份证和人脸已实名认证");
    }

}
