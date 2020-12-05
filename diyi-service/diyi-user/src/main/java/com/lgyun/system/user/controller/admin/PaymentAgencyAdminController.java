package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.NoticeState;
import com.lgyun.common.enumeration.RelBureauType;
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
@RequestMapping("/admin/payment-agency")
@Validated
@AllArgsConstructor
@Api(value = "平台端---支付机构管理模块相关接口", tags = "平台端---支付机构管理模块相关接口")
public class PaymentAgencyAdminController {

    private IAdminService adminService;
    private IRelBureauService relBureauService;
    private IServiceProviderService serviceProviderService;
    private IRelBureauNoticeService relBureauNoticeService;
    private IRelBureauServiceProviderService relBureauServiceProviderService;

    @GetMapping("/query-payment-agency-list")
    @ApiOperation(value = "查询支付机构", notes = "查询支付机构")
    public R queryPaymentAgencyList(RelBureauListDTO relBureauListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauService.queryRelBureauList(RelBureauType.PAYMENTAGENCY, relBureauListDTO,  Condition.getPage(query.setAscs("create_time")));
    }

    @PostMapping("/add-or-update-payment-agency")
    @ApiOperation(value = "添加/编辑支付机构", notes = "添加/编辑支付机构")
    public R addOrUpdatePaymentAgency(@Valid @RequestBody AddOrUpdateRelBureauDTO addOrUpdateRelBureauDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauService.addOrUpdateRelBureau(addOrUpdateRelBureauDto);
    }

    @GetMapping("/query-payment-agency-update-detail")
    @ApiOperation(value = "查询支付机构编辑详情", notes = "查询支付机构编辑详情")
    public R queryPaymentAgencyUpdateDetail(@ApiParam("支付机构") @NotNull(message = "请选择支付机构") @RequestParam(required = false) Long relBureauId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauService.queryRelBureauUpdateDetail(relBureauId);
    }

    @GetMapping("/query-payment-agency-info")
    @ApiOperation(value = "查询支付机构基础信息", notes = "查询支付机构基础信息")
    public R queryPaymentAgencyInfo(@ApiParam("支付机构") @NotNull(message = "请选择支付机构") @RequestParam(required = false) Long relBureauId, BladeUser bladeUser) {
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
    @ApiOperation(value = "支付机构匹配服务商", notes = "支付机构匹配服务商")
    public R matchServiceProvider(@ApiParam(value = "支付机构", required = true) @NotNull(message = "请选择支付机构") @RequestParam(required = false) Long relBureauId,
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
    @ApiOperation(value = "查询支付机构合作服务商", notes = "查询支付机构合作服务商")
    public R queryCooperationServiceProviderList(@ApiParam(value = "支付机构", required = true) @NotNull(message = "请选择支付机构") @RequestParam(required = false) Long relBureauId,
                                                 @ApiParam(value = "服务商名称", required = true) @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauServiceProviderService.queryCooperationServiceProviderList(relBureauId, serviceProviderName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/update-agent-main-service-provider-cooperation-status")
    @ApiOperation(value = "更改支付机构-服务商合作关系", notes = "更改支付机构-服务商合作关系")
    public R updateAgentMainServiceProviderCooperationStatus(@ApiParam(value = "支付机构", required = true) @NotNull(message = "请选择支付机构") @RequestParam(required = false) Long relBureauId,
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
    @ApiOperation(value = "查询支付机构通知", notes = "查询支付机构通知")
    public R queryIndustrialParksNotice(@ApiParam("支付机构") @NotNull(message = "请选择支付机构") @RequestParam(required = false) Long relBureauId,
                                        @ApiParam("通知状态") @NotNull(message = "请选择通知状态") @RequestParam(required = false) NoticeState noticeState, Query query, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauNoticeService.queryBureauNoticeList(relBureauId, noticeState, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/update-industrial-parks-notice-state")
    @ApiOperation(value = "更改支付机构通知状态", notes = "更改支付机构通知状态")
    public R updateIndustrialParksNoticeState(@ApiParam("相关局通知") @NotNull(message = "请选择相关局通知") @RequestParam(required = false) Long relBureauNoticeId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauNoticeService.updateRelBureauNoticeState(relBureauNoticeId);
    }

}
