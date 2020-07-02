package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.OrderEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 视图实体类
 *
 * @author jun
 * @since 2020/6/6 19:18
 */
@Data
@ApiModel(value = "OrderVO对象", description = "OrderVO对象")
public class OrderVO extends OrderEntity {
	private static final long serialVersionUID = 1L;

//	private String getOrderDate;
//
//	private String worksheetState;
//
//	private String worksheetNo;
}
