package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 收件地址 Entity
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_address")
public class AddressEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 对象ID
     */
    private Long objectId;

    /**
     * 对象身份
     */
    private ObjectType objectType;

    /**
     * 收件人
     */
    private String addressName;

    /**
     * 手机号码
     */
    private String addressPhone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 是否默认
     */
    private Boolean boolDefault;
}
