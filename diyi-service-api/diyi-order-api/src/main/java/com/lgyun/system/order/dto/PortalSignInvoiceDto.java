package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author .
 * @date 2020/9/16.
 * @time 16:02.
 */
@Data
public class PortalSignInvoiceDto implements Serializable {

    /**
     * 商户支付清单Id
     */
    @NotNull(message = "请输入商户支付清单Id")
    private Long payEnterpriseId;

    /**
     * 创客支付明细json
     */
    @NotBlank(message = "请输入创客支付明细json")
    private String payMakers;

    /**
     * 服务商的名字
     */
    @NotBlank(message = "请输入服务商的名字")
    private String serviceProviderName;
}
