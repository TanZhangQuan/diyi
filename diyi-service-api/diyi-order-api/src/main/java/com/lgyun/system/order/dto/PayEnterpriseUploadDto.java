package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 上传支付清单
 *
 * @author tzq.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class PayEnterpriseUploadDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    @NotNull(message = "请选择服务商")
    private Long serviceProviderId;

    /**
     * 支付清单URL
     */
    @NotBlank(message = "请上传支付清单")
    private String chargeListUrl;

    /**
     * 工单ID
     */
    private Long worksheetId;

    /**
     * 支付回单(可能多张)
     */
    @NotBlank(message = "请上传支付回单")
    private String payReceiptUrl;

}
