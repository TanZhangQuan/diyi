package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
public class EnterpriseWorkerWrapper extends BaseEntityWrapper<EnterpriseWorkerEntity, EnterpriseWorkerVO> {

    private static IEnterpriseService enterpriseService;

    static {
        enterpriseService = SpringUtil.getBean(IEnterpriseService.class);
    }

    public static EnterpriseWorkerWrapper build() {
        return new EnterpriseWorkerWrapper();
    }

    @Override
    public EnterpriseWorkerVO entityVO(EnterpriseWorkerEntity enterpriseWorker) {

        if (enterpriseWorker == null) {
            return null;
        }

        EnterpriseWorkerVO enterpriseWorkerVO = BeanUtil.copy(enterpriseWorker, EnterpriseWorkerVO.class);
        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseWorkerVO.getEnterpriseId());
        if (enterpriseEntity != null) {
            enterpriseWorkerVO.setEnterpriseName(enterpriseEntity.getEnterpriseName());
        }

        return enterpriseWorkerVO;
    }

}
