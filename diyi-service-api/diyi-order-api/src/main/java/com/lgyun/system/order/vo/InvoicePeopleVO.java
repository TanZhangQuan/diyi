package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.InvoicePeopleEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 视图实体类
 * @author jun
 * @date 2020/6/29.
 * @time 14:49.
 */
@Data
@ApiModel(value = "InvoicePeopleVO对象", description = "InvoicePeopleVO对象")
public class InvoicePeopleVO extends InvoicePeopleEntity {
    private static final long serialVersionUID = 1L;

}
