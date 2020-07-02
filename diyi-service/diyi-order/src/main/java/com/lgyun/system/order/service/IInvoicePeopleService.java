package com.lgyun.system.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.system.order.entity.InvoicePeopleEntity;

import java.util.List;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
public interface IInvoicePeopleService extends IService<InvoicePeopleEntity> {

    /**
     * 通过创客id查询开票人
     */
    List<InvoicePeopleEntity> findInvoicePeopleMakerId(Long makerId);
}

