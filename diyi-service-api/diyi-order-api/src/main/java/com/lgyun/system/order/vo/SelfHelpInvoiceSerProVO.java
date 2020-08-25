package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.ApplyState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/8/19.
 * @time 16:06.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceCrowdSourcingVO对象",description = "SelfHelpInvoiceCrowdSourcingVO对象")
public class SelfHelpInvoiceSerProVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //自助开票id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //购买方
    private ApplyState currentState;

    //购买方
    private String enterpriseName;

    //发票扫描件（可多张）
    private String invoiceScanPictures;

    //税票扫描件（可多张）
    private String taxScanPictures;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}