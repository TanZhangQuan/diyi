package com.lgyun.system.user.controller.service;

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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 服务商年度申报管理表控制器
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
@Slf4j
@RestController
@RequestMapping("service/enterprise_report")
@Validated
@AllArgsConstructor
@Api(value = "服务商年度申报管理表控制器", tags = "服务商年度申报管理表控制器")
public class EnterpriseReportSerivceController {

    private IEnterpriseReportService enterpriseReportService;

    private IAdminService adminService;

    @GetMapping("/findServiceEnterpriseReport")
    @ApiOperation(value = "根据服务商id查询税务申报或工商申报", notes = "根据服务商id查询税务申报或工商申报")
    public R findServiceEnterpriseReport(Long serviceProviderId,ReportTheme reportTheme,String startTime,String endTime,Query query, BladeUser bladeUser) {
        log.info("根据服务商id查询税务申报或工商申报");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();
            return enterpriseReportService.findServiceEnterpriseReport(serviceProviderId, reportTheme, startTime, endTime,Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据服务商id查询税务申报或工商申报异常", e);
        }
        return R.fail("根据服务商id查询税务申报或工商申报失败");
    }

    @GetMapping("/findServiceEnterpriseReportDetail")
    @ApiOperation(value = "服务商查询税务申报或工商申报详情", notes = "服务商查询税务申报或工商申报详情")
    public R findServiceEnterpriseReportDetail(Long enterpriseReportId, BladeUser bladeUser) {
        log.info("服务商查询税务申报或工商申报详情");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();
            return enterpriseReportService.findAdminEnterpriseReportDetail(enterpriseReportId);
        } catch (Exception e) {
            log.error("服务商查询税务申报或工商申报详情异常", e);
        }
        return R.fail("服务商查询税务申报或工商申报详情失败");
    }

    @PostMapping("/saveServiceEnterpriseReport")
    @ApiOperation(value = "服务商保存税务申报或工商申报", notes = "服务商保存税务申报或工商申报")
    public R saveServiceEnterpriseReport(BladeUser bladeUser,@Valid @RequestBody AdminEnterpriseReportDTO adminEnterpriseReportDTO) {
        log.info("服务商保存税务申报或工商申报");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();
            return enterpriseReportService.saveServiceEnterpriseReport(adminEnterpriseReportDTO);
        } catch (Exception e) {
            log.error("服务商保存税务申报或工商申报异常", e);
        }
        return R.fail("服务商保存税务申报或工商申报失败");
    }
}
