package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.vo.AgentMainWorkerVO;

/**
 * 渠道商员工表包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
public class AgentMainWorkerWrapper extends BaseEntityWrapper<AgentMainWorkerEntity, AgentMainWorkerVO> {

    public static AgentMainWorkerWrapper build() {
        return new AgentMainWorkerWrapper();
    }

    @Override
    public AgentMainWorkerVO entityVO(AgentMainWorkerEntity agentMainWorker) {
        return BeanUtil.copy(agentMainWorker, AgentMainWorkerVO.class);
    }

}
