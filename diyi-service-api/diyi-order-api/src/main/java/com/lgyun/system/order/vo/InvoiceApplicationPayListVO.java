package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.InvoiceApplicationPayListEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author .
 * @date 2020/8/11.
 * @time 16:19.
 */
@Data
@ApiModel(value = "InvoiceApplicationPayListVO对象", description = "InvoiceApplicationPayListVO对象")
public class InvoiceApplicationPayListVO extends InvoiceApplicationPayListEntity {
    private static final long serialVersionUID = 1L;
}
