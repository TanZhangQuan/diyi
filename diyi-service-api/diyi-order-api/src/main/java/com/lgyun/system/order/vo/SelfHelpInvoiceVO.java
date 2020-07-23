package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图实体类
 * @author jun
 * @date 2020/6/29.
 * @time 14:49.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SelfHelpInvoiceVO对象", description = "SelfHelpInvoiceVO对象")
public class SelfHelpInvoiceVO extends SelfHelpInvoiceEntity {
    private static final long serialVersionUID = 1L;

}
