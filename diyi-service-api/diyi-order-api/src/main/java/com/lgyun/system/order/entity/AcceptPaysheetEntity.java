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
 * Entity
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_accept_paysheet")
public class AcceptPaysheetEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单ID
     */
    private Long payEnterpriseId;

    /**
     * 服务开始日期
     */
    private Date serviceTimeStart;

    /**
     * 服务结束日期
     */
    private Date serviceTimeEnd;

    /**
     * 交付支付验收单类型：清单式，单人单张
     */
    private AcceptPaysheetType acceptPaysheetType;

    /**
     * 创客ID
     */
    private Long makerId;

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
    private String acceptPaysheetUrl;

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
