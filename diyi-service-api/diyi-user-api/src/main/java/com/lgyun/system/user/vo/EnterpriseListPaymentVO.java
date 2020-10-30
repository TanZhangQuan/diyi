package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---支付管理---商户支付管理vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class EnterpriseListPaymentVO implements Serializable {
    private static final long serialVersionUID = 1L;

     /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

     /**
     * 商户名称
     */
    private String enterpriseName;

     /**
     * 是否已签合同
     */
    private Boolean boolSignContract;

     /**
     * 是否已签协议
     */
    private Boolean boolSignAgreement;

}
