package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
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
public class PayEnterpriseCreateOrUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户支付清单ID
     */
    private Long payenterpriseId;

    /**
     * 服务商ID
     */
    @NotNull(message = "请选择服务商")
    private Long serviceProviderId;

    /**
     * 创客类型
     */
    @NotNull(message = "请选择创客类型")
    private MakerType makerType;

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
     * 支付说明
     */
    private String payMemo;

    /**
     * 支付回单(多张)
     */
    @NotBlank(message = "请上传支付回单")
    private String enterprisePayReceiptUrls;

}
