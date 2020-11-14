package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
     * 自主开票详情ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 服务开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceTimeStart;

    /**
     * 服务结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceTimeEnd;

    /**
     * 验收单URL
     */
    private String acceptPaysheetCsUrl;

}
