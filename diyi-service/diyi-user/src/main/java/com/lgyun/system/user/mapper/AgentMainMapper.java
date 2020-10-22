package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.admin.QueryAgentMainDTO;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.vo.admin.AdminAgentMainServiceProviderListVO;
import com.lgyun.system.user.vo.admin.AdminAgentMainVO;
import com.lgyun.system.user.vo.admin.AgentMainServiceProviderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 渠道商信息表 Mapper
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
@Mapper
public interface AgentMainMapper extends BaseMapper<AgentMainEntity> {

    List<AdminAgentMainVO> getAgentMainList(QueryAgentMainDTO queryAgentMainDTO, IPage<AdminAgentMainVO> page);


    List<AdminAgentMainServiceProviderListVO> getAgentMainServiceProviderList(Long agentMainId);

    List<AgentMainServiceProviderVO> queryAgentMainServiceProvider(String serviceProviderName, IPage<AgentMainServiceProviderVO> page);
}

