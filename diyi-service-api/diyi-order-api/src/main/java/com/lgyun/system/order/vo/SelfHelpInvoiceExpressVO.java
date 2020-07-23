package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.SelfHelpInvoiceExpressEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:39.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SelfHelpInvoiceExpressVO对象", description = "SelfHelpInvoiceExpressVO对象")
public class SelfHelpInvoiceExpressVO extends SelfHelpInvoiceExpressEntity {
    private static final long serialVersionUID = 1L;
}
