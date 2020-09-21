package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.*;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.EnterpriseIdNameListVO;
import com.lgyun.system.user.vo.EnterprisesDetailVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.vo.admin.*;
import com.lgyun.system.user.vo.enterprise.EnterpriseResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商户信息 Service 接口
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
public interface IEnterpriseService extends BaseService<EnterpriseEntity> {

    /**
     * 查询商户名字是否已存在
     *
     * @param enterpriseName
     * @param enterpriseId
     * @return
     */
    Integer findCountByEnterpriseName(String enterpriseName, Long enterpriseId);

    /**
     * 查询统一社会信用代码是否已存在
     *
     * @param socialCreditNo
     * @param enterpriseId
     * @return
     */
    Integer findCountBySocialCreditNo(String socialCreditNo, Long enterpriseId);

    /**
     * 通过商户名字查询
     *
     * @param enterpriseName
     * @return
     */
    MakerEnterpriseRelationVO getEnterpriseName(String enterpriseName);

    /**
     * 通过商户id查询
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId);

    /**
     * 查询商户合作服务商
     *
     * @param query
     * @param enterpriseId
     * @return
     */
    R<IPage<ServiceProviderIdNameListVO>> getServiceProviders(Query query, Long enterpriseId);

    /**
     * 查询商户基本信息
     *
     * @param enterpriseId
     * @return
     */
    R<EnterpriseResponse> getBasicEnterpriseResponse(Long enterpriseId);

    /**
     * 上传商户营业执照
     *
     * @param enterpriseId
     * @param file
     * @return
     */
    void uploadEnterpriseLicence(Long enterpriseId, MultipartFile file) throws Exception;

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
     * @param page
     * @return
     */
    R<IPage<QueryEnterpriseListPaymentVO>> queryEnterpriseListPayment(QueryEnterpriseListPaymentDTO queryEnterpriseListPaymentDTO, IPage<QueryEnterpriseListPaymentVO> page);

    /**
     * 支付管理模块查询所有服务商
     *
     * @param queryServiceProviderListPaymentDTO
     * @param page
     * @return
     */
    R<IPage<QueryServiceProviderListPaymentVO>> queryServiceProviderListPayment(QueryServiceProviderListPaymentDTO queryServiceProviderListPaymentDTO, IPage<QueryServiceProviderListPaymentVO> page);

    /**
     * 查询所有商户的编号名称
     *
     * @param enterpriseName
     * @param page
     * @return
     */
    R<IPage<EnterpriseIdNameListVO>> queryEnterpriseListNaturalPersonMaker(String enterpriseName, IPage<EnterpriseIdNameListVO> page);

    /**
     * 添加商户
     *
     * @param addEnterpriseDTO
     * @param adminEntity
     * @return
     */
    R<String> createEnterprise(AddEnterpriseDTO addEnterpriseDTO, AdminEntity adminEntity);

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
     * @param queryEnterpriseListDTO
     * @param page
     * @return
     */
    R<IPage<QueryEnterpriseListEnterpriseVO>> queryEnterpriseListEnterprise(QueryEnterpriseListDTO queryEnterpriseListDTO, IPage<QueryEnterpriseListEnterpriseVO> page);

    /**
     * 商户管理模块查询商户基本信息
     *
     * @param enterpriseId
     * @return
     */
    R<QueryEnterpriseDetailEnterpriseVO> queryEnterpriseDetailEnterprise(Long enterpriseId);

    /**
     * 更改商户状态
     *
     * @param enterpriseId
     * @param enterpriseState
     * @return
     */
    R<String> updateEnterpriseState(Long enterpriseId, AccountState enterpriseState);

    /**
     * 查询商户合作服务商
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<QueryCooperationServiceProviderListVO>> queryCooperationServiceProviderList(Long enterpriseId, IPage<QueryCooperationServiceProviderListVO> page);

    /**
     * 查询商户编号名称
     *
     * @param enterpriseId
     * @return
     */
    R<EnterpriseIdNameListVO> queryEnterpriseIdAndName(Long enterpriseId);

}

