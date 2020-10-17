package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ReportTheme;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AdminEnterpriseReportDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IEnterpriseReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 平台端---定期申报管理模块相关接口
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
@Slf4j
@RestController
@RequestMapping("/admin/enterprise_report")
@Validated
@AllArgsConstructor
@Api(value = "平台端---定期申报管理模块相关接口", tags = "平台端---定期申报管理模块相关接口")
public class EnterpriseReportAdminController {

    private IAdminService adminService;
    private IEnterpriseReportService enterpriseReportService;

    @GetMapping("/findAdminEnterpriseReportAll")
    @ApiOperation(value = "平台查询所有服务商税务申报或工商申报", notes = "平台查询所有服务商税务申报或工商申报")
    public R findAdminEnterpriseReportAll(@ApiParam(value = "服务商名字") @RequestParam(required = false) String serviceProviderName,
                                          @ApiParam(value = "申报主题") @RequestParam(required = false) ReportTheme reportTheme,
                                          String startTime, String endTime, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findAdminEnterpriseReportAll(serviceProviderName, reportTheme, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/findAdminEnterpriseReport")
    @ApiOperation(value = "平台根据服务商查询税务申报或工商申报", notes = "平台根据服务商查询税务申报或工商申报")
    public R findAdminEnterpriseReport(Long serviceProviderId, ReportTheme reportTheme, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findAdminEnterpriseReport(serviceProviderId, reportTheme, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/findAdminEnterpriseReportDetail")
    @ApiOperation(value = "平台查询税务申报或工商申报详情", notes = "平台查询税务申报或工商申报详情")
    public R findAdminEnterpriseReportDetail(Long enterpriseReportId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findAdminEnterpriseReportDetail(enterpriseReportId);
    }

    @PostMapping("/saveAdminEnterpriseReport")
    @ApiOperation(value = "平台保存税务申报或工商申报", notes = "平台保存税务申报或工商申报")
    public R saveAdminEnterpriseReport(@Valid @RequestBody AdminEnterpriseReportDTO adminEnterpriseReportDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.saveAdminEnterpriseReport(adminEnterpriseReportDTO);
    }


    @PostMapping("/toExamineAdminEnterpriseReport")
    @ApiOperation(value = "平台审核税务申报或工商申报", notes = "平台审核税务申报或工商申报")
    public R toExamineAdminEnterpriseReport(Long enterpriseReportId, @ApiParam("1审核通过，2审核不通过") Integer toExamine, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.toExamineAdminEnterpriseReport(enterpriseReportId, toExamine);
    }
}
