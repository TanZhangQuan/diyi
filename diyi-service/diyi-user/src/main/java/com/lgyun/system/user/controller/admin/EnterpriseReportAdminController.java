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
 * 平台年度申报管理表控制器
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
@Slf4j
@RestController
@RequestMapping("/admin/enterprise_report")
@Validated
@AllArgsConstructor
@Api(value = "平台年度申报管理表控制器", tags = "平台年度申报管理表控制器")
public class EnterpriseReportAdminController {

    private IEnterpriseReportService enterpriseReportService;

    private IAdminService adminService;

    @GetMapping("/findAdminEnterpriseReportAll")
    @ApiOperation(value = "平台查询所有服务商税务申报或工商申报", notes = "平台查询所有服务商税务申报或工商申报")
    public R findAdminEnterpriseReportAll(@ApiParam(value = "服务商名字") @RequestParam(required = false) String serviceProviderName,
                                          @ApiParam(value = "申报主题") @RequestParam(required = false) ReportTheme reportTheme,
                                          String startTime, String endTime,
                                          Query query, BladeUser bladeUser) {
        log.info("平台查询所有服务商税务申报或工商申报");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();
            return enterpriseReportService.findAdminEnterpriseReportAll(serviceProviderName,reportTheme, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("平台查询所有服务商税务申报或工商申报异常", e);
        }
        return R.fail("平台查询所有服务商税务申报或工商申报失败");
    }

    @GetMapping("/findAdminEnterpriseReport")
    @ApiOperation(value = "平台根据服务商查询税务申报或工商申报", notes = "平台根据服务商查询税务申报或工商申报")
    public R findAdminEnterpriseReport(Long serviceProviderId,ReportTheme reportTheme,Query query, BladeUser bladeUser) {
        log.info("平台根据服务商查询税务申报或工商申报");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();
            return enterpriseReportService.findAdminEnterpriseReport(serviceProviderId, reportTheme,Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("平台根据服务商查询税务申报或工商申报异常", e);
        }
        return R.fail("平台根据服务商查询税务申报或工商申报失败");
    }

    @GetMapping("/findAdminEnterpriseReportDetail")
    @ApiOperation(value = "平台查询税务申报或工商申报详情", notes = "平台查询税务申报或工商申报详情")
    public R findAdminEnterpriseReportDetail(Long enterpriseReportId, BladeUser bladeUser) {
        log.info("平台查询税务申报或工商申报详情");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();
            return enterpriseReportService.findAdminEnterpriseReportDetail(enterpriseReportId);
        } catch (Exception e) {
            log.error("平台查询税务申报或工商申报详情异常", e);
        }
        return R.fail("平台查询税务申报或工商申报详情失败");
    }

    @PostMapping("/saveAdminEnterpriseReport")
    @ApiOperation(value = "平台保存税务申报或工商申报", notes = "平台保存税务申报或工商申报")
    public R saveAdminEnterpriseReport(BladeUser bladeUser,@Valid @RequestBody AdminEnterpriseReportDTO adminEnterpriseReportDTO) {
        log.info("平台保存税务申报或工商申报");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();
            return enterpriseReportService.saveAdminEnterpriseReport(adminEnterpriseReportDTO);
        } catch (Exception e) {
            log.error("平台保存税务申报或工商申报异常", e);
        }
        return R.fail("平台保存税务申报或工商申报失败");
    }


    @PostMapping("/toExamineAdminEnterpriseReport")
    @ApiOperation(value = "平台审核税务申报或工商申报", notes = "平台审核税务申报或工商申报")
    public R toExamineAdminEnterpriseReport(BladeUser bladeUser,Long enterpriseReportId,@ApiParam("1审核通过，2审核不通过") Integer toExamine) {
        log.info("平台审核税务申报或工商申报");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();
            return enterpriseReportService.toExamineAdminEnterpriseReport(enterpriseReportId,toExamine);
        } catch (Exception e) {
            log.error("平台审核税务申报或工商申报异常", e);
        }
        return R.fail("平台审核税务申报或工商申报失败");
    }
}
