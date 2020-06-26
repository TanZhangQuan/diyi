package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 业务外包模式
 */
@Getter
public enum OrderBusinessPattern {
    selfGenSubSou("natCroSouCom", "个体户-总包+分包"),
    selfCroSou("natGenSubSouSpe", "个体户-众包"),
    natGenSubSou("selCroSouSpe", "自然人-总包+分包"),
    natCroSou("selGenSubSouSpe", "自然人-众包");

    private String value;
    private String desc;

    OrderBusinessPattern(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}