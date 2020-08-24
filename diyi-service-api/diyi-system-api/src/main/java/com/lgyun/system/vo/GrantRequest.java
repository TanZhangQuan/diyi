package com.lgyun.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 授权请求
 *
 * @author liangfeihu
 * @since 2020/8/24 18:19
 */
@Data
@NoArgsConstructor
@ApiModel(value = "授权请求对象", description = "授权请求对象")
public class GrantRequest {

    @ApiModelProperty(value = "菜单集合")
    private List<Long> menuIds;

    /**
     * 账号Id
     */
    private Long accountId;



}
