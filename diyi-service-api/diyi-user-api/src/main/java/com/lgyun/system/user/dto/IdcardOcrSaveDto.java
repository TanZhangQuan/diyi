package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author tzq
 * @Description 身份证实名认证信息保存dto
 * @return
 * @date 2020.06.27
 */
@Data
public class IdcardOcrSaveDto implements Serializable {

    //身份证正面图
    @NotBlank(message = "请上传身份证正面图")
    private String idcardPic;

    //身份证反面图
    @NotBlank(message = "请上传身份证反面图")
    private String idcardPicBack;

    //姓名
    @NotBlank(message = "请输入姓名")
    private String name;

    //身份证号码
    @NotBlank(message = "请输入身份证号码")
    private String idNo;

}
