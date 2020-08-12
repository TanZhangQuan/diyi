package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.ReportState;
import com.lgyun.common.enumeration.ReportTheme;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/7/29.
 * @time 16:47.
 */
@Data
@ApiModel(value = "AgreementMakerWebVO对象", description = "AgreementMakerWebVO对象")
public class EnterpriseReportsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 申报主题
     */
    private String reportTheme;

    /**
     * 申报年度
     */
    private ReportTheme reportYear;

    /**
     * 申报季度
     */
    private Integer reportQuater;

    /**
     * 申报月度
     */
    private Integer reportMonth;

    /**
     * 申报截止日期
     */
    private Date reportDeadDate;

    /**
     * 申报完成日期
     */
    private Date reportCompleteDate;

    /**
     * 申报结果说明
     */
    private String reportResultDesc;

    /**
     * 申报结果文件资料
     */
    private String reportResultFiles;

    /**
     * 申报人员
     */
    private String reportPerson;

    /**
     * 申报状态
     */
    private ReportState reportState;

    /**
     * 申报相关政府机关名称
     */
    private String reportGuardName;
}
