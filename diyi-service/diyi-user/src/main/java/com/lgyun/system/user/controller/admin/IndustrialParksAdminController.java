package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BureauServiceProviderStatus;
import com.lgyun.common.enumeration.BureauType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AddRelBureauDTO;
import com.lgyun.system.user.dto.admin.QueryRelBureauListDTO;
import com.lgyun.system.user.dto.admin.UpdateRelBureauDTO;
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
@RequestMapping("/admin/industrial-parks")
@Validated
@AllArgsConstructor
@Api(value = "平台端---在线经济产业园区管理模块相关接口", tags = "平台端---在线经济产业园区管理模块相关接口")
public class IndustrialParksAdminController {

    private IAdminService adminService;
    private IRelBureauService bureauService;
    private IRelBureauNoticeService bureauNoticeService;
    private IRelBureauServiceProviderService bureauServiceProviderService;

    @PostMapping("/query-industrial-park-list")
    @ApiOperation(value = "查询产业园区", notes = "查询产业园区")
    public R queryIndustrialParkList(@RequestBody(required = false) QueryRelBureauListDTO queryRelBureauListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.QueryRelBureau(queryRelBureauListDTO, Condition.getPage(query.setAscs("create_time")), BureauType.INDUSTRIALPARKS);
    }


    @PostMapping("/add-industrial-parks")
    @ApiOperation(value = "添加产业园区", notes = "添加产业园区")
    public R addIndustrialParks(@Valid @RequestBody AddRelBureauDTO addRelBureauDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.addRelBureau(addRelBureauDto);
    }

    @GetMapping("/query-industrial-parks-info")
    @ApiOperation(value = "查询产业园区信息", notes = "查询产业园区信息")
    public R queryIndustrialParksInfo(@ApiParam("产业园区Id") @NotNull(message = "产业园区Id不能为空！") @RequestParam(required = false) Long bureauId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.queryRelBureauInfo(bureauId);
    }

    @PostMapping("/update-industrial-parks")
    @ApiOperation(value = "编辑产业园区", notes = "编辑产业园区")
    public R updateIndustrialParks(@Valid @RequestBody UpdateRelBureauDTO updateRelBureauDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.updateBureau(updateRelBureauDTO);
    }

    @GetMapping("/query-industrial-parks-notice")
    @ApiOperation(value = "查询产业园区通知", notes = "查询产业园区通知")
    public R queryIndustrialParksNotice(@ApiParam("产业园区Id") @NotNull(message = "产业园区Id不能为空！") @RequestParam(required = false) Long bureauId, Query query, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauNoticeService.queryBureauNotice(bureauId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-industrial-parks-service-provider")
    @ApiOperation(value = "查询可以匹配的服务商", notes = "查询可以匹配的服务商")
    public R queryIndustrialParksBureauServiceProvider(@ApiParam("服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.queryRelBureauServiceProvider(serviceProviderName, BureauType.INDUSTRIALPARKS,Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/add-industrial-parks-service-provider")
    @ApiOperation(value = "添加匹配服务商", notes = "添加匹配服务商")
    public R addRelBureauServiceProvider(@ApiParam("产业园区ID不能为空") @NotNull(message = "产业园区ID不能为空") @RequestParam(required = false) Long bureauId,
                                         @ApiParam("服务商ID字符集，ID直接用逗号隔开") @NotBlank(message = "匹配服务商不能为空！") @RequestParam(required = false) String serviceProviderIds, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.addRelBureauServiceProvider(serviceProviderIds, bureauId);
    }

    @PostMapping("/update-industrial-parks-service-provider")
    @ApiOperation(value = "开启或关闭匹配服务商", notes = "开启或关闭匹配服务商")
    public R updateIndustrialParksServiceProvider(@ApiParam("匹配的ID") @NotNull(message = "匹配的ID不能为空") @RequestParam(required = false) Long bureauServiceProviderId, @NotNull(message = "状态不能为空") BureauServiceProviderStatus bureauServiceProviderStatus, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.updateBureauServiceProvider(bureauServiceProviderId, bureauServiceProviderStatus);
    }

    @PostMapping("/delete-industrial-parks-service-provider")
    @ApiOperation(value = "撤销服务商", notes = "撤销服务商")
    public R deleteIndustrialParksServiceProvider(@ApiParam("匹配的ID") @NotNull(message = "匹配的ID不能为空") @RequestParam Long bureauServiceProviderId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.deleteBureauServiceProvider(bureauServiceProviderId);
    }

}
