package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.PositionName;
import lombok.Data;

/**
 * 商户的联系人信息
 */
@Data
public class ContactsInfoVO {
    /**
     * 联系人1姓名
     */
    private String contact1Name;

    /**
     * 联系人1职位
     */
    private PositionName contact1Position;

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
    private PositionName contact2Position;

    /**
     * 联系人2电话/手机
     */
    private String contact2Phone;

    /**
     * 联系人2邮箱
     */
    private String contact2Mail;

}
