package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.admin.QueryAgentMainDTO;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.admin.AdminAgentMainServiceProviderListVO;
import com.lgyun.system.user.vo.admin.AdminAgentMainVO;
import com.lgyun.system.user.vo.admin.AgentMainTransactionVO;
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

    /**
     * 查询渠道商下服务商流水信息
     * @param agentMainId
     * @return
     */
    AgentMainTransactionVO getTransactionByAgentMainId(Long agentMainId);

    List<AdminAgentMainServiceProviderListVO> getAgentMainServiceProviderList(Long agentMainId);
}

