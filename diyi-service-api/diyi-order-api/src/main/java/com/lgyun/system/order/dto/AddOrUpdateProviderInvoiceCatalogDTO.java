package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.ApplyScope;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 服务商发票类目
 *
 * @author tzq
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class AddOrUpdateProviderInvoiceCatalogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商发票类目ID
     */
    private Long invoiceCatalogId;

    /**
     * 应用范围
     */
    @NotNull(message = "请选择应用范围")
    private ApplyScope applyScope;

    /**
     * 发票类目名称
     */
    @NotBlank(message = "请输入发票类目名称")
    private String invoiceCatalogName;

    /**
     * 设置说明
     */
    private String setDesc;

}
