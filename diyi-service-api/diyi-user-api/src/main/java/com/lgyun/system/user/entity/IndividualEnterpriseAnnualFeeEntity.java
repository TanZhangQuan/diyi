package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AnnualFeeState;
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
@TableName("diyi_individual_enterprise_annual_fee")
public class IndividualEnterpriseAnnualFeeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 个独年费信息ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long individualEnterpriseAnnualFeeId;

    /**
     * 个独ID
     */
    private Long individualEnterpriseId;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 年费缴纳日期
     */
    private Date annualFeeDateTime;

    /**
     * 年费金额
     */
    private BigDecimal annualFeeAmount;

    /**
     * 年费起始日期
     */
    private Date annualFeeStart;

    /**
     * 年费终止日期
     */
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
