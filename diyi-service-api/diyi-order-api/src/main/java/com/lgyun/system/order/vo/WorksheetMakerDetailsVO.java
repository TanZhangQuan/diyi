package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author .
 * @date 2020/7/27.
 * @time 15:53.
 */
@Data
@ApiModel(value = "WorksheetMakerDetailsVO对象", description = "WorksheetMakerDetailsVO对象")
public class WorksheetMakerDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单创客Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetMakerId;

    /**
     * 名字
     */
    private String name;

    /**
     * 身份证
     */
    private String idcardNo;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 身份证验证状态
     */
    private VerifyStatus idcardVerifyStatus;

    /**
     * 人脸验证状态
     */
    private VerifyStatus faceVerifyStatus;

    /**
     * 银行卡验证状态
     */
    private VerifyStatus bankCardVerifyStatus;

    /**
     * 电话验证状态
     */
    private VerifyStatus phoneNumberVerifyStatus;

    /**
     * 短视频审核状态
     */
    private VideoAudit videoAudit;

    /**
     * 授权协议状态
     */
    private SignState empowerSignState;

    /**
     * 加盟协议状态
     */
    private SignState joinSignState;

    /**
     * 实名认证
     */
    private CertificationState realNameAuthentication = CertificationState.UNCERTIFIED;

    /**
     * 协议状态
     */
    private CertificationState protocolAuthentication = CertificationState.UNCERTIFIED;

    /**
     * 工单创客的状态
     */
    private WorksheetMakerState worksheetMakerState;

    /**
     * 工作成果说明
     */
    private String achievementDesc;

    /**
     * 工作成果url
     */
    private String achievementFiles;

    /**
     * 提交工作成果时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date achievementDate;

    /**
     * 验收金额
     */
    private BigDecimal checkMoney;
}
