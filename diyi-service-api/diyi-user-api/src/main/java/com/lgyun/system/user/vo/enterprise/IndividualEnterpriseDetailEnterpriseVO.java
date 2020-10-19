package com.lgyun.system.user.vo.enterprise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.BizType;
import com.lgyun.common.enumeration.Ibstate;
import lombok.Data;

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
public class IndividualEnterpriseDetailEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 个独ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 个独名称
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
     * 主要行业
     */
    private String mainIndustry;

    /**
     * 经营范围
     */
    private String bizScope;

    /**
     * 个独税种：小规模，一般纳税人
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
     * 个独状态：注册中，税务登记中，运营中，已注销
     */
    private Ibstate ibstate;

    /**
     * 注册日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

}
