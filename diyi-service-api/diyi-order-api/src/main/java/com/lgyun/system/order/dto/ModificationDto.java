package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author .
 * @date 2020/12/12.
 * @time 10:52.
 */
@Data
public class ModificationDto implements Serializable {
    private Long selfHelpInvoiceDetailId;

    //("身份证正面url")
    @NotBlank(message = "请输入身份证正面url")
    private String positiveIdCard;

    //("身份证反面url")
    @NotBlank(message = "请输入身份证反面url")
    private String backIdCard;

    //("业务合同")
    @NotBlank(message = "请输入业务合同")
    private String businessContract;

    //("支付回单")
    @NotBlank(message = "请输入支付回单")
    private String paymentReceipt;

}
