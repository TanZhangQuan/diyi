package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台根据商户id查询合作服务商的合同
 * @author .
 * @date 2020/9/10.
 * @time 12:00.
 */
@Data
@ApiModel(value = "AgreementEnterServiceAdminVO对象", description = "AgreementEnterServiceAdminVO对象")
public class AgreementEnterServiceAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 合同id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;
    /**
     * 服务商id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long service_provider_id;
    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;
    /**
     *协议编号
     */
    private String agreementNo;
    /**
     * 服务商名字
     */
    private String service_provider_name;
    /**
     * 商户名字
     */
    private String enterpriseName;
    /**
     * 在线签约合同url
     */
    private String onlineAggrementUrl;
    /**
     * 纸质协议URL
     */
    private String paperAgreementUrl;
    /**
     * 发布时间
     */
    private String createTime;
}
