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
 * 总包+分包交付支付验收单表 Entity
 *
 * @author tzq
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
     * 交付支付验收单类型：清单式，单人单张
     */
    private AcceptPaysheetType acceptPaysheetType;

    /**
     * 分包支付明细ID
     */
    private Long payMakerId;

    /**
     * 服务开始日期
     */
    private Date serviceTimeStart;

    /**
     * 服务结束日期
     */
    private Date serviceTimeEnd;

    /**
     * 验收单URL
     */
    private String acceptPaysheetUrl;

}
