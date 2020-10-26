package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/10/24.
 * @time 17:36.
 */
@Data
public class EnterpriseServicePayListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户支付清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;
    /**
     * 服务商名字
     */
    private String serviceProviderName;
    /**
     * 支付清单创建时间
     */
    private String createTime;
    /**
     * 清单url
     */
    private String chargeListUrl;
}
