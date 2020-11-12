package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/8/11.
 * @time 16:16.
 */
@Data
public class EnterpriseProviderInvoiceCatalogsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 开票类目ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 发票类目名称
     */
    private String invoiceCatalogName;
}
