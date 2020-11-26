package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/11/12.
 * @time 11:51.
 */
@Data
public class EnterpriseApplyDetailVO implements Serializable {
   /**
    * 总包申请id
    */
   @JsonSerialize(using = ToStringSerializer.class)
   private Long invoiceApplicationId;
   /**
    * 总包支付清单ID
    */
   @JsonSerialize(using = ToStringSerializer.class)
   private Long payEnterpriseId;
   /**
    * 商户名字
    */
   private String enterpriseName;
   /**
    * 支付清单url
    */
   private String chargeListUrl;
   /**
    * 支付回单,多个用逗号隔开的
    */
   private String enterprisePayReceiptUrl;
   /**
    * 工单id
    */
   @JsonSerialize(using = ToStringSerializer.class)
   private String worksheetId;
   /**
    * 交付支付验收单，多个用逗号隔开的
    */
   private String acceptPaysheetUrl;
   /**
    * 申请状态，0 未申请，1已申请
    */
   private String applyState;
   /**
    * 申请说明
    */
   private String applicationDesc;
   /**
    * 发票类目
    */
   private String invoiceCatalogName;
   /**
    * 价税合计额
    */
   private String payToPlatformAmount;
   /**
    * 服务商名字
    */
   private String serviceProviderName;
   /**
    * 申请状态
    */
   private String applicationState;
   /**
    * 申请时间
    */
   private String createTime;
   /**
    * 开票信息名称
    */
   private String invoiceEnterpriseName;
   /**
    * 开票信息纳税人识别号
    */
   private String invoiceTaxNo;
   /**
    * 开票信息电话是地址
    */
   private String invoiceAddressPhone;
   /**
    * 开票信息银行卡号
    */
   private String invoiceBankNameAccount;
   /**
    * 收件人姓名
    */
   private String addressName;
   /**
    * 收件人电话
    */
   private String addressPhone;
   /**
    * 收件人地区
    */
   private String area;
   /**
    * 收件人城市
    */
   private String city;
   /**
    * 收件人省份
    */
   private String province;
   /**
    * 收件人收人详细地址
    */
   private String detailedAddress;
}
