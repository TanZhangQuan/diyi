package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.*;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.*;
import com.lgyun.system.user.vo.EnterpriseVO;
import org.springframework.web.multipart.MultipartFile;

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
     * @param id
     * @return
     */
    int queryCountById(Long id);

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
    R<EnterpriseVO> getBasicEnterpriseResponse(Long enterpriseId);

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
     * @param enterpriseName
     * @param page
     * @return
     */
    R<IPage<EnterpriseListPaymentVO>> queryEnterpriseListPayment(String enterpriseName, IPage<EnterpriseListPaymentVO> page);

    /**
     * 添加商户
     *
     * @param addOrUpdateEnterpriseDTO
     * @param adminEntity
     * @return
     */
    R<String> createOrUpdateEnterprise(AddOrUpdateEnterpriseDTO addOrUpdateEnterpriseDTO, AdminEntity adminEntity);

    /**
     * 商户管理模块查询所有商户
     *
     * @param queryEnterpriseListDTO
     * @param page
     * @return
     */
    R<IPage<EnterpriseListEnterpriseVO>> queryEnterpriseListEnterprise(QueryEnterpriseListDTO queryEnterpriseListDTO, IPage<EnterpriseListEnterpriseVO> page);

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
    R getEnterpriseAll(Long enterpriseId,String enterpriseName,IPage<EnterpriseEntity> page);

}

