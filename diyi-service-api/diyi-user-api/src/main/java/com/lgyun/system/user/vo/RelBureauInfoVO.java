package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.RelBureauType;
import lombok.Data;

import java.io.Serializable;

@Data
public class RelBureauInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 相关局类型
     */
    private RelBureauType relBureauType;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 相关局名称
     */
    private String relBureauName;

}
