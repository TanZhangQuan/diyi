package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.enumeration.ReportState;
import com.lgyun.common.enumeration.ReportTheme;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author .
 * @date 2020/9/23.
 * @time 16:34.
 */
@Data
public class AdminEnterpriseReportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long enterpriseReportId;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 申报主体类别
     */
    private BodyType mainBodyType;

    /**
     * 申报主体ID
     */
    private Long mainBodyId;

    /**
     * 申报主题
     */
    private ReportTheme reportTheme;

    /**
     * 申报年度
     */
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
    /**
     * 主体名字
     */
    private String mainBodyName;
}
