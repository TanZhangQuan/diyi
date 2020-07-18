package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.WorksheetAttentionEntity;
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
@ApiModel(value = "WorksheetAttentionVO对象", description = "WorksheetAttentionVO对象")
public class WorksheetAttentionVO extends WorksheetAttentionEntity {
    private static final long serialVersionUID = 1L;


}
