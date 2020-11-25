package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.vo.AgentMainWorkerDetailVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 渠道商员工表 Mapper
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Mapper
public interface AgentMainWorkerMapper extends BaseMapper<AgentMainWorkerEntity> {

    /**
     * 查询当前渠道商员工详情
     *
     * @param agentMainWorkerId
     * @return
     */
    AgentMainWorkerDetailVO queryAgentMainWorkerDetail(Long agentMainWorkerId);
}

