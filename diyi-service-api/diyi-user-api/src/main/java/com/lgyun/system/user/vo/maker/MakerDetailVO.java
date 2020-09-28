package com.lgyun.system.user.vo.maker;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创客端---首页管理---创客详情VO
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class MakerDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 认证状态
     */
    private CertificationState certificationState = CertificationState.UNCERTIFIED;

    /**
     * 授权合同签署状态
     */
    private SignState empowerSignState = SignState.UNSIGN;

    /**
     * 加盟合同签署状态
     */
    private SignState joinSignState = SignState.UNSIGN;

    /**
     * 账户状态
     */
    private AccountState makerState = AccountState.NORMAL;

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
     * 手持证件正面照
     */
    private String idcardHand;

    /**
     * 手持证件反面照
     */
    private String idcardBackHand;

    /**
     * 人脸截图
     */
    private String picVerify;

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
     * 商铺
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
    private VideoAudit videoAudit = VideoAudit.TOAUDIT;

    /**
     * 审核日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date videoAuditDate;

    /**
     * 审核人员
     */
    private Long videoAuditPersonId;

}