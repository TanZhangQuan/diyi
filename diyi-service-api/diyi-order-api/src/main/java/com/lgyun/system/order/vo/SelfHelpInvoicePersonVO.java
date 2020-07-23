package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:40.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SelfHelpInvoicePersonVO对象", description = "SelfHelpInvoicePersonVO对象")
public class SelfHelpInvoicePersonVO extends SelfHelpInvoicePersonEntity {
    private static final long serialVersionUID = 1L;
}
