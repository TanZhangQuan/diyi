package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_self_help_invoice_person")
public class SelfHelpInvoicePersonEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

//    /**
//     * 对象id
//     */
//    private Long objectId;
//
//    /**
//     * 对象身份1、创客本人2、商户人员3、服务商人员4、相关局人员5、渠道商人员6、合伙人本人
//     */
//    private ObjectType objectType;

    /**
     * 身份证号码
     */
    private String idCardNo;

    /**
     * 身份证姓名
     */
    private String idCardName;

    /**
     * 身份证正面图
     */
    private String idCardPic;

    /**
     * 身份证反面图
     */
    private String idCardPicBack;

    /**
     * 手机号码
     */
    private String phoneNumber;

}
