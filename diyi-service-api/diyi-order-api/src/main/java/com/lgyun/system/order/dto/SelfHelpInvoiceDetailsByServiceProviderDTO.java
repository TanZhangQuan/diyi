package com.lgyun.system.order.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付清单DTO
 *
 * @author tzq.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class SelfHelpInvoiceDetailsByServiceProviderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 开票人姓名
     */
    private String invoicePeopleName;

    /**
     * 自助开票开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    /**
     * 自助开票结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
