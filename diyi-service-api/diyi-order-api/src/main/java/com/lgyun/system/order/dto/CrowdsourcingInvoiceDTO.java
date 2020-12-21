package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/12/15.
 * @time 15:53.
 */
@Data
public class CrowdsourcingInvoiceDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *自助开票详情id
     */
    @NotNull(message = "请输入自助开票详情id")
    private Long selfHelpInvoiceDetailId;

    /**
     * 发票
     */
    @NotBlank(message = "请输入发票")
    private String invoiceScanPictures;

    /**
     * 税票
     */
    private String taxScanPictures;
}
