package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListDTO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.EnterprisesDetailVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.vo.admin.QueryEnterpriseListVO;
import com.lgyun.system.user.vo.admin.QueryServiceProviderListVO;
import com.lgyun.system.user.vo.enterprise.EnterpriseResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商户信息 Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public interface IEnterpriseService extends BaseService<EnterpriseEntity> {

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
     * 获取商户合作服务商
     *
     * @param query
     * @param enterpriseId
     * @return
     */
    R<IPage<ServiceProviderIdNameListVO>> getServiceProviders(Query query, Long enterpriseId);

    /**
     * 获取商户基本信息
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
     * 根据商户ID获取商户详情
     *
     * @param enterpriseId
     * @return
     */
    R<EnterprisesDetailVO> getEnterpriseDetailById(Long enterpriseId);

    /**
     * 查询所有商户
     *
     * @param page
     * @return
     */
    R<IPage<QueryEnterpriseListVO>> queryEnterpriseList(QueryEnterpriseListDTO queryEnterpriseListDTO, IPage<QueryEnterpriseListVO> page);

    /**
     * 查询所有服务商
     *
     * @param queryServiceProviderListDTO
     * @param page
     * @return
     */
    R<IPage<QueryServiceProviderListVO>> queryServiceProviderList(QueryServiceProviderListDTO queryServiceProviderListDTO, IPage<QueryServiceProviderListVO> page);
}

