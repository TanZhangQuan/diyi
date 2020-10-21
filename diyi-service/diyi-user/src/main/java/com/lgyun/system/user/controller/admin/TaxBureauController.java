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
import javax.validation.constraints.Pattern;
import javax.ws.rs.POST;

@RestController
@RequestMapping("/admin/tax-bureau")
@Validated
@AllArgsConstructor
@Api(value = "平台端---税局管理", tags = "平台端---税局管理")
public class TaxBureauController {
    private IRelBureauService bureauService;
    private IRelBureauNoticeService bureauNoticeService;
    private IAdminService adminService;
    private IRelBureauServiceProviderService bureauServiceProviderService;

    @PostMapping("/query-tax-bureau")
    @ApiOperation(value = "查询税局", notes = "查询税局")
    public R getTaxBureau(@RequestBody QueryRelBureauListDTO queryRelBureauListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.QueryRelBureau(queryRelBureauListDTO, Condition.getPage(query.setAscs("create_time")), BureauType.TAXBUREAU);
    }


    @PostMapping("/add-tax-bureau")
    @ApiOperation(value = "添加税局", notes = "添加税局")
    public R addTaxBureau(@Valid @RequestBody AddRelBureauDTO addRelBureauDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.addRelBureau(addRelBureauDto);
    }

    @GetMapping("/query-tax-bureau-info")
    @ApiOperation(value = "查询税局信息", notes = "查询税局信息")
    public R queryTaxBureauInfo(@ApiParam("税局Id") @NotNull(message = "税局Id不能为空！") @RequestParam(required = false) Long bureauId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.queryRelBureauInfo(bureauId);
    }

    @PostMapping("/update-tax-bureau")
    @ApiOperation(value = "编辑税局", notes = "编辑税局")
    public R updateTaxBureau(@Valid @RequestBody UpdateRelBureauDTO updateRelBureauDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauService.updateTaxBureau(updateRelBureauDTO);
    }

    @GetMapping("/query-tax-bureau-notice")
    @ApiOperation(value = "查询税局信息", notes = "查询税局信息")
    public R queryTaxBureauNotice(@ApiParam("税局Id") @NotNull(message = "税局Id不能为空！") @RequestParam(required = false) Long bureauId, Query query, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauNoticeService.queryTaxBureauNotice(bureauId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-tax-bureau-service-provider")
    @ApiOperation(value = "查询可以匹配的服务商", notes = "查询可以匹配的服务商")
    public R queryTaxBureauServiceProvider(@ApiParam("服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.queryRelBureauServiceProvider(serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/add-tax-bureau-service-provider")
    @ApiOperation(value = "开启或关闭匹配服务商", notes = "开启或关闭匹配服务商")
    public R addRelBureauServiceProvider(@ApiParam("税局ID不能为空") @NotNull(message = "税局ID不能为空") @RequestParam(required = false) Long bureauId,
                                         @ApiParam("服务商ID字符集，ID直接用逗号隔开") @NotBlank(message = "匹配服务商不能为空！") @RequestParam(required = false) String serviceProviderIds, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.addRelBureauServiceProvider(serviceProviderIds, bureauId);
    }

    @PostMapping("/update-tax-bureau-service-provider")
    @ApiOperation(value = "开启或关闭匹配服务商", notes = "开启或关闭匹配服务商")
    public R updateTaxBureauServiceProvider(@ApiParam("匹配的ID") @NotNull(message = "匹配的ID不能为空") @RequestParam(required = false) Long bureauServiceProviderId, BureauServiceProviderStatus bureauServiceProviderStatus, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.updateTaxBureauServiceProvider(bureauServiceProviderId, bureauServiceProviderStatus);
    }

    @PostMapping("/delete-tax-bureau-service-provider")
    @ApiOperation(value = "撤销服务商", notes = "撤销服务商")
    public R deleteTaxBureauServiceProvider(@ApiParam("匹配的ID") @NotNull(message = "匹配的ID不能为空") @RequestParam Long bureauServiceProviderId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return bureauServiceProviderService.deleteTaxBureauServiceProvider(bureauServiceProviderId);
    }
}
