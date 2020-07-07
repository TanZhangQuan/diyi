package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.AddressEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 视图实体类
 * @author jun
 * @date 2020/6/29.
 * @time 14:49.
 */
@Data
@ApiModel(value = "AddressVO对象", description = "AddressVO对象")
public class PaysheetVO extends AddressEntity {
    private static final long serialVersionUID = 1L;

}
