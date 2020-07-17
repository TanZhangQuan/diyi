package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Data
@NoArgsConstructor
@TableName("diyi_self_help_invoice_person")
public class SelfHelpInvoicePersonEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客id
     */
    private Long makerId;

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
