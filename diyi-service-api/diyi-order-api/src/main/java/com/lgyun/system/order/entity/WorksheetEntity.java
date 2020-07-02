package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-06-29 10:39:06
 */
@Data
@NoArgsConstructor
@TableName("diyi_worksheet")
public class WorksheetEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 平台运营工单信息ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "worksheet_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetId;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    @JsonSerialize(using = ToStringSerializer.class)
        private Long orderId;

    /**
     * 创客ID
     */
    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
        private Long makerId;

    /**
     * 工单编号
     */
        private String worksheetNo;

    /**
     * 抢单日期
     */
        private Date getOrderDate;

    /**
     * 抢单说明
     */
        private String getOrderDesc;

    /**
     * 工单状态：正常，已作废
     */
        private String worksheetState;

    /**
     * 作废日期
     */
        private Date destroyDateTime;

    /**
     * 作废人员
     */
        private String destroyPerson;
    }
