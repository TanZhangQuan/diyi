package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.WorksheetEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 视图实体类
 *
 * @author jun
 * @since 2020/6/6 19:18
 */
@Data
@ApiModel(value = "WorksheetVO对象", description = "WorksheetVO对象")
public class WorksheetVO extends WorksheetEntity {
	private static final long serialVersionUID = 1L;

}
