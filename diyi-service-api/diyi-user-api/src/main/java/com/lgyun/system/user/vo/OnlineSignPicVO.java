package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.OnlineSignPicEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/18.
 * @time 16:01.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OnlineSignPicVO对象", description = "OnlineSignPicVO对象")
public class OnlineSignPicVO extends OnlineSignPicEntity {
    private static final long serialVersionUID = 1L;
}
