package com.lgyun.system.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付清单DTO
 *
 * @author tzq.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class PayEnterpriseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 商户支付清单状态
     */
    private PayEnterpriseAuditState payEnterpriseAuditState;

    /**
     * 上传支付清单开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    /**
     * 上传支付清单结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
