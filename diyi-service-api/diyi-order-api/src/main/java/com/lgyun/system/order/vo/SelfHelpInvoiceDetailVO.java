package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:37.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SelfHelpInvoiceDetailVO对象", description = "SelfHelpInvoiceDetailVO对象")
public class SelfHelpInvoiceDetailVO extends SelfHelpInvoiceDetailEntity {
    private static final long serialVersionUID = 1L;
}
