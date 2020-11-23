package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.enumeration.ReportTheme;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author .
 * @date 2020/9/23.
 * @time 17:40.
 */
@Data
public class AdminEnterpriseReportDTO implements Serializable {


    private Long enterpriseReportId;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 申报主体类别
     */
    @NotNull(message = "请选择申报主体类别")
    private BodyType mainBodyType;

    /**
     * 申报主体ID
     */
    @NotNull(message = "请选择申报主体")
    private Long mainBodyId;

    /**
     * 申报主题
     */
    @NotNull(message = "请输入申报主题")
    private ReportTheme reportTheme;

    /**
     * 申报年度
     */
    @NotNull(message = "请输入申报年度")
    private Integer reportYear;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportDeadDate;

    /**
     * 申报完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 申报相关政府机关名称
     */
    private String reportGuardName;
}
