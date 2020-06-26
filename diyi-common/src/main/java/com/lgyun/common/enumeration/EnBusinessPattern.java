package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 业务外包模式
 */
@Getter
public enum EnBusinessPattern {
    natCroSouCom("natCroSouCom", "自然人众包（普票）"),
    natGenSubSouSpe("natGenSubSouSpe", "自然人总包+分包（专票）"),
    selfCroSouSpe("selfCroSouSpe", "个体户众包（专票）"),
    selfGenSubSouSpe("selfGenSubSouSpe", "个体户总包+分包（专票）"),
    selfCroSouCom("selfCroSouCom", "个体户众包（普票）");

    private String value;
    private String desc;

    EnBusinessPattern(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}