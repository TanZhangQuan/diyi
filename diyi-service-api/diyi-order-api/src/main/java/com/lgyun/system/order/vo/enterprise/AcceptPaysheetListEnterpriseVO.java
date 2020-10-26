package com.lgyun.system.order.vo.enterprise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
public class AcceptPaysheetListEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总包+分包交付支付验收单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 支付清单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    /**
     * 创客支付明细ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payMakerId;

    /**
     * 支付清单URL
     */
    private String chargeListUrl;

    /**
     * 创客名称(多个逗号隔开)
     */
    private String names;

    /**
     * 工单编号
     */
    private String worksheetId;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceTimeStart;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceTimeEnd;

    /**
     * 验收单URL
     */
    private String acceptPaysheetUrl;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
