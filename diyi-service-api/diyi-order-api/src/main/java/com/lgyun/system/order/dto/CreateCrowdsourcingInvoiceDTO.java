package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author .
 * @date 2020/12/15.
 * @time 15:33.
 */
@Data
public class CreateCrowdsourcingInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "请输入快递单号")
    private String expressNo;

    @NotBlank(message = "请输入快递公司名字")
    private String expressCompanyName;

    /**
     * 请输入自助开票id
     */
    @NotNull(message = "请输入自助开票id")
    private Long selfHelpInvoiceId;

    /**
     * 明细加发票
     */
    private List<CrowdsourcingInvoiceDTO> list;
}
