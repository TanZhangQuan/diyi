package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.PayMakerPayState;
import com.lgyun.common.enumeration.WorkSheetMode;
import com.lgyun.common.enumeration.WorkSheetType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class AcceptPaysheetDetailMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客支付明细ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 支付总额
     */
    private BigDecimal makerNetIncome;

    /**
     * 支付状态
     */
    private PayMakerPayState payState;

    /**
     * 服务开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceTimeStart;

    /**
     * 服务结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceTimeEnd;

    /**
     * 验收单URL
     */
    private String acceptPaysheetUrl;

    /**
     * 工单名称
     */
    private String worksheetName;

    /**
     * 类型
     */
    private WorkSheetType worksheetType;

    /**
     * 模式
     */
    private WorkSheetMode worksheetMode;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 工单编号
     */
    private String worksheetNo;

    /**
     * 验收金额
     */
    private BigDecimal checkMoney;

    /**
     * 工作成果说明
     */
    private String achievementDesc;

    /**
     * 工作成果附件
     */
    private String achievementFiles;

}
