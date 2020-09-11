package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.EnterpriseProviderEntity;
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
public interface EnterpriseProviderMapper extends BaseMapper<EnterpriseProviderEntity> {

    /**
     * 根据商户ID查询所有合作的服务商
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    List<ServiceProviderIdNameListVO> getServiceProviderByEnterpriseId(Long enterpriseId, String serviceProviderName, IPage<ServiceProviderIdNameListVO> page);

    /**
     * 查询服务商关联的所有商户
     *
     * @param serviceProviderId
     * @param keyword
     * @param page
     * @return
     */
    List<EnterprisesVO> getEnterpriseByServiceProvider(Long serviceProviderId, String keyword, IPage<EnterprisesVO> page);

    /**
     * 查询当前商户合作服务商
     *
     * @param enterpriseId
     * @param keyWord
     * @param page
     * @return
     */
    List<ServiceProvidersVO> getServiceProvidersByEnterpriseId(Long enterpriseId, String keyWord, IPage<ServiceProvidersVO> page);

    /**
     * 查询当前商户合作服务商
     *
     * @param serviceProviderId
     * @param keyWord
     * @param page
     * @return
     */
    List<EnterprisesByProviderVO> getEnterprtisesByServiceProviderId(Long serviceProviderId, String keyWord, IPage<EnterprisesByProviderVO> page);

    /**
     * 查询当前服务商合作商户
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    List<EnterpriseIdNameListVO> getEnterprisesByServiceProvider(Long serviceProviderId, IPage<EnterpriseIdNameListVO> page);
}

