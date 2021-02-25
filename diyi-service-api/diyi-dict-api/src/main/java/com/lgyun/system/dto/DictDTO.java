package com.lgyun.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "添加或编辑字典DTO")
public class DictDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典ID(编辑须传)")
    private Long dictId;

    @ApiModelProperty(value = "上级字典ID")
    private Long parentId;

    @ApiModelProperty(value = "字典码")
    @NotBlank(message = "请输入字典码")
    private String code;

    @ApiModelProperty(value = "字典值")
    @NotBlank(message = "请输入字典值")
    private String dictKey;

    @ApiModelProperty(value = "字典名称")
    @NotBlank(message = "请输入字典名称")
    private String dictValue;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "字典备注")
    private String remark;

}
