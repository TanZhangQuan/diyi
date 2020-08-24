package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.EnterpriseProviderEntity;
import com.lgyun.system.user.vo.EnterprisesVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.vo.ServiceProvidersVO;
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
     * 获取服务商关联的所有商户
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    List<EnterprisesVO> getEnterpriseByServiceProvider(Long serviceProviderId, IPage<EnterprisesVO> page);

    /**
     * 获取当前商户合作服务商
     *
     * @param enterpriseId
     * @param keyWord
     * @param page
     * @return
     */
    List<ServiceProvidersVO> getServiceProvidersByEnterpriseId(Long enterpriseId, String keyWord, IPage<ServiceProvidersVO> page);
}

