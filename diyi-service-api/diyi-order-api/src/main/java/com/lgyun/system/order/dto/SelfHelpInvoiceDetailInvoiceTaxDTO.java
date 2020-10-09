package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 服务商上传发票税票DTO
 *
 * @author tzq.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class SelfHelpInvoiceDetailInvoiceTaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票明细ID
     */
    @NotNull(message = "请输入自助开票明细编号")
    private Long selfHelpInvoiceDetailId;

    /**
     * 发票（可能多张）
     */
    @NotBlank(message = "请上传发票")
    private String InvoiceScanPictures;

    /**
     * 税票（可能多张）
     */
    private String TaxScanPictures;

}
