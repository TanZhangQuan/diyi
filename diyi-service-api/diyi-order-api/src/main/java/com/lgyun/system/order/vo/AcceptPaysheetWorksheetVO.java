package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.AcceptPaysheetType;
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
public class AcceptPaysheetWorksheetVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 交付支付验收单类型：清单式，单人单张
     */
    private AcceptPaysheetType acceptPaysheetType;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 支付总额
     */
    private BigDecimal payToPlatformAmount;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 关单时间
     */
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date closeWorksheetDate;

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
    private Date createTime2;

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
