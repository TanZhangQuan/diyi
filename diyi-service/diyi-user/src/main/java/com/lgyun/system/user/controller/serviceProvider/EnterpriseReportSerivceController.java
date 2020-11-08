package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ReportTheme;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AdminEnterpriseReportDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/service-provider/enterprise-report")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---定期申报管理模块相关接口", tags = "服务商端---定期申报管理模块相关接口")
public class EnterpriseReportSerivceController {

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IEnterpriseReportService enterpriseReportService;
    private IIndividualBusinessService individualBusinessService;
    private IIndividualEnterpriseService individualEnterpriseService;

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "根据服务商查询税务申报或工商申报", notes = "根据服务商查询税务申报或工商申报")
    public R queryEnterpriseReportList( ReportTheme reportTheme, String startTime, String endTime, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        return enterpriseReportService.findServiceEnterpriseReport(serviceProviderWorkerEntity.getServiceProviderId(),reportTheme, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-enterprise-report-detail")
    @ApiOperation(value = "查询税务申报或工商申报详情", notes = "查询税务申报或工商申报详情")
    public R queryEnterpriseReportDetail(Long enterpriseReportId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findAdminEnterpriseReportDetail(enterpriseReportId);
    }

    @PostMapping("/create-enterprise-report")
    @ApiOperation(value = "保存税务申报或工商申报", notes = "保存税务申报或工商申报")
    public R createEnterpriseReport(@Valid @RequestBody AdminEnterpriseReportDTO adminEnterpriseReportDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.saveServiceEnterpriseReport(adminEnterpriseReportDTO);
    }



    @GetMapping("/query-individual-business-list")
    @ApiOperation(value = "查询所有个体户", notes = "查询所有个体户")
    public R queryIndividualBusinessList(Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        return individualBusinessService.queryIndividualBusinessList(null, serviceProviderWorkerEntity.getServiceProviderId(), new IndividualBusinessEnterpriseListDTO(), Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-individual-enterprise-list")
    @ApiOperation(value = "查询所有个独", notes = "查询所有个独")
    public R queryIndividualEnterpriseList(Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        return individualEnterpriseService.queryIndividualEnterpriseList(Condition.getPage(query.setDescs("create_time")), null, serviceProviderWorkerEntity.getServiceProviderId(), new IndividualBusinessEnterpriseListDTO());
    }
}
