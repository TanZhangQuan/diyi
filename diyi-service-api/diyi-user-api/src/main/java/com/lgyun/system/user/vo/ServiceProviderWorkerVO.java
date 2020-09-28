package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 视图实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 00:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceProviderWorkerVO extends ServiceProviderWorkerEntity {
    private static final long serialVersionUID = 1L;

    private String accountStateValue;

    private String accountStateDesc;

    private String workerSexValue;

    private String workerSexDesc;

    private String positionNameValue;

    private String positionNameDesc;

    @ApiModelProperty(value = "菜单集合,勾选菜单title集合")
    private List<String> menuNameList;

    @ApiModelProperty(value = "菜单集合,勾选菜单Id集合")
    private List<String> menuIds;
}
