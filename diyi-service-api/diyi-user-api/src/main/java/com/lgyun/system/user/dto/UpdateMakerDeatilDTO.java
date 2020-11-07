package com.lgyun.system.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tzq
 * @Description 编辑创客DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class UpdateMakerDeatilDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 政治面貌
     */
    private String politicState;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 文化程度
     */
    private String levelofedu;

    /**
     * 电子邮箱
     */
    private String emailAddress;

    /**
     * 身份证号码
     */
    private String idcardNo;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 开户支行
     */
    private String subBankName;

    /**
     * 线上经营场所
     */
    private String runAddress;

    /**
     * 线下经营地址
     */
    private String houseAddress;

    /**
     * 住址
     */
    private String livingAddress;

    /**
     * 自我描述
     */
    private String selfDesc;

    /**
     * 商铺
     */
    private String shopUrl;

    /**
     * 商铺用户名
     */
    private String shopUserName;

}
