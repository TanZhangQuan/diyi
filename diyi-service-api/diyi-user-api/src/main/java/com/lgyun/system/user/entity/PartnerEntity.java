package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.*;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 合伙人信息表 Entity
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_partner")
public class PartnerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 管理者ID
     */
    private Long userId;

    /**
     * 介绍合伙人ID
     */
    private Long introducePartnerId;

    /**
     * 微信open_id
     */
    private String openid;

    /**
     * 微信session_key
     */
    private String sessionKey;

    /**
     * 微信关联日期
     */
    private Date relDate;

    /**
     * 到期日期
     */
    private Date dueDate;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 认证状态
     */
    private CertificationState certificationState = CertificationState.UNCERTIFIED;

    /**
     * 加盟协议状态
     */
    private SignState joinSignState = SignState.UNSIGN;

    /**
     * 账户状态
     */
    private AccountState partnerState = AccountState.NORMAL;

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
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 手机号码2
     */
    private String phoneNumber2;

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
    private String idcardPic;

    /**
     * 身份证反面图
     */
    private String idcardPicBack;

    /**
     * 正面自拍照
     */
    private String selfPic;

    /**
     * 身份证复印件图
     */
    private String idcarCopy;

    /**
     * 手持证件反面照
     */
    private String idcardBackHand;

    /**
     * 手持证件正面照
     */
    private String idcardHand;

    /**
     * 身份证验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus idcardVerifyStatus = VerifyStatus.TOVERIFY;

    /**
     * 身份证验证日期时间
     */
    private Date idcardVerifyDate;

    /**
     * 人脸验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus faceVerifyStatus = VerifyStatus.TOVERIFY;

    /**
     * 人脸验证日期时间
     */
    private Date faceVerifyDate;

    /**
     * 银行卡验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus bankCardVerifyStatus = VerifyStatus.TOVERIFY;

    /**
     * 银行卡验证日期时间
     */
    private Date bankCardVerifyDate;

    /**
     * 手机号码验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus phoneNumberVerifyStatus = VerifyStatus.TOVERIFY;

    /**
     * 手机号码验证日期时间
     */
    private Date phoneNumberVerifyDate;

    /**
     * 验证图片
     */
    private String picVerify;

    /**
     * 身份证验证类型：系统验证，手工验证
     */
    private IdcardVerifyType idcardVerifyType;

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
