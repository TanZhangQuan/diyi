package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.ServiceProvidersVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
@Mapper
public interface ServiceProviderMapper extends BaseMapper<ServiceProviderEntity> {

    /**
     * 获取当前商户合作服务商
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    //TODO
    List<ServiceProvidersVO> getServiceProvidersByEnterpriseId(Long payEnterpriseId, IPage<ServiceProvidersVO> page);
}

