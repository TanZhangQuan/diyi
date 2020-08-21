package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收件地址性质
 */
@Getter
@AllArgsConstructor
public enum AddressType {
    TOMANAGEMENTCENTER("TOMANAGEMENTCENTER", "快递给管理中心"),
    TOCUSTOMER("TOCUSTOMER", "直接快递给客户");

    private final String value;
    private final String desc;

}