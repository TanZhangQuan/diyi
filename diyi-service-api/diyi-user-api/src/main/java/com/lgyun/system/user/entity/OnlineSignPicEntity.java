package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 在线签字图片表 Entity
 *
 * @author jun
 * @since 2020-07-18 15:59:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_online_sign_pic")
public class OnlineSignPicEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 对象身份
     */
    private ObjectType objectType;

    /**
     * 对象ID
     */
    private Long objectId;

    /**
     * 签字笔迹URL
     */
    private String signPic;
}
