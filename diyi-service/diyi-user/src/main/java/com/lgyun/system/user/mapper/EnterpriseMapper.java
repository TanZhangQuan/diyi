package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListPaymentDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListPaymentDTO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.EnterprisesDetailVO;
import com.lgyun.system.user.vo.admin.QueryEnterpriseListPaymentVO;
import com.lgyun.system.user.vo.admin.QueryServiceProviderListPaymentVO;
import com.lgyun.system.user.vo.admin.QueryEnterpriseIdAndNameListVO;
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
    List<QueryEnterpriseListPaymentVO> queryEnterpriseList(QueryEnterpriseListPaymentDTO queryEnterpriseListPaymentDTO, IPage<QueryEnterpriseListPaymentVO> page);

    /**
     * 查询所有服务商
     *
     * @param queryServiceProviderListPaymentDTO
     * @param page
     * @return
     */
    List<QueryServiceProviderListPaymentVO> queryServiceProviderList(QueryServiceProviderListPaymentDTO queryServiceProviderListPaymentDTO, IPage<QueryServiceProviderListPaymentVO> page);

    /**
     * 查询所有商户的编号名称
     *
     * @param enterpriseName
     * @param page
     * @return
     */
    List<QueryEnterpriseIdAndNameListVO> queryEnterpriseIdAndNameList(String enterpriseName, IPage<QueryEnterpriseIdAndNameListVO> page);
}

