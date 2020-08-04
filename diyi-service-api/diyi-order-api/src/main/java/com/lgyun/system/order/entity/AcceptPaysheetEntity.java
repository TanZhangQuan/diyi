package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    /**
     * 交付支付验收单类型：清单式，单人单张
     */
    private AcceptPaysheetType acceptPaysheetType;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 上传来源
     */
    private String uploadDateSource;

    /**
     * 上传人员
     */
    private String uploadDatePerson;

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
