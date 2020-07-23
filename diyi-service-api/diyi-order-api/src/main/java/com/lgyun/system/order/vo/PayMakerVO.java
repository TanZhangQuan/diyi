package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.PayMakerEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/18.
 * @time 20:55.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PayMakerVO对象", description = "PayMakerVO对象")
public class PayMakerVO extends PayMakerEntity {
}
