package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.PositionName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class ChildAccountDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "子账号ID")
    private Long childAccountId;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "请输入要姓名")
    private String name;

    @ApiModelProperty(value = "岗位性质", notes = "com.lgyun.common.enumeration.PositionName")
    @NotNull(message = "请选择岗位性质")
    private PositionName positionName;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "请输入手机号码")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "请输入用户名")
    @Pattern(regexp = "[A-Z,a-z,0-9,-]*", message = "用户名只能包含字母和数字")
    private String userName;

    @ApiModelProperty(value = "初始密码")
    private String passWord;

    @ApiModelProperty(value = "拥有的角色ID")
    @NotNull(message = "请选择角色")
    private Long roleId;

    @ApiModelProperty(value = "是否有建立子账号的权限（为true是有权限创建）")
    @NotNull(message = "请选择是否有建立子账号的权限")
    private Boolean adminPower;

}
