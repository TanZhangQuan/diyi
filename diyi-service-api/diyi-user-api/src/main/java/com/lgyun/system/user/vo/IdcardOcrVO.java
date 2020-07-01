package com.lgyun.system.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 身份证识别返回对象
 *
 * @author liangfeihu
 * @since 2020/6/6 00:28
 */
@Data
public class IdcardOcrVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String idNo;

}
