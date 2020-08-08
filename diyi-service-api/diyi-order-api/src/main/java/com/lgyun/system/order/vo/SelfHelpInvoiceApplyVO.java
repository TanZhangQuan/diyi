package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.SelfHelpInvoiceApplyEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author .
 * @date 2020/8/8.
 * @time 10:38.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SelfHelpInvoiceApplyVO对象", description = "SelfHelpInvoiceApplyVO对象")
public class SelfHelpInvoiceApplyVO extends SelfHelpInvoiceApplyEntity {
    private static final long serialVersionUID = 1L;
}
