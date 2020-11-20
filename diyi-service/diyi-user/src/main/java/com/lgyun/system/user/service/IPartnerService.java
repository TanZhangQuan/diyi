package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddPartnerDTO;
import com.lgyun.system.user.dto.QueryPartnerDTO;
import com.lgyun.system.user.dto.UpdatePartnerDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.vo.PartnerVO;

/**
 * 合伙人信息表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
public interface IPartnerService extends BaseService<PartnerEntity> {

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
     * @param queryPartnerDTO
     * @return
     */
    R<IPage<PartnerVO>> getPartnerList(IPage<PartnerVO> page, QueryPartnerDTO queryPartnerDTO);

    /**
     * 修改合伙人状态
     *
     * @param partnerId
     * @return
     */
    R updateIllegal(Long partnerId, AccountState accountState, AdminEntity adminEntity);

    /**
     * 添加合伙人
     *
     * @param addPartnerDTO
     * @param adminEntity
     * @return
     */
    R addPartner(AddPartnerDTO addPartnerDTO, AdminEntity adminEntity);

    /**
     * 编辑合伙人信息
     *
     * @param updatePartnerDTO
     * @param adminEntity
     * @return
     */
    R updatePartner(UpdatePartnerDTO updatePartnerDTO, AdminEntity adminEntity);

}

