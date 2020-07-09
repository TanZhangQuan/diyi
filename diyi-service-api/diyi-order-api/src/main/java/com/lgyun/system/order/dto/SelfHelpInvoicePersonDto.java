package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 15:56.
 */
@Data
public class SelfHelpInvoicePersonDto implements Serializable {
    /**
     * 身份证号码
     */
    @NotBlank(message = "请输入身份证号码")
    private String idCardNo;

    /**
     * 身份证姓名
     */
    @NotBlank(message = "请输入身份证姓名")
    private String idCardName;

    /**
     * 身份证正面图
     */
    @NotBlank(message = "请输入身份证正面图")
    private String idCardPic;

    /**
     * 身份证反面图
     */
    @NotBlank(message = "请输入身份证反面图")
    private String idCardPicBack;

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号码")
    private String phoneNumber;
}
