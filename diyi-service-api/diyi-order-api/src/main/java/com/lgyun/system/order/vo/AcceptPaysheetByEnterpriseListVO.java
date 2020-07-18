package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
@ApiModel(value = "AcceptPaysheetEnterpriseVO对象", description = "AcceptPaysheetEnterpriseVO对象")
public class AcceptPaysheetByEnterpriseListVO {

    //交付支付验收单ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //商户名称
    private String enterpriseName;

    //支付总额=外包费总额+总身份验证费+总开票手续费
    private BigDecimal payToPlatformAmount;

    //发布时间
    private Date publishDate;

    //关单时间
    private Date closeWorksheetDate;

}
