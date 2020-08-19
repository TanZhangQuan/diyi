package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 00:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ServiceProviderWorkerVO对象", description = "ServiceProviderWorkerVO对象")
public class ServiceProviderWorkerVO extends ServiceProviderWorkerEntity {
    private static final long serialVersionUID = 1L;

}