package com.lgyun.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 授权请求
 *
 * @author tzq
 * @since 2020/8/24 18:19
 */
@Data
public class GrantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单集合")
    private List<Long> menuIds;

    @ApiModelProperty(value = "账号Id")
    private Long accountId;

    @ApiModelProperty(value = "创建者UserId")
    private Long userId;

}
