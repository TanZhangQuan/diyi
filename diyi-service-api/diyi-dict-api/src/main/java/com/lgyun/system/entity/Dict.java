package com.lgyun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.Data;

/**
 * 实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 19:21
 */
@Data
@TableName("sys_dict")
public class Dict extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父主键
     */
    private Long parentId;

    /**
     * 字典码
     */
    private String code;

    /**
     * 字典值
     */
    private String dictKey;

    /**
     * 字典名称
     */
    private String dictValue;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 字典备注
     */
    private String remark;

}
