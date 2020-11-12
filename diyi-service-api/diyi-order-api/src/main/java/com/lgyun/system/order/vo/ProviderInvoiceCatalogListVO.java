package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.ApplyScope;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class ProviderInvoiceCatalogListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商发票类目ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 应用范围
     */
    private ApplyScope applyScope;

    /**
     * 发票类目名称
     */
    private String invoiceCatalogName;

    /**
     * 设置人员
     */
    private String setPerson;

    /**
     * 设置说明
     */
    private String setDesc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
