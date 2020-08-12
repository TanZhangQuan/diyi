package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.PlatformInvoiceListEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author .
 * @date 2020/8/11.
 * @time 14:28.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PlatformInvoiceListVO对象", description = "PlatformInvoiceListVO对象")
public class PlatformInvoiceListVO extends PlatformInvoiceListEntity {
    private static final long serialVersionUID = 1L;
}
