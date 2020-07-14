package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AnnualFeeState;
import com.lgyun.common.tool.DateUtil;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@NoArgsConstructor
@TableName("diyi_individual_business_annual_fee")
public class IndividualBusinessAnnualFeeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 个体工商户年费信息ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long individualBusinessAnnualFeeId;

    /**
     * 个体户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long individualBusinessId;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 年费缴纳日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date annualFeeDateTime;

    /**
     * 年费金额
     */
    private BigDecimal annualFeeAmount;

    /**
     * 年费起始日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date annualFeeStart;

    /**
     * 年费终止日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date annualFeeEnd;

    /**
     * 年费状态：待缴费，已缴费
     */
    private AnnualFeeState annualFeeState;

    /**
     * 第几年
     */
    private Integer yearSerial;

}
