package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.PayEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 视图实体类
 *
 * @author jun
 * @since 2020/6/6 19:18
 */
@Data
@ApiModel(value = "PayVO对象", description = "PayVO对象")
public class PayVO extends PayEntity {
	private static final long serialVersionUID = 1L;
}
