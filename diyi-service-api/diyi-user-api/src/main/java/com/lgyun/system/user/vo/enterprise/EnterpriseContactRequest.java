package com.lgyun.system.user.vo.enterprise;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * EnterpriseContactRequest
 *
 * @author liangfeihu
 * @since 2020/8/19 11:40
 */
@Data
@NoArgsConstructor
public class EnterpriseContactRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String workerName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 岗位性质
     */
    private String positionName;

    /**
     * email
     */
    private String email;

    /**
     * wxId
     */
    private String wxId;

    /**
     * 性别
     */
    private String workerSex;

    /**
     * 用户名
     */
    private String employeeUserName;

}
