package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.ReportState;
import com.lgyun.common.enumeration.ReportTheme;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class EnterpriseReportsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "申报主题")
    private String reportTheme;

    @ApiModelProperty(value = "申报年度")
    private ReportTheme reportYear;

    @ApiModelProperty(value = "申报季度")
    private Integer reportQuater;

    @ApiModelProperty(value = "申报月度")
    private Integer reportMonth;

    @ApiModelProperty(value = "申报截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDeadDate;

    @ApiModelProperty(value = "申报完成日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportCompleteDate;

    @ApiModelProperty(value = "申报结果说明")
    private String reportResultDesc;

    @ApiModelProperty(value = "申报结果文件资料")
    private String reportResultFiles;

    @ApiModelProperty(value = "申报人员")
    private String reportPerson;

    @ApiModelProperty(value = "申报状态")
    private ReportState reportState;

    @ApiModelProperty(value = "申报相关政府机关名称")
    private String reportGuardName;

}
