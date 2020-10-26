package com.lgyun.system.order.vo.maker;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class AcceptPaysheetCsDetailMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 支付总额
     */
    private BigDecimal totalPayProviderFee;

    /**
     * 服务开始日期
     */
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date serviceTimeStart;

    /**
     * 服务结束日期
     */
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date serviceTimeEnd;

    /**
     * 验收单URL
     */
    private String acceptPaysheetUrl;

}
