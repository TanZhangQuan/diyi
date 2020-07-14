package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.DateUtil;
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
@TableName("diyi_agreement")
public class AgreementEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 平台合同的信息ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;

    /**
     * 签署类型：纸质协议，平台在线协议，三方在线协议-法大大
     */
    private SignType signType;

    /**
     * 签署日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date signDate;

    /**
     * 签署状态：签署中，已完毕
     */
    private SignState signState;

    /**
     * 协议类型：互联网众包项目合作协议，众包-三方合作协议，众包-三方服务订单，总包-双方合作协议，总包-双方服务订单，分包-三方合作协议，分包-三方服务订单，分包-双方合作协议，分包-双方服务订单，单独税务事项委托授权书，单独支付事项委托授权书
     */
    private AgreementType agreementType;

    /**
     * 协议编号
     */
    private String agreementNo;

    /**
     * 发包方ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long runCompanyId;

    /**
     * 平台方ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long platformId;

    /**
     * 分包方/承包方ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 上传人员
     */
    private String uploadPerson;

    /**
     * 上传日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date uploadDateTime;

    /**
     * 在线协议发起时间
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date startDateTime;

    /**
     * 纸质协议URL
     */
    private String paperAgreementUrl;

    /**
     * 三方在线协议URL
     */
    private String thirdOnlineAgreementUrl;

    /**
     * 平台在线协议文本
     */
    private String platformOnlineAgreementUrl;

    /**
     * 平台在线协议平台方签字
     */
    private String poaplatformSignatureUrl;

    /**
     * 平台在线协议分包方签字
     */
    private String poamakerSignatureUrl;

    /**
     * 平台在线协议发包方签字
     */
    private String poacompanySignatureUrl;

    /**
     * 平台在线协议平台方操作日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date poaplatformSignDateTime;

    /**
     * 平台在线协议分包方操作日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date poamakerSignDateTime;

    /**
     * 平台在线协议发包方操作日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date poacompanySignDateTime;

    /**
     * 平台在线协议文本集合签字
     */
    private String poawithSignatureUrl;

    /**
     * 纸质协议上传状态：未上传, 已上传
     */
    private PaaState paaState;

    /**
     * 平台在线协议状态：签署中，已签署完毕
     */
    private AgreementState poaState;

    /**
     * 三方在线协议状态：签署中，已签署完毕
     */
    private AgreementState toaState;

    /**
     * 平台签署状态：签署中，已签署，无需签署（没参与）
     */
    private SignState platformSignState;

    /**
     * 创客签署状态：签署中，已签署，无需签署（没参与）
     */
    private SignState makerSignState;

    /**
     * 发包方签署状态：签署中，已签署，无需签署（没参与）
     */
    private SignState companySignState;

}
