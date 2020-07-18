package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.EnterprisePayReceiptEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "EnterprisePayReceiptVO对象", description = "EnterprisePayReceiptVO对象")
public class EnterprisePayReceiptVO extends EnterprisePayReceiptEntity {
    private static final long serialVersionUID = 1L;
}
