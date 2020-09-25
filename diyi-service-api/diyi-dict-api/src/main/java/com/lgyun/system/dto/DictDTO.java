package com.lgyun.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据传输对象实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 19:24
 */
@Data
public class DictDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 父主键
     */
    @NotNull(message = "请输入父主键")
    private Long parentId;

    /**
     * 字典码
     */
    @NotBlank(message = "请输入字典码")
    private String code;

    /**
     * 字典值
     */
    @NotNull(message = "请输入字典值")
    private Integer dictKey;

    /**
     * 字典名称
     */
    @NotBlank(message = "请输入字典名称")
    private String dictValue;

    /**
     * 排序
     */
    @NotNull(message = "请输入排序")
    private Integer sort;
}
