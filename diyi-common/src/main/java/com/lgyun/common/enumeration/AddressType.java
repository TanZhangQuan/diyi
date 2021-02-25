package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收件地址性质
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum AddressType {
    TOMANAGEMENTCENTER("TOMANAGEMENTCENTER", "快递给管理中心"),
    TOCUSTOMER("TOCUSTOMER", "直接快递给客户");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}