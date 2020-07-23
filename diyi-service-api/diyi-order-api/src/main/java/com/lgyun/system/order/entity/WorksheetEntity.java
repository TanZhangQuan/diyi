package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetMode;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("diyi_worksheet")
public class WorksheetEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 企业ID
     */
    private Long enterpriseId;

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
     * 发布时间
     */
    private Date publishDate;

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
     * 作废时间
     */
    private Date destroyDatetime;

    /**
     * 作废人员
     */
    private String destroyPerson;

    /**
     * 作废说明
     */
    private String destroyDesc;

    /**
     * 关单时间
     */
    private Date closeWorksheetDate;

    /**
     * 1，手动关单；2，自动关单
     */
    private String closeDesc;

    /**
     * 关单人员
     */
    private String closePerson;

    /**
     * 完毕日期
     */
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
