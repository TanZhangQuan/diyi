package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.PositionName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "创建子账号的参数")
public class ChildAccountDTO {

    /**
     * 子账号ID
     */
    @ApiModelProperty("子账号ID(修改时传)")
    private Long childAccountId;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空！")
    private String name;

    /**
     * 岗位性质
     */
    @ApiModelProperty("岗位性质")
    @NotNull(message = "岗位性质不能为空！")
    private PositionName positionName;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @NotBlank(message = "手机号码不能为空！")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phoneNumber;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空！")
    private String userName;

    /**
     * 初始密码
     */
    @ApiModelProperty("初始密码(为空时不修改密码)")
    private String passWord;

    /**
     * 拥有的角色ID
     */
    @ApiModelProperty("拥有的角色ID(角色ID为0时为超级管理员拥有所以权限)")
    @NotNull(message = "角色ID不能为空！")
    private Long roleId;

    /**
     * 是否有建立子账号的权限（为true是有权限创建）
     */
    @ApiModelProperty("是否有建立子账号的权限（为true是有权限创建）")
    @NotNull(message = "建立子账号的权限不能为空！")
    private Boolean adminPower;

}
