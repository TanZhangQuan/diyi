package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 监管文件阅读记录：相关局监管文件阅读管理表 Entity
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_rel_bureau_files_read")
public class RelBureauFilesReadEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 通知ID
     */
    private Long filesId;

    /**
     * 阅读服务商
     */
    private Long readServicer;

    /**
     * 阅读人
     */
    private String reader;

}
