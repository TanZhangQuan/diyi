package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.enumeration.ReportTheme;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AdminEnterpriseReportDTO;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.lgyun.system.user.vo.EnterpriseReportsVO;
import com.lgyun.system.user.vo.admin.AdminEnterpriseReportAllVO;
import com.lgyun.system.user.vo.admin.AdminEnterpriseReportVO;

/**
 * 年度申报管理表 Service 接口
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
public interface IEnterpriseReportService extends BaseService<EnterpriseReportEntity> {

    /**
     * 根据申报主体类别，申报主体ID查询年审信息
     *
     * @param query
     * @param mainBodyType
     * @param mainBodyId
     * @return
     */
    R<IPage<EnterpriseReportsVO>> findByBodyTypeAndBodyId(Query query, BodyType mainBodyType, Long mainBodyId);

    /**
     * 根据申报主体类别，申报主体ID查询申报结果文件资料
     *
     * @param mainBodyType
     * @param mainBodyId
     * @return
     */
    String findReportResultFiles(BodyType mainBodyType, Long mainBodyId);

    /**
     *平台查询所有服务商税务申报或工商申报
     */
    R findAdminEnterpriseReportAll(String serviceProviderName, ReportTheme reportTheme,String startTime, String endTime,IPage<AdminEnterpriseReportAllVO> page);

    /**
     * 平台根据服务商查询税务申报或工商申报
     */
    R findAdminEnterpriseReport(Long serviceProviderId,ReportTheme reportTheme,IPage<AdminEnterpriseReportVO> page);

    /**
     * 平台查询税务申报或工商申报详情
     */
    R findAdminEnterpriseReportDetail(Long enterpriseReportId);

    /**
     *平台保存税务申报或工商申报
     */
    R saveAdminEnterpriseReport(AdminEnterpriseReportDTO adminEnterpriseReportDTO);

    /**
     * 平台审核税务申报或工商申报
     */
    R toExamineAdminEnterpriseReport(Long enterpriseReportId,Integer toExamine);


    /**
     * 根据服务商id查询税务申报或工商申报
     */
    R findServiceEnterpriseReport(Long serviceProviderId,ReportTheme reportTheme,String startTime,String endTime,IPage<AdminEnterpriseReportAllVO> page);


    /**
     *服务商保存税务申报或工商申报
     */
    R saveServiceEnterpriseReport(AdminEnterpriseReportDTO adminEnterpriseReportDTO);
}

