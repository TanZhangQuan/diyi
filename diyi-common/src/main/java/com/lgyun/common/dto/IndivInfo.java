/**
 *
 */
package com.lgyun.common.dto;

import lombok.Data;

/**
 * 个人基本信息
 * @author tzq
 *
 */
@Data
public class IndivInfo {

    //个人银行卡号
    private String bankCardNo;

    //个人证件号
    private String certNo;

    //个人手机号
    private String mobileNo;

    //个人姓名
    private String name;

}
