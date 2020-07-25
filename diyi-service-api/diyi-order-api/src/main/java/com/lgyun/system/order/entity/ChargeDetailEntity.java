package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.ChargeState;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_charge_detail")
public class ChargeDetailEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 运营公司ID
     */
    private Long runCompanyId;

    /**
     * 充值金额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 充值日期
     */
    private Date chargeDateTime;

    /**
     * 充值人员
     */
    private String chargeMoneyPlatformPerson;

    /**
     * 充值状态：已充值，已打款
     */
    private ChargeState chargeState;

    /**
     * 银行回单URL
     */
    private String bankReceiptUrl;

    /**
     * 银行回单上传日期
     */
    private Date bankReceiptUploadDate;

    /**
     * 打款人员
     */
    private String chargeMoneyEnPerson;

}
