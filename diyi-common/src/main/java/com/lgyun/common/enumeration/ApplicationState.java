package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 处理状态 1,申请中；2，已拒绝；3，已全额开具；4，已部分开具,5已取消
 * @author tzq
 * @date 2020/8/12.
 * @time 14:17.
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum ApplicationState {
    APPLYING("APPLYING", "申请中"),
    REJECTED("REJECTED", "已拒绝"),
    ISSUEDINFULL("ISSUEDINFULL", "已全额开具"),
    PARTIALLYISSUED("PARTIALLYISSUED", "已部分开具"),
    CANCELLED("CANCELLED", "已取消");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
