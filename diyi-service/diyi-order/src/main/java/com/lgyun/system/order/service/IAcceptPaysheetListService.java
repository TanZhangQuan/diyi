package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.AcceptPaysheetListEntity;

import java.util.List;

/**
 * 商户支付回单表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-10-29 19:55:38
 */
public interface IAcceptPaysheetListService extends BaseService<AcceptPaysheetListEntity> {

    /**
     * 查询总包支付清单的交付支付验收单关联记录ID
     *
     * @param payEnterpriseId
     */
    List<Long> queryAcceptPaysheetIdList(Long payEnterpriseId);

    /**
     * 删除交付支付验收单关联记录
     *
     * @param acceptPaysheetId
     */
    void deleteAcceptPaysheetList(Long acceptPaysheetId);
}

