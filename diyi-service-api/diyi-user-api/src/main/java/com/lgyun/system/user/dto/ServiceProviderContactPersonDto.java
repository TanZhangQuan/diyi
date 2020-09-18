package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.PositionName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tzq
 * @Description
 * @return
 * @date 2020.06.27
 */
@Data
public class ServiceProviderContactPersonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 联系人1姓名（一般为老板/财务负责人）
     */
    private String contact1Name;

    /**
     * 联系人1职位
     */
    private PositionName contact1Position;

    /**
     * 联系人1电话手机（必填）
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
     * 联系人2电话手机（必填）
     */
    private String contact2Phone;

    /**
     * 联系人2邮箱
     */
    private String contact2Mail;

}
