package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.AgentMainEnterpriseEntity;
import com.lgyun.system.user.vo.AgentMainEnterpriseVO;

/**
 * 渠道商-商户关联表包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
public class AgentMainEnterpriseWrapper extends BaseEntityWrapper<AgentMainEnterpriseEntity, AgentMainEnterpriseVO> {

    public static AgentMainEnterpriseWrapper build() {
        return new AgentMainEnterpriseWrapper();
    }

    @Override
    public AgentMainEnterpriseVO entityVO(AgentMainEnterpriseEntity agentMainEnterprise) {
        return BeanUtil.copy(agentMainEnterprise, AgentMainEnterpriseVO.class);
    }

}
