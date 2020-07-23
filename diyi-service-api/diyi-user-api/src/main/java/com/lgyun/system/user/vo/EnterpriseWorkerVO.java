package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "EnterpriseWorkerVO对象", description = "EnterpriseWorkerVO对象")
public class EnterpriseWorkerVO extends EnterpriseWorkerEntity {
    private static final long serialVersionUID = 1L;

}