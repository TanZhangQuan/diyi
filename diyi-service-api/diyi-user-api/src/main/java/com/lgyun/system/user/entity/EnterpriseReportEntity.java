package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.enumeration.ReportState;
import com.lgyun.common.enumeration.ReportTheme;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 年度申报管理表 Entity
 *
 * @author liangfeihu
 * @since 2020-08-12 14:47:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_enterprise_report")
public class EnterpriseReportEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
}
