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
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.IdcardOcrVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        MakerEntity makerEntity = getById(1);
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

        IdcardOcrVO idcardOcrVO = new IdcardOcrVO();
        idcardOcrVO.setName(name);
        idcardOcrVO.setIdNo(idNo);

        return R.data(idcardOcrVO);
    }

    @Override
    public R idcardOcrSave(IdcardOcrSaveDto idcardOcrSaveDto) {

        //TODO
        MakerEntity makerEntity = getById(1);
        //查看创客是否已经身份证实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus())) {
            return R.fail("身份证已实名认证");
        }

        makerEntity.setIdcardPic(idcardOcrSaveDto.getIdcardPic());
        makerEntity.setIdcardPicBack(idcardOcrSaveDto.getIdcardPicBack());
        makerEntity.setName(idcardOcrSaveDto.getName());
        makerEntity.setIdcardNo(idcardOcrSaveDto.getIdNo());
        makerEntity.setIdcardVerifyStatus(VerifyStatus.VERIFYPASS);
        makerEntity.setIdcardVerifyDate(new Date());
        makerEntity.setIdcardVerifyType(IdcardVerifyType.SYSTEMVERIFY);
        saveOrUpdate(makerEntity);

        return R.success("身份证实名认证信息保存成功");
    }

    @Override
    public R faceOcr() throws Exception {

        //TODO
        MakerEntity makerEntity = getById(1);
        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经刷脸实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
            return R.fail("已刷脸实名认证");
        }

        return RealnameVerifyUtil.faceOCR(makerEntity.getMakerId(), "谭章权", "445381199501095717");
    }

    @Override
    public R faceOcrNotify(HttpServletRequest request) throws Exception {

        logger.info("进入刷脸实名认证异步通知");
        //获取body的数据进行验签
        String rbody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
        boolean res = RealnameVerifyUtil.checkPass(request, rbody, RealnameVerifyConstant.APPKEY);
        if (!res) {
            return R.fail("验签失败");
        }

        // 业务逻辑处理 ****************************
        //回调参数转json
        JSONObject jsonObject = JSONObject.parseObject(rbody);
        boolean boolSuccess = jsonObject.getBooleanValue("success");
        if (!boolSuccess) {
            return R.fail("刷脸认证失败");
        }

        Long makerId = jsonObject.getLong("contextId");
        MakerEntity makerEntity = getById(makerId);
        if (makerEntity == null) {
            logger.info("创客不存在");
            return R.fail("刷脸认证回调处理失败");
        }

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            logger.info("数据有误，创客未进行身份证实名认证");
            return R.fail("刷脸认证回调处理失败");
        }

        //查看创客是否已经刷脸实名认证
        if (VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
            return R.success("已刷脸实名认证");
        }

        //查询认证信息
        JSONObject detail = RealnameVerifyUtil.detail(jsonObject.getString("flowId"));
        if (detail == null){
            return R.fail("查询认证信息失败");
        }

        //获取人脸截图
        JSONObject indivInfo = detail.getJSONObject("indivInfo");
        //base64链接
        String facePhotoUrl = indivInfo.getString("facePhotoUrl");
        //获取base64
        String facePhotoBase64 = HttpUtil.get(facePhotoUrl);
        //TODO
        //base图片上传
        




        makerEntity.setPicVerify(facePhotoUrl);
        makerEntity.setFaceVerifyStatus(VerifyStatus.VERIFYPASS);
        makerEntity.setFaceVerifyDate(new Date());
        saveOrUpdate(makerEntity);

        return R.success("刷脸认证成功");
    }

    @Override
    public R detail(String flowId) throws Exception {
        JSONObject jsonObject = RealnameVerifyUtil.detail(flowId);
        return R.data(jsonObject);
    }

}
