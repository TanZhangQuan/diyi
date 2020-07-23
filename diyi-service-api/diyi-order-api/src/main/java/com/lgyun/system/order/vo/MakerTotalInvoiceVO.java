package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.MakerTotalInvoiceEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/18.
 * @time 20:54.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MakerTotalInvoiceVO对象", description = "MakerTotalInvoiceVO对象")
public class MakerTotalInvoiceVO extends MakerTotalInvoiceEntity {
}
