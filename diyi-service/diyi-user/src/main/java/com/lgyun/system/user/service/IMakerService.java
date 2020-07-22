package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.IdcardOcrSaveDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.MakerEnterpriseNumIncomeVO;
import com.lgyun.system.user.vo.MakerInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface IMakerService extends IService<MakerEntity> {

    /**
     * 根据微信手机号码获取创客
     *
     * @param phoneNumber
     * @return
     */
    MakerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号码密码获取创客
     *
     * @param phoneNumber
     * @return
     */
    MakerEntity findByPhoneNumberAndLoginPwd(String phoneNumber, String loginPwd);

    /**
     * 身份证实名认证
     *
     * @param idcardPic
     * @return
     */
    R idcardOcr(String idcardPic, MakerEntity makerEntity) throws Exception;

    /**
     * 身份证实名认证信息保存
     *
     * @return
     */
    R idcardOcrSave(IdcardOcrSaveDto idcardOcrSaveDto, MakerEntity makerEntity);

    /**
     * 身份实名认证
     *
     * @return
     */
    R faceOcr(MakerEntity makerEntity) throws Exception;

    /**
     * 身份实名认证异步回调
     *
     * @return
     */
    R faceOcrNotify(HttpServletRequest request) throws Exception;

    /**
     * 银行卡实名认证
     *
     * @return
     */
    R bankCardOcr(String bankCardNo, MakerEntity makerEntity) throws Exception;

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
    R mobileOcr(MakerEntity makerEntity) throws Exception;

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
    R queryIdcardOcr(MakerEntity makerEntity);

    /**
     * 获取运营者名称
     */
    String getName(Long id);

    /**
     * 根据userId获取创客
     *
     * @param userId
     * @return
     */
    MakerEntity findByUserId(Long userId);

    /**
     * 获取创客基本信息
     *
     * @param
     * @return
     */
    R<MakerInfoVO> getInfo(Long makerId);

    /**
     * 获取当前创客关联商户数和收入情况
     *
     * @param
     * @return
     */
    R<MakerEnterpriseNumIncomeVO> getEnterpriseNumIncome(Long makerId);

    /**
     * 获取当前创客
     *
     * @param bladeUser
     * @return
     */
    MakerEntity current(BladeUser bladeUser);


    /**
     *
     */
    R uploadMakerVideo(MakerEntity makerEntity ,String applyShortVideo);

}

