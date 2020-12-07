package com.lgyun.system.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 联系人1姓名
     */
    private String contact1Name;

    /**
     * 联系人1职位
     */
    private String contact1Position;

    /**
     * 联系人1电话/手机
     */
    private String contact1Phone;

    /**
     * 联系人1邮箱
     */
    private String contact1Mail;

    /**
     * 联系人2姓名
     */
    private String contact2Name;

    /**
     * 联系人2职位
     */
    private String contact2Position;

    /**
     * 联系人2电话/手机
     */
    private String contact2Phone;

    /**
     * 联系人2邮箱
     */
    private String contact2Mail;

}
