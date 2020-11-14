package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.InvoiceDemand;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 服务商发票类目
 *
 * @author tzq
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class AddOrUpdateEnterpriseProviderInvoiceCatalogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户-服务商发票类目ID
     */
    private Long enterpriseProviderInvoiceCatalogId;

    /**
     * 发票类目名称
     */
    @NotBlank(message = "请输入发票类目名称")
    private String invoiceCatalogName;

    /**
     * 开票诉求
     */
    @NotNull(message = "请选择开票诉求")
    private InvoiceDemand invoiceDemand;

    /**
     * 开票诉求备注
     */
    private String invoiceDemandDesc;

}
