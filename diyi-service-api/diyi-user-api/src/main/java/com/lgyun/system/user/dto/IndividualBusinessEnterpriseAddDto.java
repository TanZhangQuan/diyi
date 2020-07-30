package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.BizType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzq
 * @Description 个体户新增dto
 * @return
 * @date 2020.06.27
 */
@Data
public class IndividualBusinessEnterpriseAddDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //税种：小规模，一般纳税人
    @NotNull(message = "请选择税种")
    private BizType bizType;

    //注册资金
    @NotNull(message = "请输入注册资金")
    private BigDecimal registeredMoney;

    //主要行业
    @NotBlank(message = "请选择主要行业")
    private String mainIndustry;

    //经营范围
    @NotBlank(message = "请输入经营范围")
    private String bizScope;

    //注册时候选名称
    @NotBlank(message = "请输入企业名称")
    private String candidatedNames;

    //联系人姓名
    @NotBlank(message = "请输入联系人姓名")
    private String contactName;

    //联系人手机号
    @NotBlank(message = "请输入联系人手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String contactPhone;

}
