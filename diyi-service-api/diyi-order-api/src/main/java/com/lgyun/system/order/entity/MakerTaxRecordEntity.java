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
     * 票证代码
     */
    private String voiceTypeNo;

    /**
     * 票证号码
     */
    private String voiceSerialNo;

    /**
     * 总税金
     */
    private BigDecimal makerTaxAmount;

    /**
     * 价税合计
     */
    private BigDecimal totalAmount;

    /**
     * 金额合计
     */
    private BigDecimal salesamount;

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
     * 完税证明url
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
