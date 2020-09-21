package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author tzq
 * @Description 银行卡实名认证DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class BankCardOcrDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //个人银行卡号
    @NotBlank(message = "请输入银行卡号")
    private String bankCardNo;

    //身份证反面图
    @NotBlank(message = "请上传身份证反面图")
    private String idcardPicBack;

}
