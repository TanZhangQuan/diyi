package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.WorkSheetMode;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.enumeration.WorksheetMakerState;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/7/7.
 * @time 18:22.
 */
@Data
public class WorksheetXiaoVo implements Serializable {

    /**
     * 工单创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetMakerId;

    /**
     * 工单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetId;

    /**
     * 企业ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 企业姓名
     */
    private String enterpriseName;

    /**
     * 工单编号
     */
    private String worksheetNo;

    /**
     * 工单名称
     */
    private String worksheetName;

    /**
     * 上线人数
     */
    private Integer upPersonNum;

    /**
     * 工作天数
     */
    private Integer workDays;

    /**
     * 费用
     */
    private BigDecimal worksheetFee;

    /**
     * 类型，总包+分包，众包/众采
     */
    private WorkSheetType worksheetType;

    /**
     * 模式，派单、抢单、混合（默认：混合型）
     */
    private WorkSheetMode worksheetMode;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDate;

    /**
     * 工单创客的状态：1待提交，2待验证，3验证通过，4验证失败
     */
    private WorksheetMakerState worksheetMakerState;

    /**
     * 验收金额
     */
    private BigDecimal checkMoney;

    /**
     *工作成果说明
     */
    private String achievementDesc;

    /**
     * 工作成果附件
     */
    private String achievementFiles;
}
