package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_maker_tax_record")
public class MakerTaxRecordEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客支付ID
     */
    private Long payMakerId;

    /**
     * 票证号码
     */
    private String voiceTypeNo;

    /**
     * 金库名称
     */
    private String voiceSerialNo;

    /**
     * 总税金
     */
    private BigDecimal makerTaxAmount;

    /**
     * 填票人
     */
    private BigDecimal totalAmount;

    /**
     * 金额
     */
    private BigDecimal salesamount;

    /**
     * 纳税人名称
     */
    private String taxAmount;

    /**
     * 纳税人识别号
     */
    private String voicePerson;

    /**
     * 税务机关
     */
    private String saleCompany;

    /**
     * 证明类型
     */
    private Integer helpMakeOrganationName;

    /**
     * 税务机关
     */
    private String makerTaxUrl;

    /**
     * 完税证明开具日期
     */
    private Date makerTaxGetDatetime;

    /**
     * 完税证明上传日期
     */
    private Date makerTaxUploadDatetime;
}
