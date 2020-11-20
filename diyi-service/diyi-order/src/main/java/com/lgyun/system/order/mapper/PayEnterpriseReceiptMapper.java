package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 *
 * @author tzq
 * @since 2020-07-17 20:01:13
 */
@Mapper
public interface PayEnterpriseReceiptMapper extends BaseMapper<PayEnterpriseReceiptEntity> {

    /**
     * 删除总包支付回单
     *
     * @param payEnterpriseId
     */
    void deleteByPayEnterpriseId(Long payEnterpriseId);

}

