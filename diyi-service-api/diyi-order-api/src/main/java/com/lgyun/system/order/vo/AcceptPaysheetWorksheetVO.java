package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.AcceptPaysheetType;
import com.lgyun.common.enumeration.WorkSheetMode;
import com.lgyun.common.enumeration.WorkSheetType;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "AcceptPaysheetWorksheetVO对象", description = "AcceptPaysheetWorksheetVO对象")
public class AcceptPaysheetWorksheetVO implements Serializable {

    //交付支付验收单类型：清单式，单人单张
    private AcceptPaysheetType acceptPaysheetType;

    //商户名称
    private String enterpriseName;

    //支付总额=外包费总额+总身份验证费+总开票手续费
    private BigDecimal payToPlatformAmount;

    //发布时间
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date publishDate;

    //关单时间
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date closeWorksheetDate;

    //验收单URL
    private String acceptPaysheetUrl;

    //工单名称
    private String worksheetName;

    //类型，总包+分包，众包/众采
    private WorkSheetType worksheetType;

    //模式，派单、抢单、混合（默认：混合型）
    private WorkSheetMode worksheetMode;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDate2;

    //工单编号
    private String worksheetNo;

    //验收金额
    private BigDecimal checkMoney;

    //工作成果说明
    private String achievementDesc;

    //工作成果附件
    private String achievementFiles;

}
