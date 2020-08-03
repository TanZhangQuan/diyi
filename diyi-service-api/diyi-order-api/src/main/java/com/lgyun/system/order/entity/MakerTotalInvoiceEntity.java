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
 * @since 2020-07-18 20:49:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_maker_total_invoice")
public class MakerTotalInvoiceEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单ID
     */
    private Long payListId;

    /**
     * 发票代码
     */
    private String invoiceTypeNo;

    /**
     * 发票号码
     */
    private String invoiceSerialNo;

    /**
     * 开票日期
     */
    private Date invoiceDatetime;

    /**
     * 货物或应税劳务、服务名称
     */
    private String invoiceCategory;

    /**
     *
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
    private String invoicePerson;

    /**
     * 销售方名称
     */
    private String saleCompany;

    /**
     * 总包发票URL
     */
    private String companyInvoiceUrl;

    /**
     * 发票上传日期
     */
    private Date companyVoiceUploadDatetime;

    /**
     * 总完税证明URL
     */
    private String makerTaxUrl;

    /**
     * 清单式完税凭证URL
     */
    private String makerTaxListUrl;
}
