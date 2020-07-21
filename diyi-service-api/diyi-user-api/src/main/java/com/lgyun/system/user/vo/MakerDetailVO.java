package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.IdcardVerifyType;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.common.enumeration.VideoAudit;
import com.lgyun.common.tool.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * app创客详情VO
 *
 * @author liangfeihu
 * @since 2020/6/6 00:28
 */
@Data
public class MakerDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客（分包方）的基本信息ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 微信关联ID
     */
    private String wechatId;

    /**
     * 微信昵称
     */
    private String wechatNickname;

    /**
     * 微信关联日期
     */
    private Date relDate;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date createTime;

    /**
     * 姓名
     */
    private String name;

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
     * 到期日期
     */
    private Date dueDate;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 手机号码2
     */
    private String phoneNumber2;

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
     * 手持证件正面照
     */
    private String idcardHand;

    /**
     * 手持证件反面照
     */
    private String idcardBackHand;

    /**
     * 身份证验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus idcardVerifyStatus;

    /**
     * 身份证验证日期时间
     */
    private Date idcardVerifyDate;

    /**
     * 人脸验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus faceVerifyStatus;

    /**
     * 人脸验证日期时间
     */
    private Date faceVerifyDate;

    /**
     * 银行卡验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus bankCardVerifyStatus;

    /**
     * 银行卡验证日期时间
     */
    private Date bankCardVerifyDate;

    /**
     * 手机号码验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus phoneNumberVerifyStatus;

    /**
     * 手机号码验证日期时间
     */
    private Date phoneNumberVerifyDate;

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

    /**
     * 商铺ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long shopId;

    /**
     * 商铺URL
     */
    private String shopUrl;

    /**
     * 商铺用户名
     */
    private String shopUserName;

    /**
     * 声明短视频
     */
    private String applyShortVideo;

    /**
     * 短视频审核状态：未审核，审核通过，审核未通过
     */
    private VideoAudit videoAudit;

    /**
     * 审核日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date videoAuditDate;

    /**
     * 审核人员
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long videoAuditPersonId;

}
