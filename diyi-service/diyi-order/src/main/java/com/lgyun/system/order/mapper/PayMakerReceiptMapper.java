package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.entity.PayMakerReceiptEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 *
 * @author tzq
 * @since 2020-08-04 14:20:06
 */
@Mapper
public interface PayMakerReceiptMapper extends BaseMapper<PayMakerReceiptEntity> {

    /**
     * 删除分包支付回单
     *
     * @param payMakerId
     * @return
     */
    void deleteByPayMakerId(Long payMakerId);
}

