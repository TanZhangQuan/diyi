package com.lgyun.system.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author xjw
 * @date 2020-09-9
 */
@Data
public class MakerListIndividualDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 身份证号码
     */
    private String idcardNo;
}
