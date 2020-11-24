package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RelBureauListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 相关局名称
     */
    private String relBureauName;

    /**
     * 相关局联系人
     */
    private String contactName;

    /**
     * 相关局联系电话
     */
    private String contactPhone;

    /**
     * 相关局账户状态
     */
    private AccountState relBureauState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
