package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AnnualFeeState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_individual_enterprise_annual_fee")
public class IndividualEnterpriseAnnualFeeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 个独ID
     */
    private Long individualEnterpriseId;

    /**
     * 年费年度
     */
    private Integer annualFeeYear;

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
