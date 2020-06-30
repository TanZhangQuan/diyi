package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.IdcardVerifyStatus;
import com.lgyun.common.enumeration.IdcardVerifyType;
import com.lgyun.common.enumeration.VideoAudit;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Data
@NoArgsConstructor
@TableName("diyi_maker")
public class MakerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客（分包方）的基本信息ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 微信关联ID
     */
    private String wechatId;

    /**
     * 微信open_id
     */
    private String openid;

    /**
     * 微信session_key
     */
    private String sessionKey;

    /**
     * 微信昵称
     */
    private String wechatNickname;

    /**
     * 微信关联日期
     */
    private Date relDate;

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
     * 手持证件照
     */
    private String idcardHand;

    /**
     * 身份验证状态：未验证，验证通过，验证未通过
     */
    private IdcardVerifyStatus idcardVerifyStatus;

    /**
     * 验证日期时间
     */
    private Date idcardVerifyDate;

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

    /**
     * 商铺ID
     */
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
    private Date videoAuditDate;

    /**
     * 审核人员
     */
    private Long videoAuditPersonId;

}
