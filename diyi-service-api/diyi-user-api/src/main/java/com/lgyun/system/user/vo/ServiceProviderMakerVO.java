package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.ServiceProviderMakerEntity;
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
@ApiModel(value = "ServiceProviderMakerVO对象", description = "ServiceProviderMakerVO对象")
public class ServiceProviderMakerVO extends ServiceProviderMakerEntity {
    private static final long serialVersionUID = 1L;
}
