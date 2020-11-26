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
public class PortalSignInvoiceDTO implements Serializable {

    /**
     * 总包支付清单ID
     */
    @NotNull(message = "请输入总包支付清单Id")
    private Long payEnterpriseId;

    /**
     * 分包支付明细
     */
    @NotBlank(message = "请输入分包支付明细")
    private String payMakers;

    /**
     * 服务商的名字
     */
    @NotBlank(message = "请输入服务商的名字")
    private String serviceProviderName;
}
