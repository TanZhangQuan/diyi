package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.PositionEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 00:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PositionVO对象", description = "PositionVO对象")
public class PositionVO extends PositionEntity {
    private static final long serialVersionUID = 1L;


}
