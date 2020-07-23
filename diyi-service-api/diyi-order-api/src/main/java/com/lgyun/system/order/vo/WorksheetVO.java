package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.WorksheetEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/2.
 * @time 16:30.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorksheetVO对象", description = "WorksheetVO对象")
public class WorksheetVO extends WorksheetEntity {
	private static final long serialVersionUID = 1L;

}
