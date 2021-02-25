package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class RelBureauNoticeFileListServiceProviderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知/监督文件ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "通知/监督文件标题")
    private String noticeFileTitle;

    @ApiModelProperty(value = "通知/监督文件摘要")
    private String noticeFileDesc;

    @ApiModelProperty(value = "通知/监督文件阅读状态")
    private Boolean boolRead;

    @ApiModelProperty(value = "发布日期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDatetime;

}
