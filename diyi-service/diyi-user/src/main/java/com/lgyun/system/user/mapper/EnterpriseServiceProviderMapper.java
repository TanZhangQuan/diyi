package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.EnterpriseServiceProviderEntity;
import com.lgyun.system.user.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
@Mapper
public interface EnterpriseServiceProviderMapper extends BaseMapper<EnterpriseServiceProviderEntity> {

    /**
     * 查询服务商关联的所有商户
     *
     * @param serviceProviderId
     * @param keyword
     * @param page
     * @return
     */
    List<EnterprisesVO> queryRelevanceEnterpriseList(Long serviceProviderId, String keyword, IPage<EnterprisesVO> page);

    /**
     * 查询当前商户合作服务商
     *
     * @param enterpriseId
     * @param serviceProviderName
     * @param page
     * @return
     */
    List<CooperationServiceProviderListVO> queryCooperationServiceProviderList(Long enterpriseId, String serviceProviderName, IPage<CooperationServiceProviderListVO> page);

    /**
     * 查询当前商户合作服务商
     *
     * @param serviceProviderId
     * @param enterpriseName
     * @param page
     * @return
     */
    List<CooperationEnterprisesListVO> queryCooperationEnterpriseList(Long serviceProviderId, String enterpriseName, IPage<CooperationEnterprisesListVO> page);

}

