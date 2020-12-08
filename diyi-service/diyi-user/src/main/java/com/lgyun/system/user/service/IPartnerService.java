package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddPartnerDTO;
import com.lgyun.system.user.dto.PartnerListDTO;
import com.lgyun.system.user.dto.UpdatePartnerDeatilDTO;
import com.lgyun.system.user.dto.UpdatePhoneNumberDTO;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.vo.BaseInfoVO;
import com.lgyun.system.user.vo.PartnerDetailVO;
import com.lgyun.system.user.vo.PartnerListVO;

/**
 * 合伙人信息表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
public interface IPartnerService extends BaseService<PartnerEntity> {

    /**
     * 查询当前合伙人
     *
     * @param bladeUser
     * @return
     */
    R<PartnerEntity> currentPartner(BladeUser bladeUser);

    /**
     * 新建合伙人
     *
     * @param openid
     * @param sessionKey
     * @param phoneNumber
     * @param password
     */
    PartnerEntity partnerSave(String openid, String sessionKey, String phoneNumber, String password);

    /**
     * 新建合伙人
     *
     * @param purePhoneNumber
     * @param name
     * @param idcardNo
     * @param bankCardNo
     * @param bankName
     * @param subBankName
     * @return
     */
    PartnerEntity partnerSave(String purePhoneNumber, String loginPwd, String name, String idcardNo, String bankCardNo, String bankName, String subBankName, Long introducePartnerId);

    /**
     * 根据手机号查询合伙人
     *
     * @param phoneNumber
     * @return
     */
    PartnerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据微信手机号码查询合伙人是否存在
     *
     * @param phoneNumber
     * @return
     */
    int findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据身份证号查找合伙人
     *
     * @param idcardNo
     * @return
     */
    PartnerEntity findByIdcardNo(String idcardNo);

    /**
     * 修改创客微信信息
     *
     * @param partnerEntity
     * @param openid
     * @param sessionKey
     */
    void partnerUpdate(PartnerEntity partnerEntity, String openid, String sessionKey);

    /**
     * 根据账号密码查询合伙人
     *
     * @param phoneNumber
     * @param password
     * @return
     */
    PartnerEntity findByPhoneNumberAndLoginPwd(String phoneNumber, String password);

    /**
     * 平台查询所有合伙人
     *
     * @param page
     * @param partnerListDTO
     * @return
     */
    R<IPage<PartnerListVO>> queryPartnerList(PartnerListDTO partnerListDTO, IPage<PartnerListVO> page);

    /**
     * 修改合伙人状态
     *
     * @param partnerId
     * @param partnerState
     * @return
     */
    R<String> updatePartnerState(Long partnerId, AccountState partnerState);

    /**
     * 添加合伙人
     *
     * @param addPartnerDTO
     * @return
     */
    R<String> createPartner(AddPartnerDTO addPartnerDTO);

    /**
     * 查询合伙人基本信息
     *
     * @param partnerId
     * @return
     */
    R<BaseInfoVO> queryPartnerInfo(Long partnerId);

    /**
     * 根据ID查询合伙人
     *
     * @param partnerId
     * @return
     */
    int queryCountById(Long partnerId);

    /**
     * 查询合伙人详情
     *
     * @param partnerId
     * @return
     */
    R<PartnerDetailVO> queryCurrentPartnerDetail(Long partnerId);

    /**
     * 修改合伙人详情
     *
     * @param updatePartnerDeatilDTO
     * @param partnerEntity
     * @return
     */
    R<String> updatePartnerDetail(UpdatePartnerDeatilDTO updatePartnerDeatilDTO, PartnerEntity partnerEntity);

    /**
     * 修改手机号码
     *
     * @param updatePhoneNumberDTO
     * @param partnerEntity
     * @return
     */
    R<String> updatePhoneNumber(UpdatePhoneNumberDTO updatePhoneNumberDTO, PartnerEntity partnerEntity);

    /**
     *查询合伙人需要签署的合同
     */
    R queryCooperationNeedContract(Long partnerId);

}

