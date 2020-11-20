package com.lgyun.system.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 身份证识别返回对象
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class IdcardOcrVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 身份证正面图
     */
    private String idcardPic;

    /**
     * 身份证反面图
     */
    private String idcardPicBack;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String idNo;

    /**
     * 手持证件正面照
     */
    private String idcardHand;

    /**
     * 手持证件反面照
     */
    private String idcardBackHand;

}
