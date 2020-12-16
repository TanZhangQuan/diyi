package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.ContactsInfoDTO;
import com.lgyun.system.user.dto.CreateEnterpriseDTO;
import com.lgyun.system.user.dto.EnterpriseListDTO;
import com.lgyun.system.user.dto.UpdateEnterpriseDTO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.*;

/**
 * 商户信息 Service 接口
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
public interface IEnterpriseService extends BaseService<EnterpriseEntity> {

    /**
     * 根据ID查询商户是否存在
     *
     * @param enterpriseId
     * @return
     */
    int queryCountById(Long enterpriseId);

    /**
     * 通过商户名字查询
     *
     * @param enterpriseName
     * @return
     */
    R<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName);

    /**
     * 通过商户id查询
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId);

    /**
     * 查询商户基本信息
     *
     * @param enterpriseId
     * @return
     */
    R<EnterpriseInfoVO> queryEnterpriseInfo(Long enterpriseId);

    /**
     * 根据商户ID查询商户详情
     *
     * @param enterpriseId
     * @return
     */
    R<EnterprisesDetailVO> getEnterpriseDetailById(Long enterpriseId);

    /**
     * 支付管理模块查询所有商户
     *
     * @param enterpriseName
     * @param page
     * @return
     */
    R<IPage<EnterpriseListPaymentVO>> queryEnterpriseListPayment(String enterpriseName, IPage<EnterpriseListPaymentVO> page);

    /**
     * 添加商户
     *
     * @param createEnterpriseDTO
     * @param agentMainId
     * @param partnerId
     * @return
     */
    R<String> createEnterprise(CreateEnterpriseDTO createEnterpriseDTO, Long agentMainId, Long partnerId);

    /**
     * 编辑商户
     *
     * @param updateEnterpriseDTO
     * @param agentMainId
     * @param partnerId
     * @return
     */
    R<String> updateEnterprise(UpdateEnterpriseDTO updateEnterpriseDTO, Long agentMainId, Long partnerId);

    /**
     * 查询所有商户
     *
     * @param enterpriseListDTO
     * @param page
     * @return
     */
    R<IPage<EnterpriseListAdminVO>> queryEnterpriseList(Long agentMainId, Long relBureauId, EnterpriseListDTO enterpriseListDTO, IPage<EnterpriseListAdminVO> page);

    /**
     * 查询编辑商户详情
     *
     * @param enterpriseId
     * @return
     */
    R<EnterpriseUpdateDetailVO> queryEnterpriseUpdateDetail(Long enterpriseId);

    /**
     * 更改商户状态
     *
     * @param enterpriseId
     * @param enterpriseState
     * @return
     */
    R<String> updateEnterpriseState(Long enterpriseId, AccountState enterpriseState);

    /**
     * 查询所有商户
     *
     * @param page
     * @return
     */
    R getEnterpriseAll(Long enterpriseId, String enterpriseName, IPage<EnterpriseEntity> page);

    /**
     * 修改商户的企业网址
     *
     * @param enterpriseId
     * @param enterpriseUrl
     * @return
     */
    R<String> updateEnterpriseUrl(Long enterpriseId, String enterpriseUrl);

    /**
     * 查询商户的联系人
     *
     * @param enterpriseId
     * @return
     */
    R<ContactInfoVO> queryContact(Long enterpriseId);

    /**
     * 修改商户的联系人
     *
     * @param enterpriseId
     * @param contactsInfoDTO
     * @return
     */
    R<String> updateContact(Long enterpriseId, ContactsInfoDTO contactsInfoDTO);

    /**
     * 查询商户的开票信息
     *
     * @param enterpriseId
     * @return
     */
    R<InvoiceVO> queryeInvoice(Long enterpriseId);

    /**
     * 查询商户编号和名称
     *
     * @param serviceProviderId
     * @param partnerId
     * @param enterpriseName
     * @param page
     * @return
     */
    R<IPage<EnterpriseIdNameListVO>> queryEnterpriseIdAndNameList(Long serviceProviderId, Long partnerId, String enterpriseName, IPage<EnterpriseIdNameListVO> page);

    /**
     * 查询商户详情
     *
     * @param enterpriseId
     * @return
     */
    R<EnterprisesDetailAgentMainVO> queryEnterpriseDetailAgentMain(Long enterpriseId);

    /**
     * @param enterpriseId
     * @return
     */
    R<EnterprisesDetailPartnerVO> queryEnterpriseDetailPartner(Long enterpriseId);

    /**
     * 根据商户名字查询商户
     */
    EnterpriseEntity queryEnterpriseName(String enterpriseName);

    /**
     *生成二维码
     */
    R createQrCode(String linkUrl) throws Exception;
}

