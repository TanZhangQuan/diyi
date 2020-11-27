package com.lgyun.system.order.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayEnterpriseExpressVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 快递公司
     */
    private String expressCompanyName;

    /**
     * 快递单号
     */
    private String expressSheetNo;

    /**
     * 收件地址
     */
    private String receiptWorkingAddress;

    /**
     * 收件人
     */
    private String receiptWorkingRelName;

    /**
     * 收件人手机号
     */
    private String receiptWorkingRelPhone;

    /**
     * 发件地址
     */
    private String sendWorkingAddress;

    /**
     * 发件人
     */
    private String sendWorkingRelName;

    /**
     * 发件人手机号
     */
    private String sendWorkingRelPhone;

    /**
     * 物流信息
     */
    private JSONObject expressDetail;

}
