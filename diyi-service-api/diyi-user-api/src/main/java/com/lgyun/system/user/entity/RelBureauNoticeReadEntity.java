package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 相关局通知阅读管理表 Entity
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_rel_bureau_notice_read")
public class RelBureauNoticeReadEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 通知ID
     */
    private Long relBureauNoticeId;

    /**
     * 服务商ID
     */
    private Long servicerProviderId;

    /**
     * 服务商员工ID
     */
    private Long servicerProviderWorkerId;

}
