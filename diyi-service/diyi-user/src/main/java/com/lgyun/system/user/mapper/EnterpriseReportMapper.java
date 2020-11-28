package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.ReportTheme;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.lgyun.system.user.vo.AdminEnterpriseReportAllVO;
import com.lgyun.system.user.vo.AdminEnterpriseReportVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 年度申报管理表 Mapper
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
@Mapper
public interface EnterpriseReportMapper extends BaseMapper<EnterpriseReportEntity> {

    /**
     * 平台查询所有服务商税务申报或工商申报
     */
    List<AdminEnterpriseReportAllVO> findAdminEnterpriseReportAll(String serviceProviderName, ReportTheme reportTheme, String startTime, String endTime, IPage<AdminEnterpriseReportAllVO> page);

    /**
     *平台根据服务商查询税务申报或工商申报
     */
    List<AdminEnterpriseReportVO> findAdminEnterpriseReport(Long serviceProviderId, ReportTheme reportTheme, IPage<AdminEnterpriseReportVO> page);

    /**
     * 平台查询税务申报或工商申报详情
     */
    AdminEnterpriseReportVO findAdminEnterpriseReportDetail(Long enterpriseReportId);

    /**
     *根据服务商id查询税务申报或工商申报
     */
    List<AdminEnterpriseReportVO> findServiceEnterpriseReport(Long serviceProviderId, ReportTheme reportTheme, String startTime, String endTime, IPage<AdminEnterpriseReportVO> page);
}

