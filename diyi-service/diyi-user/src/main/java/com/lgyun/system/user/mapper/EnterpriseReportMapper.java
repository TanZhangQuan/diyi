package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.enumeration.ReportTheme;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.admin.QueryAdminEnterpriseReportAllVO;
import com.lgyun.system.user.vo.admin.QueryAdminEnterpriseReportVO;
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
     * 根据申报主体类别，申报主体ID查询申报结果文件资料
     *
     * @param mainBodyType
     * @param mainBodyId
     * @return
     */
    String findReportResultFiles(BodyType mainBodyType, Long mainBodyId);


    /**
     * 平台查询所有服务商税务申报或工商申报
     */
    List<QueryAdminEnterpriseReportAllVO> findAdminEnterpriseReportAll(String serviceProviderName, ReportTheme reportTheme,String startTime, String endTime, IPage<QueryAdminEnterpriseReportAllVO> page);

    /**
     *平台根据服务商查询税务申报或工商申报
     */
    List<QueryAdminEnterpriseReportVO> findAdminEnterpriseReport(Long serviceProviderId,ReportTheme reportTheme,IPage<QueryAdminEnterpriseReportVO> page);

    /**
     * 平台查询税务申报或工商申报详情
     */
    QueryAdminEnterpriseReportVO findAdminEnterpriseReportDetail(Long enterpriseReportId);

    /**
     *根据服务商id查询税务申报或工商申报
     */
    List<QueryAdminEnterpriseReportAllVO> findServiceEnterpriseReport(Long serviceProviderId,ReportTheme reportTheme, String startTime, String endTime, IPage<QueryAdminEnterpriseReportAllVO> page);
}

