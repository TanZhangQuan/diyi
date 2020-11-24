package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.CreateEnterpriseDTO;
import com.lgyun.system.user.dto.UpdateEnterpriseDTO;
import com.lgyun.system.user.dto.ContactsInfoDTO;
import com.lgyun.system.user.dto.EnterpriseListDTO;
import com.lgyun.system.user.entity.AdminEntity;
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
     * @param adminEntity
     * @return
     */
    R<String> createEnterprise(CreateEnterpriseDTO createEnterpriseDTO, AdminEntity adminEntity);

    /**
     * 编辑商户
     *
     * @param updateEnterpriseDTO
     * @param adminEntity
     * @return
     */
    R<String> updateEnterprise(UpdateEnterpriseDTO updateEnterpriseDTO, AdminEntity adminEntity);

    /**
     * 商户管理模块查询所有商户
     *
     * @param enterpriseListDTO
     * @param page
     * @return
     */
    R<IPage<EnterpriseListAdminVO>> queryEnterpriseListAdmin(EnterpriseListDTO enterpriseListDTO, IPage<EnterpriseListAdminVO> page);

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
    R<ContactsInfoVO> queryContact(Long enterpriseId);

    /**
     * 修改商户的联系人
     *
     * @param enterpriseId
     * @param contactsInfoDTO
     * @return
     */
    R<String> updateContacts(Long enterpriseId, ContactsInfoDTO contactsInfoDTO);

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
     * @param page
     * @param serviceProviderId
     * @return
     */
    R<IPage<EnterpriseIdNameListVO>> queryEnterpriseIdAndNameList(Long serviceProviderId, String enterpriseName, IPage<EnterpriseIdNameListVO> page);
}

