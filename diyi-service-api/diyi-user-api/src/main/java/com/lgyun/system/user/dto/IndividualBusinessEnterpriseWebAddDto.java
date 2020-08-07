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
 * @return
 * @date 2020.07.15
 */
@Data
public class IndividualBusinessEnterpriseWebAddDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //注册时候选名称
    @NotBlank(message = "请输入企业名称")
    private String candidatedNames;

    //主要行业
    @NotBlank(message = "请选择主要行业")
    private String mainIndustry;

    //经营范围
    @NotBlank(message = "请输入经营范围")
    private String bizScope;

    //税种：小规模，一般纳税人
    @NotNull(message = "请选择税种")
    private BizType bizType;

    //注册资金
    @NotNull(message = "请输入注册资金")
    private BigDecimal registeredMoney;

    //经营者姓名
    @NotBlank(message = "请输入经营者姓名")
    private String name;

    //经营者手机号
    @NotBlank(message = "请输入经营者手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phone;

    //经营者身份证号
    @NotBlank(message = "请输入经营者身份证号")
    private String idcardNo;

    //身份证正面图
    @NotBlank(message = "请上传身份证正面图")
    private String idcardPic;

    //身份证反面图
    @NotBlank(message = "请上传身份证反面图")
    private String idcardPicBack;

    //手持证件正面照
    @NotBlank(message = "请上传手持证件正面照")
    private String idcardHand;

    //手持证件反面照
    @NotBlank(message = "请上传手持证件反面照")
    private String idcardBackHand;

    //联系人姓名
    @NotBlank(message = "请输入联系人姓名")
    private String contactName;

    //联系人手机号
    @NotBlank(message = "请输入联系人手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String contactPhone;

}
