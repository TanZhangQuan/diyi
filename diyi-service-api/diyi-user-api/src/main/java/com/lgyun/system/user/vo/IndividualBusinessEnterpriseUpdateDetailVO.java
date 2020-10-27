package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.BizType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class IndividualBusinessEnterpriseUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 个体户/个独ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 注册时候选名称
     */
    private String candidatedNames;

    /**
     * 主要行业
     */
    private String mainIndustry;

    /**
     * 经营范围
     */
    private String bizScope;

    /**
     * 税种
     */
    private BizType bizType;

    /**
     * 注册资金
     */
    private BigDecimal registeredMoney;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactPhone;

}
