package com.lgyun.system.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.system.order.entity.AddressEntity;

import java.util.List;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
public interface IAddressService extends IService<AddressEntity> {

    /**
     * 通过购买方id查询收件地址
     */
    List<AddressEntity> findAddressCompanyId(Long companyId);
}

