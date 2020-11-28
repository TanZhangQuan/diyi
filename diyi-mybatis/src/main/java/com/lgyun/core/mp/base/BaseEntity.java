package com.lgyun.core.mp.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 *
 * @author tzq
 * @since 2020/6/6 00:40
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 记录状态[0-非正常 1-正常]
     */
    private Integer status;

    /**
     * 是否已删除[0-未删除 1-已删除]
     */
    @TableLogic
    private Integer isDeleted;
}
