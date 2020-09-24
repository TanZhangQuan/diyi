package com.lgyun.system.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.IdcardOcrSaveDto;
import com.lgyun.system.user.dto.MakerAddDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Service 接口
 *
 * @author tzq
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
     * 根据微信手机号码查询创客
     *
     * @param phoneNumber
     * @return
     */
    MakerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据微信手机号码查询创客是否存在
     *
     * @param phoneNumber
     * @return
     */
    Integer findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号码密码查询创客
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
     * 活体认证
     *
     * @param makerEntity
     * @return
     * @throws Exception
     */
    R faceOcr(MakerEntity makerEntity) throws Exception;

    /**
     * 活体认证异步回调
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
     * 查询运营者名称
     *
     * @param makerId
     * @return
     */
    String getName(Long makerId);

    /**
     * 根据userId查询创客
     *
     * @param userId
     * @return
     */
    MakerEntity findByUserId(Long userId);

    /**
     * 查询创客基本信息
     *
     * @param makerId
     * @return
     */
    R<MakerInfoVO> getInfo(Long makerId);

    /**
     * 查询当前创客关联商户数和收入情况
     *
     * @param makerId
     * @return
     */
    R<MakerEnterpriseNumIncomeVO> getEnterpriseNumIncome(Long makerId);

    /**
     * 查询当前创客
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
     *
     * @param current
     * @param size
     * @param makerName
     * @return
     */
    R<IPage<MakerWorksheetVO>> getMakerName(Integer current, Integer size, String makerName);

    /**
     * 查询当前创客所有实名认证状态
     *
     * @param makerId
     * @return
     */
    R<MakerRealNameAuthenticationStateVO> getRealNameAuthenticationState(Long makerId);

    /**
     * 导入创客数据
     *
     * @param list
     * @param enterpriseId
     */
    void importMaker(List<MakerExcel> list, Long enterpriseId);

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
     * 根据创客ID查询创客详情
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    R<EnterpriseMakerDetailVO> getMakerDetailById(Long enterpriseId, Long makerId);

    /**
     * 新增单个创客
     *
     * @param makerAddDto
     * @param enterpriseId
     * @return
     */
    MakerEntity enterpriseMakerAdd(MakerAddDto makerAddDto, Long enterpriseId);

    /**
     * 平台端上传创客授权视频
     */
    R saveAdminMakerVideo(Long makerId,String videoUrl);
}

