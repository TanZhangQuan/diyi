package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.MakerEntity;
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
@ApiModel(value = "MakerVO对象", description = "MakerVO对象")
public class MakerVO extends MakerEntity {
    private static final long serialVersionUID = 1L;


}
