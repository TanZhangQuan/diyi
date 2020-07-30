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
public class IndividualEnterpriseDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 个体户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 个体户名称
     */
    private String ibname;

    /**
     * 注册时候选名称
     */
    private String candidatedNames;

    /**
     * 统一社会信用代码
     */
    private String ibtaxNo;

    /**
     * 园区
     */
    private String bizPark;

    /**
     * 主要行业
     */
    private String mainIndustry;

    /**
     * 经营范围
     */
    private String bizScope;

    /**
     * 税种：小规模，一般纳税人
     */
    private BizType bizType;

    /**
     * 经营者
     */
    private String bizName;

    /**
     * 注册资金
     */
    private BigDecimal registeredMoney;

    /**
     * 营业执照正本
     */
    private String businessLicenceUrl;

    /**
     * 营业执照副本
     */
    private String businessLicenceCopyUrl;

}
