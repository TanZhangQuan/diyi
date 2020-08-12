package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.PlatformInvoicePayListEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author .
 * @date 2020/8/11.
 * @time 16:21.
 */
@Data
@ApiModel(value = "PlatformInvoiceListVO对象", description = "PlatformInvoiceListVO对象")
public class PlatformInvoicePayListVO extends PlatformInvoicePayListEntity {
    private static final long serialVersionUID = 1L;
}
