package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "创客详情VO")
public class MakerDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信关联日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date relDate;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "认证状态")
    private CertificationState certificationState;

    @ApiModelProperty(value = "是否有有效的创客授权书")
    private Boolean boolPowerAttorney;

    @ApiModelProperty(value = "是否有有效的创客加盟协议")
    private Boolean boolJoinAgreement;

    @ApiModelProperty(value = "账户状态")
    private AccountState makerState;

    @ApiModelProperty(value = "政治面貌")
    private String politicState;

    @ApiModelProperty(value = "民族")
    private String nationality;

    @ApiModelProperty(value = "文化程度")
    private String levelofedu;

    @ApiModelProperty(value = "电子邮箱")
    private String emailAddress;

    @ApiModelProperty(value = "身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "到期日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dueDate;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "开户银行")
    private String bankName;

    @ApiModelProperty(value = "开户支行")
    private String subBankName;

    @ApiModelProperty(value = "身份证正面图")
    private String idcardPic;

    @ApiModelProperty(value = "身份证反面图")
    private String idcardPicBack;

    @ApiModelProperty(value = "正面自拍照")
    private String selfPic;

    @ApiModelProperty(value = "身份证复印件图")
    private String idcardCopy;

    @ApiModelProperty(value = "手持证件正面照")
    private String idcardHand;

    @ApiModelProperty(value = "手持证件反面照")
    private String idcardBackHand;

    @ApiModelProperty(value = "活体截图")
    private String picVerify;

    @ApiModelProperty(value = "身份证验证状态")
    private VerifyStatus idcardVerifyStatus;

    @ApiModelProperty(value = "身份证验证日期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date idcardVerifyDate;

    @ApiModelProperty(value = "活体验证状态")
    private VerifyStatus faceVerifyStatus;

    @ApiModelProperty(value = "活体验证日期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date faceVerifyDate;

    @ApiModelProperty(value = "银行卡验证状态")
    private VerifyStatus bankCardVerifyStatus;

    @ApiModelProperty(value = "银行卡验证日期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bankCardVerifyDate;

    @ApiModelProperty(value = "手机号码验证状态")
    private VerifyStatus phoneNumberVerifyStatus;

    @ApiModelProperty(value = "手机号码验证日期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date phoneNumberVerifyDate;

    @ApiModelProperty(value = "身份证验证类型")
    private IdcardVerifyType idcardVerifyType;

    @ApiModelProperty(value = "手工验证人")
    private String manualVerifyName;

    @ApiModelProperty(value = "验证描述")
    private String manualVerifyDesc;

    @ApiModelProperty(value = "线上经营场所")
    private String runAddress;

    @ApiModelProperty(value = "线下经营地址")
    private String houseAddress;

    @ApiModelProperty(value = "住址")
    private String livingAddress;

    @ApiModelProperty(value = "自我描述")
    private String selfDesc;

    @ApiModelProperty(value = "商铺")
    private String shopUrl;

    @ApiModelProperty(value = "商铺用户名")
    private String shopUserName;

    @ApiModelProperty(value = "声明短视频")
    private String applyShortVideo;

    @ApiModelProperty(value = "短视频审核状态")
    private VideoAudit videoAudit;

    @ApiModelProperty(value = "审核日期")
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date videoAuditDate;

    @ApiModelProperty(value = "审核人员")
    private String videoAuditPersonName;

}
