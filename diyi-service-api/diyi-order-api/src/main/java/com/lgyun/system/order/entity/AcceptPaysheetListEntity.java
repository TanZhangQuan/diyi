package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 众包交付支付验收单清单表 Entity
 *
 * @author tzq
 * @since 2020-10-29 19:55:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_accept_paysheet_list")
public class AcceptPaysheetListEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 总包交付支付验收单ID
     */
    private Long acceptPaysheetId;

    /**
     * 创客支付明细ID
     */
    private Long payMakerId;

}
