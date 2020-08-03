package com.lgyun.system.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.IdcardOcrSaveDto;
import com.lgyun.system.user.dto.MakerAddDto;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface IMakerService extends IService<MakerEntity> {

    /**
     * 新建创客
     *
     * @param purePhoneNumber
     * @param name
     * @param idcardNo
     * @param idcardPic
     * @param idcardPicBack
     * @param idcardHand
     * @param idcardBackHand
     * @param enterpriseId
     * @return
     */
    MakerEntity makerSave(String purePhoneNumber, String name, String idcardNo, String idcardPic, String idcardPicBack, String idcardHand, String idcardBackHand, Long enterpriseId);

    /**
     * 新建创客
     *
     * @param openid
     * @param sessionKey
     * @param purePhoneNumber
     * @param loginPwd
     * @return
     */
    MakerEntity makerSave(String openid, String sessionKey, String purePhoneNumber, String loginPwd);

    /**
     * 新建创客
     *
     * @param purePhoneNumber
     * @param name
     * @param idcardNo
     * @param bankCardNo
     * @param bankName
     * @param subBankName
     * @param enterpriseId
     * @return
     */
    MakerEntity makerSave(String purePhoneNumber, String name, String idcardNo, String bankCardNo, String bankName, String subBankName, Long enterpriseId);

    /**
     * 修改创客
     *
     * @param makerEntity
     * @param openid
     * @param sessionKey
     */
    void makerUpdate(MakerEntity makerEntity, String openid, String sessionKey);

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
     * @param loginPwd
     * @return
     */
    MakerEntity findByPhoneNumberAndLoginPwd(String phoneNumber, String loginPwd);

    /**
     * 身份证实名认证
     *
     * @param idcardPic
     * @param makerEntity
     * @return
     * @throws Exception
     */
    R<JSONObject> idcardOcr(String idcardPic, MakerEntity makerEntity) throws Exception;

    /**
     * 身份证实名认证信息保存
     *
     * @param idcardOcrSaveDto
     * @param makerEntity
     * @return
     */
    R<String> idcardOcrSave(IdcardOcrSaveDto idcardOcrSaveDto, MakerEntity makerEntity);

    /**
     * 身份实名认证
     *
     * @param makerEntity
     * @return
     * @throws Exception
     */
    R<String> faceOcr(MakerEntity makerEntity) throws Exception;

    /**
     * 身份实名认证异步回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    R<String> faceOcrNotify(HttpServletRequest request) throws Exception;

    /**
     * 银行卡实名认证
     *
     * @param bankCardNo
     * @param makerEntity
     * @return
     * @throws Exception
     */
    R<JSONObject> bankCardOcr(String bankCardNo, MakerEntity makerEntity) throws Exception;

    /**
     * 银行卡实名认证异步回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    R<String> bankCardOcrNotify(HttpServletRequest request) throws Exception;

    /**
     * 银行卡实名认证
     *
     * @param makerEntity
     * @return
     * @throws Exception
     */
    R<JSONObject> mobileOcr(MakerEntity makerEntity) throws Exception;

    /**
     * 银行卡实名认证异步回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    R<String> mobileOcrNotify(HttpServletRequest request) throws Exception;

    /**
     * 查询当前创客身份证实名认证的照片
     *
     * @param makerEntity
     * @return
     */
    R<IdcardOcrVO> queryIdcardOcr(MakerEntity makerEntity);

    /**
     * 获取运营者名称
     *
     * @param makerId
     * @return
     */
    String getName(Long makerId);

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
     * @param makerId
     * @return
     */
    R<MakerInfoVO> getInfo(Long makerId);

    /**
     * 获取当前创客关联商户数和收入情况
     *
     * @param makerId
     * @return
     */
    R<MakerEnterpriseNumIncomeVO> getEnterpriseNumIncome(Long makerId);

    /**
     * 获取当前创客
     *
     * @param bladeUser
     * @return
     */
    R<MakerEntity> currentMaker(BladeUser bladeUser);


    /**
     * 上传创客视频
     *
     * @param makerEntity
     * @param applyShortVideo
     * @return
     */
    R<String> uploadMakerVideo(MakerEntity makerEntity, String applyShortVideo);

    /**
     * 根据创客姓名分页查询
     */
    R getMakerName(Integer current,Integer size,String makerName);

    /**
     * 获取当前创客所有实名认证状态
     *
     * @param makerId
     * @return
     */
    R<MakerRealNameAuthenticationStateVO> getRealNameAuthenticationState(Long makerId);

    /**
     * 导入创客数据
     *
     * @param list
     */
    void importMaker(List<MakerExcel> list);

    /**
     * 修改密码
     *
     * @param updatePasswordDto
     * @return
     */
    R<String> updatePassword(UpdatePasswordDto updatePasswordDto);

    /**
     * 新增单个创客
     *
     * @param makerAddDto
     * @param enterpriseId
     * @return
     */
    R<String> makerAdd(MakerAddDto makerAddDto, Long enterpriseId);

    /**
     * 根据身份证号查找创客
     *
     * @param idcardNo
     * @return
     */
    MakerEntity findByIdcardNo(String idcardNo);

    /**
     * 根据创客ID获取创客详情
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    R<EnterpriseMakerDetailVO> getMakerDetailById(Long enterpriseId, Long makerId);
}

