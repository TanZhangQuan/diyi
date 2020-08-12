package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.InvoiceApplicationEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author .
 * @date 2020/8/11.
 * @time 16:20.
 */
@Data
@ApiModel(value = "InvoiceApplicationVO对象", description = "InvoiceApplicationVO对象")
public class InvoiceApplicationVO extends InvoiceApplicationEntity {
    private static final long serialVersionUID = 1L;
}
