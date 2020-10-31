package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.IdcardVerifyType;
import com.lgyun.common.enumeration.VerifyStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class UpdatePartnerDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 合伙人ID
     */
    @NotNull(message = "请选择合伙人")
    private Long partnerId;

    /**
     * 介绍合伙人ID
     */
    private Long introducePartnerId;

    /**
     * 微信open_id
     */
    private String openId;

    /**
     * 微信session_key
     */
    private String sessionKey;

    /**
     * 微信昵称
     */
    private String weChatNickname;

    /**
     * 微信关联日期
     */
    private Date relDate;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账户状态
     */
    private AccountState partnerState=AccountState.NORMAL;

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
    private String leveloedu;

    /**
     * 电子邮箱
     */
    private String emailAddress;

    /**
     * 身份证号码
     */
    private String idCardNo;

    /**
     * 到期日期
     */
    private Date dueDate;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 登录密码
     */
    private String loginPwd;

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
     * 身份证正面图
     */
    private String idCardPic;

    /**
     * 身份证反面图
     */
    private String idCardPicBack;

    /**
     * 正面自拍照
     */
    private String selfPic;

    /**
     * 身份证复印件图
     */
    private String idCardCopy;

    /**
     * 手持证件反面照
     */
    private String idCardBackHand;

    /**
     * 手持证件正面照
     */
    private String idCardHand;

    /**
     * 刷脸截图
     */
    private String picVerify;

    /**
     * 身份证验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus idCardVerifyStatus;

    /**
     * 身份证验证日期时间
     */
    private Date idCardVerifyDate;

    /**
     * 身份证验证类型：系统验证，手工验证
     */
    private IdcardVerifyType idCardVerifyType;

    /**
     * 手工验证人
     */
    private String manualVerifyName;

    /**
     * 验证描述
     */
    private String manualVerifyDesc;

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
}
