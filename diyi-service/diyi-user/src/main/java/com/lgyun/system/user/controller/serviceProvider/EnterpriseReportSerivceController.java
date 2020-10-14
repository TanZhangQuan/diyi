package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ReportTheme;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AdminEnterpriseReportDTO;
import com.lgyun.system.user.service.IEnterpriseReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 服务商端---定期申报管理模块相关接口
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
@Slf4j
@RestController
@RequestMapping("service/enterprise_report")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---定期申报管理模块相关接口", tags = "服务商端---定期申报管理模块相关接口")
public class EnterpriseReportSerivceController {

    private IEnterpriseReportService enterpriseReportService;

    @GetMapping("/findServiceEnterpriseReport")
    @ApiOperation(value = "根据服务商id查询税务申报或工商申报", notes = "根据服务商id查询税务申报或工商申报")
    public R findServiceEnterpriseReport(Long serviceProviderId, ReportTheme reportTheme, String startTime, String endTime, Query query) {
        return enterpriseReportService.findServiceEnterpriseReport(serviceProviderId, reportTheme, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/findServiceEnterpriseReportDetail")
    @ApiOperation(value = "服务商查询税务申报或工商申报详情", notes = "服务商查询税务申报或工商申报详情")
    public R findServiceEnterpriseReportDetail(Long enterpriseReportId) {
        return enterpriseReportService.findAdminEnterpriseReportDetail(enterpriseReportId);
    }

    @PostMapping("/saveServiceEnterpriseReport")
    @ApiOperation(value = "服务商保存税务申报或工商申报", notes = "服务商保存税务申报或工商申报")
    public R saveServiceEnterpriseReport(@Valid @RequestBody AdminEnterpriseReportDTO adminEnterpriseReportDTO) {
        return enterpriseReportService.saveServiceEnterpriseReport(adminEnterpriseReportDTO);
    }
}
