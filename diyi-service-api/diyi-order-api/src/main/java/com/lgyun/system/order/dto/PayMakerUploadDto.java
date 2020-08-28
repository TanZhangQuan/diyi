package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 上传分包支付
 *
 * @author tzq.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class PayMakerUploadDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单ID
     */
    @NotNull(message = "请选择支付清单")
    private Long payEnterpriseId;

    /**
     * 支付回单
     */
    @NotBlank(message = "请上传支付回单")
    private String payReceiptUrl;

}
