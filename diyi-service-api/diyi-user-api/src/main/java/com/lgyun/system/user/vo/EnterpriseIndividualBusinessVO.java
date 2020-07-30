package com.lgyun.system.user.vo;

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
public class EnterpriseIndividualBusinessVO implements Serializable {
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
     * 个体户税种：小规模，一般纳税人
     */
    private BizType bizType;

    /**
     * 经营者
     */
    private String bizName;

    /**
     * 经营者身份证号码
     */
    private String bizIdcardNo;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactPhone;

    /**
     * 注册资金
     */
    private BigDecimal registeredMoney;

    /**
     * 营业执照正本
     */
    private String businessLicenceUrl;

    /**
     * 年审信息
     */
    private String businessLicenceUrl1;

    /**
     * 个体户状态：注册中，税务登记中，运营中，已注销
     */
    private Ibstate ibstate;

    /**
     * 注册日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

}
