package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
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
public class SelfHelpInvoicesByEnterpriseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //自助开票编号
    private Long selfHelpInvoiceId;

    //创客类别
    @NotNull(message = "请选择创客类型")
    private MakerType makerType;

    //自主开票开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    //自主开票结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
