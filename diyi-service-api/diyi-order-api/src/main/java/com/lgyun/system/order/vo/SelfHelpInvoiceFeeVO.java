package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.SelfHelpInvoiceFeeEntity;
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
@ApiModel(value = "SelfHelpInvoiceFeeVO对象", description = "SelfHelpInvoiceFeeVO对象")
public class SelfHelpInvoiceFeeVO extends SelfHelpInvoiceFeeEntity {
    private static final long serialVersionUID = 1L;
}
