package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.EnterpriseListDTO;
import com.lgyun.system.user.dto.ServiceProviderListDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "平台端---自助开票管理模块相关接口", tags = "平台端---自助开票管理模块相关接口")
public class SelfHelpInvoiceAdminController {

    private IAdminService adminService;
    private IEnterpriseService enterpriseService;
    private IMakerService makerService;
    private IServiceProviderService serviceProviderService;

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询所有商户", notes = "查询所有商户")
    public R queryEnterpriseList(EnterpriseListDTO enterpriseListDTO, Query query, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }

        return enterpriseService.queryEnterpriseList(null, null, enterpriseListDTO, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询所有创客", notes = "查询所有创客")
    public R queryEnterpriseList(@ApiParam(value = "创客姓名/手机号/身份证号") @RequestParam(required = false) String keyWord, Query query, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }

        return makerService.queryMakerSelectList(keyWord, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderList(ServiceProviderListDTO serviceProviderListDTO, Query query, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }

        return serviceProviderService.queryServiceProviderList(null, null, serviceProviderListDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/create-qr-code")
    @ApiOperation(value = "生成二维码", notes = "生成二维码")
    public R createQrCode(@ApiParam(value = "文件链接", required = true) @NotNull(message = "文件链接不能为空") @RequestParam(required = false) String linkUrl) throws Exception{
        return enterpriseService.createQrCode(linkUrl);
    }

}
