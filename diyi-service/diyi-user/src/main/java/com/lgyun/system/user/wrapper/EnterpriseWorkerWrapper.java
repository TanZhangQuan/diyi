package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
public class EnterpriseWorkerWrapper extends BaseEntityWrapper<EnterpriseWorkerEntity, EnterpriseWorkerVO> {

    public static EnterpriseWorkerWrapper build() {
        return new EnterpriseWorkerWrapper();
    }

    @Override
    public EnterpriseWorkerVO entityVO(EnterpriseWorkerEntity enterpriseWorker) {
        return BeanUtil.copy(enterpriseWorker, EnterpriseWorkerVO.class);
    }

}
