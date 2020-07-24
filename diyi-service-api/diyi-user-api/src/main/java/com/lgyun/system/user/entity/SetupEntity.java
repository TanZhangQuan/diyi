package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_setup")
public class SetupEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 允许第一次关联企业更新创客信息
     */
    private Boolean allowFirstEdit;

    /**
     * 允许所有关联企业更新创客信息
     */
    private Boolean allowAllEdit;

}
