package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_pay_receipt")
public class PayReceiptEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客支付ID
     */
    private Long payId;

    /**
     * 支付回单
     */
    private String makerPayReceiptUrl;

}
