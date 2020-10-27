package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BureauServiceProviderStatus;
import com.lgyun.common.enumeration.BureauType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddRelBureauDTO;
import com.lgyun.system.user.dto.QueryRelBureauListDTO;
import com.lgyun.system.user.dto.UpdateRelBureauDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IRelBureauNoticeService;
import com.lgyun.system.user.service.IRelBureauService;
import com.lgyun.system.user.service.IRelBureauServiceProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/paying-agency")
@Validated
@AllArgsConstructor
@Api(value = "平台端---支付机构管理模块相关接口", tags = "平台端---支付机构管理模块相关接口")
public class PayingAgencyAdminController {

    private IAdminService adminService;
    private IRelBureauService bureauService;
    private IRelBureauNoticeService bureauNoticeService;
    private IRelBureauServiceProviderService bureauServiceProviderService;

    @PostMapping("/query-paying-agency")
    @ApiOperation(value = "查询支付机构", notes = "查询支付机构")
    public R queryPayingAgency(@RequestBody(required = false) QueryRelBureauListDTO queryRelBureauListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.QueryRelBureau(queryRelBureauListDTO, Condition.getPage(query.setAscs("create_time")), BureauType.PAYINGAGENCY);
    }


    @PostMapping("/add-paying-agency")
    @ApiOperation(value = "添加支付机构", notes = "添加支付机构")
    public R addPayingAgency(@Valid @RequestBody AddRelBureauDTO addRelBureauDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.addRelBureau(addRelBureauDto);
    }

    @GetMapping("/query-paying-agency-info")
    @ApiOperation(value = "查询支付机构信息", notes = "查询支付机构信息")
    public R queryPayingAgencyInfo(@ApiParam("支付机构Id") @NotNull(message = "支付机构Id不能为空！") @RequestParam(required = false) Long bureauId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.queryRelBureauInfo(bureauId);
    }

    @PostMapping("/update-paying-agency")
    @ApiOperation(value = "编辑支付机构", notes = "编辑支付机构")
    public R updatePayingAgencyBureau(@Valid @RequestBody UpdateRelBureauDTO updateRelBureauDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.updateBureau(updateRelBureauDTO);
    }

    @GetMapping("/query-paying-agency-notice")
    @ApiOperation(value = "查询支付机构通知", notes = "查询支付机构通知")
    public R queryPayingAgencyNotice(@ApiParam("支付机构Id") @NotNull(message = "支付机构Id不能为空！") @RequestParam(required = false) Long bureauId, Query query, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauNoticeService.queryBureauNotice(bureauId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-paying-agency-service-provider")
    @ApiOperation(value = "查询可以匹配的服务商", notes = "查询可以匹配的服务商")
    public R queryPayingAgencyServiceProvider(@ApiParam("服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.queryRelBureauServiceProvider(serviceProviderName, BureauType.PAYINGAGENCY, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/add-paying-agency-service-provider")
    @ApiOperation(value = "添加匹配服务商", notes = "添加匹配服务商")
    public R addRelBureauServiceProvider(@ApiParam("支付机构ID不能为空") @NotNull(message = "支付机构ID不能为空") @RequestParam(required = false) Long bureauId,
                                         @ApiParam("服务商ID字符集，ID直接用逗号隔开") @NotBlank(message = "匹配服务商不能为空！") @RequestParam(required = false) String serviceProviderIds, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.addRelBureauServiceProvider(serviceProviderIds, bureauId);
    }

    @PostMapping("/update-paying-agency-service-provider")
    @ApiOperation(value = "开启或关闭匹配服务商", notes = "开启或关闭匹配服务商")
    public R updateBureauServiceProvider(@ApiParam("匹配的ID") @NotNull(message = "匹配的ID不能为空") @RequestParam(required = false) Long bureauServiceProviderId, @NotNull(message = "状态不能为空") BureauServiceProviderStatus bureauServiceProviderStatus, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.updateBureauServiceProvider(bureauServiceProviderId, bureauServiceProviderStatus);
    }

    @PostMapping("/delete-paying-agency-service-provider")
    @ApiOperation(value = "撤销服务商", notes = "撤销服务商")
    public R deleteBureauServiceProvider(@ApiParam("匹配的ID") @NotNull(message = "匹配的ID不能为空") @RequestParam Long bureauServiceProviderId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.deleteBureauServiceProvider(bureauServiceProviderId);
    }

}
