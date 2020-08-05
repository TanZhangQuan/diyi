package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
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
     * 对象id
     */
    private Long objectId;

    /**
     * 对象身份1、创客本人2、商户人员3、服务商人员4、相关局人员5、渠道商人员6、合伙人本人
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
     * 是否默认[0:默认,1:不默认]
     */
    private Integer isDefault;
}
