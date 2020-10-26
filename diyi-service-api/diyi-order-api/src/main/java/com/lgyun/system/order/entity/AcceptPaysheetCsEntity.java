package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AcceptPaysheetType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 众包交付支付验收单表 Entity
 *
 * @author liangfeihu
 * @since 2020-08-05 10:43:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_accept_paysheet_cs")
public class AcceptPaysheetCsEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票Id
     */
    private Long selfHelpInvoiceId;

    /**
     * 交付支付验收单类型：清单式，单人单张
     */
    private AcceptPaysheetType acceptPaysheetCsType;

    /**
     * 自助开票明细Id
     */
    private Long selfHelpInvoiceDetailId;

    /**
     * 服务开始日期
     */
    private Date serviceTimeStart;

    /**
     * 服务结束日期
     */
    private Date serviceTimeEnd;

    /**
     * 上传来源
     */
    private String uploadSource;

    /**
     * 上传人员
     */
    private String uploadPerson;

    /**
     * 验收单URL
     */
    private String acceptPaysheetCsUrl;

    /**
     * 验收单验收日期
     */
    private Date confirmDate;

    /**
     * 验收人员
     */
    private String confirmPerson;

    /**
     * 验收说明
     */
    private String confirmDesc;

}
