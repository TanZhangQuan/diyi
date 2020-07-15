package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetMode;
import com.lgyun.common.enumeration.WorkSheetType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 发布工单
 * @author jun.
 * @date 2020/7/7.
 * @time 15:04.
 */
@Data
public class ReleaseWorksheetDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 工单名称
     */
    @NotBlank(message = "请输入工单名称")
    private String worksheetName;

    /**
     * 上线人数
     */
    @Min(1)
    private Integer uppersonNum;

    /**
     * 工作天数
     */
    @Min(1)
    private Integer workDays;

    /**
     * 费用
     */
    @Min(0)
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
     * 创客身份，自然人，个体户，个独。如果是个体户/个独，则抢单或派单时需要指定相关个体户/个独，如果只有一个则不用指定。
     */
    private MakerType makerType;

    /**
     * 创客ids
     */
    private String MakerIds;

}
