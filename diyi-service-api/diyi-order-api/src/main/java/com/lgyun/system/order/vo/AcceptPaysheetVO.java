package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 视图实体类
 * @author jun
 * @date 2020/6/29.
 * @time 14:49.
 */
@Data
@ApiModel(value = "AcceptPaysheetVO对象", description = "AcceptPaysheetVO对象")
public class AcceptPaysheetVO extends AcceptPaysheetEntity {
    private static final long serialVersionUID = 1L;

}
