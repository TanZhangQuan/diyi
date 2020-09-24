package com.lgyun.system.user.dto.service_provider;

import com.lgyun.common.enumeration.PositionName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 添加或编辑服务商联系人
 *
 * @author tzq
 * @since 2020/8/19 11:40
 */
@Data
@NoArgsConstructor
public class AddOrUpdateServiceProviderContactDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //服务商编号
    @NotNull(message = "请输入服务商编号")
    private Long serviceProviderId;

    //联系人1姓名
    @NotBlank(message = "请输入联系人1姓名")
    private String contact1Name;

    //联系人1职位
    @NotNull(message = "请输入联系人1职位")
    private PositionName contact1Position;

    //联系人1电话/手机
    @NotBlank(message = "请输入联系人1电话/手机")
    @Length(min = 11, max = 11, message = "请输入11位的联系人1电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人1电话/手机")
    private String contact1Phone;

    //联系人1邮箱
    @NotBlank(message = "请输入联系人1邮箱")
    private String contact1Mail;

    //联系人2姓名
    @NotBlank(message = "请输入联系人2姓名")
    private String contact2Name;

    //联系人2职位
    @NotNull(message = "请输入联系人2职位")
    private PositionName contact2Position;

    //联系人2电话/手机
    @NotBlank(message = "请输入联系人2电话/手机")
    @Length(min = 11, max = 11, message = "请输入11位的联系人2电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人2电话/手机")
    private String contact2Phone;

    //联系人2邮箱
    @NotBlank(message = "请输入联系人2邮箱")
    private String contact2Mail;

}
