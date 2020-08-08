package com.lgyun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 参数配置 实体类
 *
 * @author liangfeihu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_param")
public class Param extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 参数名
     */
    @ApiModelProperty(value = "参数名")
    private String paramName;

    /**
     * 参数键
     */
    @ApiModelProperty(value = "参数键")
    private String paramKey;

    /**
     * 参数值
     */
    @ApiModelProperty(value = "参数值")
    private String paramValue;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
