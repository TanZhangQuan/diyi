package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.EnterpriseServiceProviderEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author .
 * @date 2020/7/28.
 * @time 14:55.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "EnterpriseProviderVO对象", description = "EnterpriseProviderVO对象")
public class EnterpriseServiceProviderVO extends EnterpriseServiceProviderEntity {
    private static final long serialVersionUID = 1L;
}
