package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.enumeration.ReportTheme;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class AdminEnterpriseReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "申报ID")
    private Long enterpriseReportId;

    @ApiModelProperty(value = "服务商ID")
    private Long serviceProviderId;

    @ApiModelProperty(value = "申报主体类别", notes = "com.lgyun.common.enumeration.BodyType")
    @NotNull(message = "请选择申报主体类别")
    private BodyType mainBodyType;

    @ApiModelProperty(value = "申报主体ID")
    @NotNull(message = "请选择申报主体")
    private Long mainBodyId;

    @ApiModelProperty(value = "申报主题", notes = "com.lgyun.common.enumeration.ReportTheme")
    @NotNull(message = "请输入申报主题")
    private ReportTheme reportTheme;

    @ApiModelProperty(value = "申报年度")
    @NotNull(message = "请输入申报年度")
    private Integer reportYear;

    @ApiModelProperty(value = "申报季度")
    private Integer reportQuater;

    @ApiModelProperty(value = "申报月度")
    private Integer reportMonth;

    @ApiModelProperty(value = "申报截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportDeadDate;

    @ApiModelProperty(value = "申报完成日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportCompleteDate;

    @ApiModelProperty(value = "申报结果说明")
    private String reportResultDesc;

    @ApiModelProperty(value = "申报结果文件资料")
    private String reportResultFiles;

    @ApiModelProperty(value = "申报人员")
    private String reportPerson;

    @ApiModelProperty(value = "申报相关政府机关名称")
    private String reportGuardName;
}
