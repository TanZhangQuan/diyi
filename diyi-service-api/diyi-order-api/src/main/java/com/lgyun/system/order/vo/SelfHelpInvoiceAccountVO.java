package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.SelfHelpInvoiceAccountEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceAccountVO对象", description = "SelfHelpInvoiceAccountVO对象")
public class SelfHelpInvoiceAccountVO extends SelfHelpInvoiceAccountEntity {
    private static final long serialVersionUID = 1L;
}
