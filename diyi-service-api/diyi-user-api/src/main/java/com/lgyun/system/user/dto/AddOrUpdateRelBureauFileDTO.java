package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddOrUpdateRelBureauFileDTO {

    /**
     * 监督文件ID
     */
    private Long relBureauFileId;

    /**
     * 监督文件标题
     */
    @NotBlank(message = "请输入监督文件标题")
    private String fileTitle;

    /**
     * 监督文件摘要
     */
    @NotBlank(message = "请输入监督文件摘要")
    private String fileDesc;

    /**
     * 监督文件文件
     */
    @NotBlank(message = "请上传监督文件文件")
    private String fileUrl;

    /**
     * 发布联系人
     */
    private String contactPerson;

    /**
     * 联系人手机
     */
    private String contactPhone;

    /**
     * 联系人微信
     */
    private String contactWechat;

}
