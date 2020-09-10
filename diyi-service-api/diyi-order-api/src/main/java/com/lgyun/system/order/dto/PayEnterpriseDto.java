package com.lgyun.system.order.dto;

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
public class PayEnterpriseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //总包ID
    private Long payEnterpriseId;

    //服务商名称
    private String serviceProviderName;

    //商户名称
    private String enterpriseName;

    //总包审核状态
    private PayEnterpriseAuditState payEnterpriseAuditState;

    //上传支付清单开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    //上传支付清单结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
