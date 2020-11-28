package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.AcceptPaysheetListEntity;

/**
 * 商户支付回单表 Service 接口
 *
 * @author tzq
 * @since 2020-10-29 19:55:38
 */
public interface IAcceptPaysheetListService extends BaseService<AcceptPaysheetListEntity> {

    /**
     * 删除交付支付验收单关联记录
     *
     * @param acceptPaysheetId
     */
    void deleteAcceptPaysheetList(Long acceptPaysheetId);
}

