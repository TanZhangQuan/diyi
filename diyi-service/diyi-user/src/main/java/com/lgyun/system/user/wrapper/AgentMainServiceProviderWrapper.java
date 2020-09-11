package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.AgentMainServiceProviderEntity;
import com.lgyun.system.user.vo.AgentMainServiceProviderVO;

/**
 * 渠道商-服务商关联表包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
public class AgentMainServiceProviderWrapper extends BaseEntityWrapper<AgentMainServiceProviderEntity, AgentMainServiceProviderVO> {

    public static AgentMainServiceProviderWrapper build() {
        return new AgentMainServiceProviderWrapper();
    }

    @Override
    public AgentMainServiceProviderVO entityVO(AgentMainServiceProviderEntity agentMainServiceProvider) {
        return BeanUtil.copy(agentMainServiceProvider, AgentMainServiceProviderVO.class);
    }

}
