package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.CertificationState;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 姓名
     */
    private String name;

    /**
     * 认证状态
     */
    private CertificationState certificationState;

    /**
     * 自我描述
     */
    private String selfDesc;

}
