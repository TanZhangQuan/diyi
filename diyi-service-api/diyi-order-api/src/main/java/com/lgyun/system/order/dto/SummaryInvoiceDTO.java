package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/10/27.
 * @time 14:31.
 */
@Data
public class SummaryInvoiceDTO implements Serializable {

    /**
     * 商户清单id，多个用逗号隔开
     */
    @NotBlank(message = "请输入商户清单id")
    private String payEnterpriseIds;
    /**
     *发票url
     */
    @NotNull(message = "请输入发票url")
    private String companyInvoiceUrl;
    /**
     * 总完税证明URL
     */
    @NotNull(message = "请输入总完税证明URL")
    private String makerTaxUrl;

    /**
     * 清单式完税凭证URL
     */
    private String makerTaxListUrl;

}
