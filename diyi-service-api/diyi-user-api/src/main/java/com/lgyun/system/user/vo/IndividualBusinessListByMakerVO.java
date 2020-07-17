package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/7/6 00:28
 */
@Data
public class IndividualBusinessListByMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 个体户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 个体户名称
     */
    private String ibname;

    /**
     * 注册时候选名称
     */
    private String candidatedNames;

}
