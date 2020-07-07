package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.IndividualBusinessEntity;
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
@ApiModel(value = "IndividualBusinessVO对象", description = "IndividualBusinessVO对象")
public class IndividualBusinessVO extends IndividualBusinessEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 经营者
     */
    private String bizName;

}
