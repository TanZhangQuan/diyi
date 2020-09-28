package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 服务商更新快递信息DTO
 *
 * @author tzq.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class SelfHelpInvoiceExpressDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票ID
     */
    @NotNull(message = "请输入自助开票编号")
    private Long selfHelpInvoiceId;

    /**
     * 快递单号
     */
    @NotNull(message = "请输入运单号")
    private String expressSheetNo;

    /**
     * 快递公司
     */
    @NotNull(message = "请选择快递公司")
    private String expressCompanyName;

}
