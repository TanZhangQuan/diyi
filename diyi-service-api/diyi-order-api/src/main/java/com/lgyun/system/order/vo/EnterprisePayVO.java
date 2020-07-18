package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.EnterprisePayEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "EnterprisePayVO对象", description = "EnterprisePayVO对象")
public class EnterprisePayVO extends EnterprisePayEntity {
    private static final long serialVersionUID = 1L;
}
