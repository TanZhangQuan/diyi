package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author tzq
 * @Description 身份证实名认证DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class IdcardOcrDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 身份证正面图
     */
    @NotBlank(message = "请上传身份证正面图")
    private String idcardPic;

    /**
     * 身份证反面图
     */
    @NotBlank(message = "请上传身份证反面图")
    private String idcardPicBack;

}
