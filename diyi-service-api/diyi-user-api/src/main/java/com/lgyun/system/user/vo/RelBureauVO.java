package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "税务局展示")
public class RelBureauVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 税务局ID
     */
    private Long bureauId;

    /**
     * 税务局名称
     */
    private String relBureauName;

    /**
     * 税务局联系人
     */
    private String contactPerson;

    /**
     * 税务局联系电话
     */
    private String telPhoneNo;

    /**
     * 创建时间
     */
    private String createTime;
}
