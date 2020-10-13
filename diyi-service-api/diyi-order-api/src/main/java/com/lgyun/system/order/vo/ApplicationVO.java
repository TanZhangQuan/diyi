package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tzq
 * @date 2020/8/19.
 * @time 14:38.
 */
@Data
public class ApplicationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总包开票申请ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long applicationId;

    /**
     * 商户支付清单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;
}
