package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.GetType;
import com.lgyun.common.enumeration.WorksheetMakerState;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Data
@NoArgsConstructor
@TableName("diyi_worksheet_maker")
public class WorksheetMakerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 工单创客ID
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long worksheetMakerId;

    /**
     * 创客ID
     */
        private Long makerId;

    /**
     * 工单ID
     */
        private Long worksheetId;

    /**
     * 创客姓名
     */
        private String makerName;

    /**
     * 获得方式：1,抢单获得；2，派单获得
     */
        private GetType getType;

    /**
     * 工单创客的状态：1待提交，2待验证，3验证通过，4验证失败
     */
    private WorksheetMakerState worksheetMakerState;

    /**
     * 抢单/派单日期
     */
        private Date getOrderDate;

    /**
     * 工作成果说明
     */
        private String achievementDesc;

    /**
     * 工作成果附件
     */
        private String achievementFiles;

    /**
     * 提交工作成果日期
     */
        private Date achievementDate;

    /**
     * 验收金额
     */
        private BigDecimal checkMoney;

    /**
     * 验收人员
     */
        private String checkPerson;

    /**
     * 验收时间
     */
        private Date checkDate;

    /**
     * 派单人员
     */
        private String arrangePerson;

    /**
     * 派单日期
     */
        private Date arrangeDate;
    }
