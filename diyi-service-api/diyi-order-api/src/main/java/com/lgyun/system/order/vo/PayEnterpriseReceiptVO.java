package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "EnterprisePayReceiptVO对象", description = "EnterprisePayReceiptVO对象")
public class PayEnterpriseReceiptVO extends PayEnterpriseReceiptEntity {
    private static final long serialVersionUID = 1L;
}
