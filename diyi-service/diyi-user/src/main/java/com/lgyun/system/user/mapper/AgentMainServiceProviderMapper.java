package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.AgentMainServiceProviderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.CooperationServiceProviderListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 渠道商-服务商关联表 Mapper
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Mapper
public interface AgentMainServiceProviderMapper extends BaseMapper<AgentMainServiceProviderEntity> {

    /**
     * 查询渠道商合作服务商
     *
     * @param enterpriseId
     * @param serviceProviderName
     * @param page
     * @return
     */
    List<CooperationServiceProviderListVO> queryCooperationServiceProviderList(Long enterpriseId, String serviceProviderName, IPage<CooperationServiceProviderListVO> page);
}

