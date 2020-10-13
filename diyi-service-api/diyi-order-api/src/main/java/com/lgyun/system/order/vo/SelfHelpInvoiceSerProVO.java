package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @date 2020/8/19.
 * @time 16:06.
 */
@Data
public class SelfHelpInvoiceSerProVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 自助开票状态
     */
    private SelfHelpInvoiceApplyState currentState;

    /**
     * 购买方
     */
    private String enterpriseName;

    /**
     * 发票扫描件（多张）
     */
    private String invoiceScanPictures;

    /**
     * 税票扫描件（多张）
     */
    private String taxScanPictures;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
