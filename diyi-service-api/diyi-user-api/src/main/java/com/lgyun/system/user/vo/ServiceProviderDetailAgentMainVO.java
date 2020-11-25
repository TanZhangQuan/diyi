package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author .
 * @date 2020/7/28.
 * @time 14:55.
 */
@Data
public class ServiceProviderDetailAgentMainVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 法人名称
     */
    private String legalPersonName;

    /**
     * 联系人
     */
    private String contact1Name;

    /**
     * 联系人电话
     */
    private String contact1Phone;

    /**
     * 统一社会信用代码
     */
    private String socialCreditNo;

    /**
     * 营业执照
     */
    private String bizLicenceUrl;

    /**
     * 加盟合同
     */
    private String joinContract;

    /**
     * 状态
     */
    private AccountState serviceProviderState;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
