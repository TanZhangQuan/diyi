package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListDTO;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListPaymentDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListPaymentDTO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.EnterpriseIdNameListVO;
import com.lgyun.system.user.vo.EnterprisesDetailVO;
import com.lgyun.system.user.vo.admin.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
@Mapper
public interface EnterpriseMapper extends BaseMapper<EnterpriseEntity> {

    /**
     * 根据商户ID查询商户详情
     *
     * @param enterpriseId
     * @return
     */
    EnterprisesDetailVO getEnterpriseDetailById(Long enterpriseId);

    /**
     * 查询所有商户
     *
     * @param page
     * @return
     */
    List<QueryEnterpriseListPaymentVO> queryEnterpriseListPayment(QueryEnterpriseListPaymentDTO queryEnterpriseListPaymentDTO, IPage<QueryEnterpriseListPaymentVO> page);

    /**
     * 查询所有服务商
     *
     * @param queryServiceProviderListPaymentDTO
     * @param page
     * @return
     */
    List<QueryServiceProviderListPaymentVO> queryServiceProviderListPayment(QueryServiceProviderListPaymentDTO queryServiceProviderListPaymentDTO, IPage<QueryServiceProviderListPaymentVO> page);

    /**
     * 查询所有商户的编号名称
     *
     * @param enterpriseName
     * @param page
     * @return
     */
    List<EnterpriseIdNameListVO> queryEnterpriseListNaturalPersonMaker(String enterpriseName, IPage<EnterpriseIdNameListVO> page);

    /**
     * 商户管理模块查询所有商户
     *
     * @param queryEnterpriseListDTO
     * @param page
     * @return
     */
    List<QueryEnterpriseListEnterpriseVO> queryEnterpriseListEnterprise(QueryEnterpriseListDTO queryEnterpriseListDTO, IPage<QueryEnterpriseListEnterpriseVO> page);

    /**
     * 商户管理模块查询商户基本信息
     *
     * @param enterpriseId
     * @return
     */
    QueryEnterpriseDetailEnterpriseVO queryEnterpriseDetailEnterprise(Long enterpriseId);

    /**
     * 查询商户合作服务商
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    List<QueryCooperationServiceProviderListVO> queryCooperationServiceProviderList(Long enterpriseId, IPage<QueryCooperationServiceProviderListVO> page);

    /**
     * 查询商户编号名称
     *
     * @param enterpriseId
     * @return
     */
    EnterpriseIdNameListVO queryEnterpriseIdAndName(Long enterpriseId);
}

