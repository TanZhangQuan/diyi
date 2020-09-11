package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.vo.AgentMainVO;

/**
 * 渠道商表包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
public class AgentMainWrapper extends BaseEntityWrapper<AgentMainEntity, AgentMainVO> {

    public static AgentMainWrapper build() {
        return new AgentMainWrapper();
    }

    @Override
    public AgentMainVO entityVO(AgentMainEntity agentMain) {
        return BeanUtil.copy(agentMain, AgentMainVO.class);
    }

}
