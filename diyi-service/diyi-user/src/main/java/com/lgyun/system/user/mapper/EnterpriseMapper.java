package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListDTO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.EnterprisesDetailVO;
import com.lgyun.system.user.vo.admin.QueryEnterpriseListVO;
import com.lgyun.system.user.vo.admin.QueryServiceProviderListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Mapper
public interface EnterpriseMapper extends BaseMapper<EnterpriseEntity> {

    /**
     * 根据商户ID获取商户详情
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
    List<QueryEnterpriseListVO> queryEnterpriseList(QueryEnterpriseListDTO queryEnterpriseListDTO, IPage<QueryEnterpriseListVO> page);

    /**
     * 查询所有服务商
     *
     * @param queryServiceProviderListDTO
     * @param page
     * @return
     */
    List<QueryServiceProviderListVO> queryServiceProviderList(QueryServiceProviderListDTO queryServiceProviderListDTO, IPage<QueryServiceProviderListVO> page);
}

