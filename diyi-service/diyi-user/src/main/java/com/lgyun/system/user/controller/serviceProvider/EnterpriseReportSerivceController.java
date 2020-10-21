package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ReportTheme;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AdminEnterpriseReportDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 服务商端---定期申报管理模块相关接口
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
@RestController
@RequestMapping("/service-provider/enterprise-report")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---定期申报管理模块相关接口", tags = "服务商端---定期申报管理模块相关接口")
public class EnterpriseReportSerivceController {

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IEnterpriseReportService enterpriseReportService;

    @GetMapping("/findServiceEnterpriseReport")
    @ApiOperation(value = "根据服务商id查询税务申报或工商申报", notes = "根据服务商id查询税务申报或工商申报")
    public R findServiceEnterpriseReport(Long serviceProviderId, ReportTheme reportTheme, String startTime, String endTime, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findServiceEnterpriseReport(serviceProviderId, reportTheme, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/findServiceEnterpriseReportDetail")
    @ApiOperation(value = "服务商查询税务申报或工商申报详情", notes = "服务商查询税务申报或工商申报详情")
    public R findServiceEnterpriseReportDetail(Long enterpriseReportId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findAdminEnterpriseReportDetail(enterpriseReportId);
    }

    @PostMapping("/saveServiceEnterpriseReport")
    @ApiOperation(value = "服务商保存税务申报或工商申报", notes = "服务商保存税务申报或工商申报")
    public R saveServiceEnterpriseReport(@Valid @RequestBody AdminEnterpriseReportDTO adminEnterpriseReportDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.saveServiceEnterpriseReport(adminEnterpriseReportDTO);
    }
}
