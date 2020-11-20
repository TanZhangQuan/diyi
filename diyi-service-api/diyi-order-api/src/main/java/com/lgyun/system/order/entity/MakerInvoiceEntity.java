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
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_maker_invoice")
public class MakerInvoiceEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客支付ID
     */
    private Long payMakerId;

    /**
     * 发票代码
     */
    private String voiceTypeNo;

    /**
     * 发票号码
     */
    private String voiceSerialNo;

    /**
     * 发票开具日期
     */
    private Date makerVoiceGetDateTime;

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
     * 代开机关名称
     */
    private String helpMakeOrganationName;

    /**
     * 代开企业名称
     */
    private String helpMakeCompany;

    /**
     * 代开企业税号
     */
    private String helpMakeTaxNo;

    /**
     * 发票URL
     */
    private String makerVoiceUrl;

    /**
     * 发票上传日期
     */
    private Date makerVoiceUploadDateTime;

}
