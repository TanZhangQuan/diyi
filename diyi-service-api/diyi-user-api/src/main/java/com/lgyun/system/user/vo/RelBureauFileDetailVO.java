package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RelBureauFileDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 监督文件ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 监督文件标题
     */
    private String fileTitle;

    /**
     * 监督文件摘要
     */
    private String fileDesc;

    /**
     * 监督文件文件
     */
    private String fileUrl;

    /**
     * 监督文件状态
     */
    private RelBureauNoticeFileState fileState;

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

    /**
     * 发布日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDatetime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
