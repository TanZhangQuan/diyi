package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.*;
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
public class WorksheetNoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单名称
     */
    private String worksheetName;

    /**
     * 上限人数
     */
    private Integer uppersonNum;

    /**
     * 工作天数
     */
    private Integer workDays;

    /**
     * 最低费用
     */
    private BigDecimal worksheetFeeLow;

    /**
     * 最高费用
     */
    private BigDecimal worksheetFeeHigh;

    /**
     * 类型，总包+分包，众包/众采
     */
    private WorkSheetType worksheetType;

    /**
     * 模式，派单、抢单、混合（默认：混合型）
     */
    private WorkSheetMode worksheetMode;

    /**
     * 创客身份，自然人，个体户，个独。如果是个体户/个独，则抢单或派单时需要指定相关个体户/个独，如果只有一个则不用指定。
     */
    private MakerType makerType;

    /**
     * 工单状态：
     * a) 发布中，发布代抢单或代派单的工单
     * b) 已关单，已经抢单或者派单完毕（人数不做控制依据）
     * c) 验收中，有个人创客提交了工单等待验收或部分验收完毕
     * d) 已完毕，所有个人创客都验收完毕了
     * e) 已作废，验收中工单都可以作废，已完毕的不能作废
     */
    private WorksheetState worksheetState;

    /**
     * 完毕日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishDate;

    /**
     * 说明
     */
    private String worksheetMemo;

    /**
     * 图文说明
     */
    private String worksheetDescFiles;

}
