package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.system.user.dto.IdcardOcrSaveDto;
import com.lgyun.system.user.entity.MakerEntity;

import javax.servlet.http.HttpServletRequest;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface IMakerService extends IService<MakerEntity> {

    /**
     * 根据微信openid获取创客
     *
     * @param openid
     * @return
     */
    MakerEntity findByOpenid(String openid);

    /**
     * 根据微信手机号码获取创客
     *
     * @param phoneNumber
     * @return
     */
    MakerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 身份证实名认证
     *
     * @param idcardPic
     * @return
     */
    R idcardOcr(String idcardPic) throws Exception;

    /**
     * 身份证实名认证信息保存
     *
     * @return
     */
    R idcardOcrSave(IdcardOcrSaveDto idcardOcrSaveDto);

    /**
     * 刷脸实名认证
     *
     * @return
     */
    R faceOcr() throws Exception;

    /**
     * 刷脸实名认证异步回调
     *
     * @return
     */
    R faceOcrNotify(HttpServletRequest request) throws Exception;

    /**
     * 查询认证详情
     *
     * @return
     */
    R detail(String flowId) throws Exception;

    /**
     * 银行卡实名认证
     *
     * @return
     */
    R bankCardOcr(String bankCardNo) throws Exception;

    /**
     * 银行卡实名认证异步回调
     *
     * @return
     */
    R bankCardOcrNotify(HttpServletRequest request) throws Exception;

    /**
     * 银行卡实名认证
     *
     * @return
     */
    R mobileOcr() throws Exception;

    /**
     * 银行卡实名认证异步回调
     *
     * @return
     */
    R mobileOcrNotify(HttpServletRequest request) throws Exception;

    /**
     * 查询当前创客身份证实名认证的照片
     *
     * @return
     */
    R queryIdcardOcr();

    /**
     * 检查当前创客身份证和人脸是否已实名认证
     *
     * @return
     */
    R checkIdcardFaceVerify();

}

