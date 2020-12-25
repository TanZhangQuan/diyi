package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class RelBureauFileDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "监督文件ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "监督文件标题")
    private String fileTitle;

    @ApiModelProperty(value = "监督文件摘要")
    private String fileDesc;

    @ApiModelProperty(value = "监督文件文件")
    private String fileUrl;

    @ApiModelProperty(value = "监督文件状态")
    private RelBureauNoticeFileState fileState;

    @ApiModelProperty(value = "发布联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系人手机")
    private String contactPhone;

    @ApiModelProperty(value = "联系人微信")
    private String contactWechat;

    @ApiModelProperty(value = "发布日期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDatetime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
