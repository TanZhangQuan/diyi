package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.AddressEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图实体类
 * @author jun
 * @date 2020/6/29.
 * @time 14:49.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkAchievementVO对象", description = "WorkAchievementVO对象")
public class WorkAchievementVO extends AddressEntity {
    private static final long serialVersionUID = 1L;

}