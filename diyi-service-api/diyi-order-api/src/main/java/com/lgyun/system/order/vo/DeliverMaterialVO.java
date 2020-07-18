package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.DeliverMaterialEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "DeliverMaterialVO对象", description = "DeliverMaterialVO对象")
public class DeliverMaterialVO extends DeliverMaterialEntity {
    private static final long serialVersionUID = 1L;
}
