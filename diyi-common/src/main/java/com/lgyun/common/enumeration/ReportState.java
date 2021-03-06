package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申报状态
 * @author .
 * @date 2020/8/8.
 * @time 10:41.
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum ReportState {
    DECLAREING("DECLAREING", "申报中"),
    DECLARESUCCESS("DECLARESUCCESS", "申报成功"),
    DECLAREFAIL("DECLAREFAIL", "申报失败"),
    EDITDECLARESUCCESS("EDITDECLARESUCCESS", "修改后申报成功");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
