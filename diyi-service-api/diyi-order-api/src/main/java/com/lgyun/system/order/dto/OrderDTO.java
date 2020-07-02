package com.lgyun.system.order.dto;

import com.lgyun.system.order.entity.OrderEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据传输对象实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 19:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDTO extends OrderEntity {
    private static final long serialVersionUID = 1L;

}
