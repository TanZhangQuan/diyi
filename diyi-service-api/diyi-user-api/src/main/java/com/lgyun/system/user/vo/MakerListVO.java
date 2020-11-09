package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.VideoAudit;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创客列表VO
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class MakerListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String idcardNo;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 是否实名
     */
    private boolean boolRealNameVerify;

    /**
     * 是否已签协议
     */
    private boolean boolSign;

    /**
     * 是否个体户
     */
    private boolean boolIndividualBusiness;

    /**
     * 是否个独
     */
    private boolean boolIndividualEnterprise;

    /**
     * 认证时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date certificationDate;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 视频状态
     */
    private VideoAudit videoAudit;

    /**
     * 是否有商户和创客的补充协议
     */
    private boolean boolSupplementSign;

}
