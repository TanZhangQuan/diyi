package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.BizType;
import com.lgyun.common.enumeration.Ibstate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class IndividualBusinessEnterpriseUpdateServiceProviderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 个体户/个独ID
     */
    @NotNull(message = "请选择个体户/个独")
    private Long id;

    /**
     * 核定税种
     */
    @NotNull(message = "请选择核定税种")
    private BizType bizType;

    /**
     * 个体户名称
     */
    private String ibname;

    /**
     * 统一社会信用代码
     */
    private String ibtaxNo;

    /**
     * 营业执照的注册日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date buildDateTime;

    /**
     * 园区
     */
    private String bizPark;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 注册资金
     */
    @NotNull(message = "请输入注册资金")
    @Min(value = 0, message = "注册资金不能小于0")
    private BigDecimal registeredMoney;

    /**
     * 经营场所
     */
    private String businessAddress;

    /**
     * 主要行业
     */
    @NotBlank(message = "请选择行业类型")
    private String mainIndustry;

    /**
     * 经营范围
     */
    @NotBlank(message = "请选择经营范围")
    private String bizScope;

    /**
     * 注册时候选名称
     */
    @NotBlank(message = "请输入注册候选名称")
    private String candidatedNames;

    /**
     * 网络经营场所
     */
    private String netBusinessAddress;

    /**
     * 营业执照正本
     */
    private String businessLicenceUrl;

    /**
     * 营业执照副本
     */
    private String businessLicenceCopyUrl;

    /**
     * 个体户/个独状态
     */
    @NotNull(message = "请选择状态")
    private Ibstate ibstate;

    /**
     * 提交日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitDateTime;

    /**
     * 注册日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registeredDate;

    /**
     * 税务登记日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taxRegisterDateTime;

    /**
     * 联系人姓名
     */
    @NotBlank(message = "请输入联系人名称")
    private String contactName;

    /**
     * 联系人手机号
     */
    @NotBlank(message = "请输入联系人手机号")
    private String contactPhone;

    /**
     * 服务费率
     */
    private BigDecimal serviceRate;

}
