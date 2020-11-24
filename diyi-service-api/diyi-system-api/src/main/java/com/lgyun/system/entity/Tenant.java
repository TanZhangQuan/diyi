package com.lgyun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.TenantEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 租户管理 实体类
 *
 * @author tzq
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_tenant")
public class Tenant extends TenantEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 联系地址
     */
    private String address;

}
