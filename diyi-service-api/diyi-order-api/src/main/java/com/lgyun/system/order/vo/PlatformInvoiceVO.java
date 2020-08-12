package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.PlatformInvoiceEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author .
 * @date 2020/8/11.
 * @time 16:22.
 */
@Data
@ApiModel(value = "PlatformInvoiceVO对象", description = "PlatformInvoiceVO对象")
public class PlatformInvoiceVO extends PlatformInvoiceEntity {
    private static final long serialVersionUID = 1L;
}
