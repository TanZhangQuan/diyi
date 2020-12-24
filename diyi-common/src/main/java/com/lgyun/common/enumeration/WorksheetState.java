package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 工单状态：
 a) 发布中，发布代抢单或代派单的工单
 b) 已关单，已经抢单或者派单完毕（人数不做控制依据）
 c) 验收中，有个人创客提交了工单等待验收或部分验收完毕
 d) 已完毕，所有个人创客都验收完毕了
 e) 已作废，验收中工单都可以作废，已完毕的不能作废
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum WorksheetState {
    PUBLISHING("PUBLISHING", "发布中"),
    CLOSED("CLOSED", "已关单"),
    CHECKACCEPT("CHECKACCEPT", "验收中"),
    FINISHED("FINISHED", "已完毕"),
    INVALID("INVALID", "已作废");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
