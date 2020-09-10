package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.excel.PayEnterpriseExcel;
import com.lgyun.system.order.vo.PayMakerVO;

import java.util.List;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public interface IPayMakerService extends BaseService<PayMakerEntity> {

    /**
     * 根据商户支付清单id查询创客支付明细
     */
    List<PayMakerVO> getPayEnterpriseId(Long payEnterpriseId);

    /**
     * 根据商户支付清单id查询创客支付明细和门征发票信息
     */
    List<PayMakerVO> getPayEnterprise(Long payEnterpriseId);

    /**
     * 根据总包支付清单生成分包
     *
     * @param list
     * @param payEnterpriseEntity
     */
    void importMaker(List<PayEnterpriseExcel> list, PayEnterpriseEntity payEnterpriseEntity);
}

