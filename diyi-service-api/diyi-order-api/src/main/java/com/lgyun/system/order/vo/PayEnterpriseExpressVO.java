package com.lgyun.system.order.vo;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class PayEnterpriseExpressVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "快递公司")
    private String expressCompanyName;

    @ApiModelProperty(value = "快递单号")
    private String expressSheetNo;

    @ApiModelProperty(value = "收件地址")
    private String receiptWorkingAddress;

    @ApiModelProperty(value = "收件人")
    private String receiptWorkingRelName;

    @ApiModelProperty(value = "收件人手机号")
    private String receiptWorkingRelPhone;

    @ApiModelProperty(value = "发件地址")
    private String sendWorkingAddress;

    @ApiModelProperty(value = "发件人")
    private String sendWorkingRelName;

    @ApiModelProperty(value = "发件人手机号")
    private String sendWorkingRelPhone;

    @ApiModelProperty(value = "物流信息")
    private JSONArray expressDetail;

}
