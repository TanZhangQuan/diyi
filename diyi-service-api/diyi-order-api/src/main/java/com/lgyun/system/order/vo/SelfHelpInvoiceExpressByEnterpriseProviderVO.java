package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/8/3.
 * @time 15:36.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceExpressByEnterpriseVO对象", description = "SelfHelpInvoiceExpressByEnterpriseVO对象")
public class SelfHelpInvoiceExpressByEnterpriseProviderVO implements Serializable {

    /**
     * 发票
     */
    private String invoiceScanPictures;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递公司
     */
    private String expressCompanyName;

    /**
     * 快递信息
     */
    private String expressMessage;

}
