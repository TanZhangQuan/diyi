package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.IndBusTaxType;
import com.lgyun.common.tool.DateUtil;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("diyi_individual_business")
public class IndividualBusinessEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 个体户税种：小规模，一般纳税人
     */
    private IndBusTaxType indBusTaxType;

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
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date buildDateTime;

    /**
     * 园区
     */
    private String bizPark;

    /**
     * 所在省市县
     */
    private String provinceandCounty;

    /**
     * 注册资金
     */
    private BigDecimal registeredMoney;

    /**
     * 经营场所
     */
    private String businessAddress;

    /**
     * 主要行业
     */
    private String mainIndustry;

    /**
     * 经营范围
     */
    private String bizScope;

    /**
     * 注册时候选名称
     */
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
     * 个体户状态：注册中，税务登记中，运营中，已注销
     */
    private Ibstate ibstate;

    /**
     * 提交日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date submitDateTime;

    /**
     * 注册日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date registeredDate;

    /**
     * 税务登记日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date taxRegisterDateTime;

    /**
     * 注销日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date logoutDateTime;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactPhone;

    /**
     * 服务费率
     */
    private BigDecimal serviceRat;

}
