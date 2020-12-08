package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.NoticeState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddOrUpdateRelBureauDTO;
import com.lgyun.system.user.dto.RelBureauListDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/rel-bureau")
@Validated
@AllArgsConstructor
@Api(value = "平台端---相关局管理模块相关接口", tags = "平台端---相关局管理模块相关接口")
public class RelBureauAdminController {

    private IAdminService adminService;
    private IRelBureauService relBureauService;
    private IServiceProviderService serviceProviderService;
    private IRelBureauNoticeService relBureauNoticeService;
    private IRelBureauServiceProviderService relBureauServiceProviderService;

    @GetMapping("/query-rel-bureau-list")
    @ApiOperation(value = "查询相关局", notes = "查询相关局")
    public R queryRelBureauList(RelBureauListDTO relBureauListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauService.queryRelBureauList(relBureauListDTO, Condition.getPage(query.setAscs("create_time")));
    }

    @PostMapping("/add-or-update-rel-bureau")
    @ApiOperation(value = "添加/编辑相关局", notes = "添加/编辑相关局")
    public R addOrUpdateRelBureau(@Valid @RequestBody AddOrUpdateRelBureauDTO addOrUpdateRelBureauDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauService.addOrUpdateRelBureau(addOrUpdateRelBureauDto);
    }

    @GetMapping("/query-rel-bureau-update-detail")
    @ApiOperation(value = "查询相关局编辑详情", notes = "查询相关局编辑详情")
    public R queryRelBureauUpdateDetail(@ApiParam("相关局") @NotNull(message = "请选择相关局") @RequestParam(required = false) Long relBureauId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauService.queryRelBureauUpdateDetail(relBureauId);
    }

    @GetMapping("/query-rel-bureau-info")
    @ApiOperation(value = "查询相关局基础信息", notes = "查询相关局基础信息")
    public R queryRelBureauInfo(@ApiParam("相关局") @NotNull(message = "请选择相关局") @RequestParam(required = false) Long relBureauId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauService.queryRelBureauInfo(relBureauId);
    }

    @GetMapping("/query-service-provider-id-and-name-list")
    @ApiOperation(value = "查询所有服务商编号姓名", notes = "查询所有服务商编号姓名")
    public R queryServiceProviderIdAndNameList(@ApiParam(value = "服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderService.queryServiceProviderIdAndNameList(null, serviceProviderName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/match-service-provider")
    @ApiOperation(value = "相关局匹配服务商", notes = "相关局匹配服务商")
    public R matchServiceProvider(@ApiParam(value = "相关局", required = true) @NotNull(message = "请选择相关局") @RequestParam(required = false) Long relBureauId,
                                  @ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                  @ApiParam(value = "分配说明") @RequestParam(required = false) String matchDesc, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauServiceProviderService.relevanceRelBureauServiceProvider(relBureauId, serviceProviderId, matchDesc);
    }

    @GetMapping("/query-cooperation-service-provider-list")
    @ApiOperation(value = "查询相关局合作服务商", notes = "查询相关局合作服务商")
    public R queryCooperationServiceProviderList(@ApiParam(value = "相关局", required = true) @NotNull(message = "请选择相关局") @RequestParam(required = false) Long relBureauId,
                                                 @ApiParam(value = "服务商名称", required = true) @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauServiceProviderService.queryCooperationServiceProviderList(relBureauId, serviceProviderName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/update-agent-main-service-provider-cooperation-status")
    @ApiOperation(value = "更改相关局-服务商合作关系", notes = "更改相关局-服务商合作关系")
    public R updateAgentMainServiceProviderCooperationStatus(@ApiParam(value = "相关局", required = true) @NotNull(message = "请选择相关局") @RequestParam(required = false) Long relBureauId,
                                                             @ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                                             @ApiParam(value = "合作状态", required = true) @NotNull(message = "请选择合作状态") @RequestParam(required = false) CooperateStatus cooperateStatus, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauServiceProviderService.updateCooperationStatus(relBureauId, serviceProviderId, cooperateStatus);
    }

    @GetMapping("/query-industrial-parks-notice")
    @ApiOperation(value = "查询相关局通知", notes = "查询相关局通知")
    public R queryIndustrialParksNotice(@ApiParam("相关局") @NotNull(message = "请选择相关局") @RequestParam(required = false) Long relBureauId,
                                        @ApiParam("通知状态") @NotNull(message = "请选择通知状态") @RequestParam(required = false) NoticeState noticeState, Query query, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauNoticeService.queryBureauNoticeList(relBureauId, noticeState, Condition.getPage(query.setDescs("create_time")));
    }

}
