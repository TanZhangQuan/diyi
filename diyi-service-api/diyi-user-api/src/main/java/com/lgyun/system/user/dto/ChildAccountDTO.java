package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.PositionName;
import io.swagger.annotations.ApiModel;
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
    private Long childAccountId;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空！")
    private String name;

    /**
     * 岗位性质
     */
    @NotNull(message = "岗位性质不能为空！")
    private PositionName positionName;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空！")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phoneNumber;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空！")
    private String userName;

    /**
     * 初始密码
     */
    private String passWord;

    /**
     * 拥有的角色ID
     */
    @NotNull(message = "角色ID不能为空！")
    private Long roleId;

    /**
     * 是否有建立子账号的权限（为true是有权限创建）
     */
    @NotNull(message = "建立子账号的权限不能为空！")
    private Boolean adminPower;

}
