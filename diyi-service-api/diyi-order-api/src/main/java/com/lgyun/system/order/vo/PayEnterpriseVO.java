package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.PayEnterpriseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PayEnterpriseVO对象", description = "PayEnterpriseVO对象")
public class PayEnterpriseVO extends PayEnterpriseEntity {
    private static final long serialVersionUID = 1L;
}
