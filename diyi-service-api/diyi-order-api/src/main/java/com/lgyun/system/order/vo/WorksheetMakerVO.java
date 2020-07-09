package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.WorksheetMakerEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jun.
 * @date 2020/7/2.
 * @time 16:30.
 */
@Data
@ApiModel(value = "InvoicePeopleVo对象", description = "InvoicePeopleVo对象")
public class WorksheetMakerVO extends WorksheetMakerEntity {
    private static final long serialVersionUID = 1L;
}
