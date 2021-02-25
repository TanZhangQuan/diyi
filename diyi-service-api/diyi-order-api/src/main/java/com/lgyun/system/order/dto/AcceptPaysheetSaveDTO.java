package com.lgyun.system.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.AcceptPaysheetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "上传交付支付验收单DTO")
public class AcceptPaysheetSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付清单ID")
    @NotNull(message = "请选择支付清单")
    private Long payEnterpriseId;

    @ApiModelProperty(value = "清单式分包支付明细ID集合")
    private List<Long> payMakerIdList;

    @ApiModelProperty(value = "单人单张分包支付明细ID")
    private Long payMakerId;

    @ApiModelProperty(value = "交付支付验收单类型", notes = "com.lgyun.common.enumeration.AcceptPaysheetType")
    @NotNull(message = "请选择交付支付验收单类型")
    private AcceptPaysheetType acceptPaysheetType;

    @ApiModelProperty(value = "交付支付验收单服务开始时间")
    @NotNull(message = "请选择服务开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serviceTimeStart;

    @ApiModelProperty(value = "交付支付验收单服务结束时间")
    @NotNull(message = "请选择服务结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serviceTimeEnd;

    @ApiModelProperty(value = "交付支付验收单")
    @NotBlank(message = "请上传交付支付验收单")
    private String acceptPaysheetUrl;




}
