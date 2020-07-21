package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.PayEnterpriseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "EnterprisePayVO对象", description = "EnterprisePayVO对象")
public class PayEnterpriseVO extends PayEnterpriseEntity {
    private static final long serialVersionUID = 1L;
}
