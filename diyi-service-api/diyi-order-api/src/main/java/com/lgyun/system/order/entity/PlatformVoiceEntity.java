package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * @since 2020-07-25 14:38:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_platform_voice")
public class PlatformVoiceEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 支付ID
     */
    private Long enterprisePayId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 发票代码
     */
    private String voiceTypeNo;

    /**
     * 发票号码
     */
    private String voiceSerialNo;

    /**
     * 开票日期
     */
    private Date voiceDateTime;

    /**
     * 货物或应税劳务、服务名称
     */
    private String voiceCategory;

    /**
     * 价税合计
     */
    private BigDecimal totalAmount;

    /**
     * 金额合计
     */
    private BigDecimal salesAmount;

    /**
     * 税额合计
     */
    private BigDecimal taxAmount;

    /**
     * 开票人
     */
    private String voicePerson;

    /**
     * 销售方名称
     */
    private String saleCompany;

    /**
     * 平台给企业开票URL地址
     */
    private String companyinvoiceUrl;

    /**
     * 发票上传日期
     */
    private Date companyVoiceUploadDateTime;

}
