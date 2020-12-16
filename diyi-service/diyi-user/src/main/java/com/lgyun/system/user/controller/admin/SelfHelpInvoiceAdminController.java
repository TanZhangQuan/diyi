package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.EnterpriseListDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
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
    private IEnterpriseServiceProviderService enterpriseServiceProviderService;

    @GetMapping("/query-cooperation-service-provider-list")
    @ApiOperation(value = "查询商户合作服务商", notes = "查询商户合作服务商")
    public R queryCooperationServiceProviderList(@ApiParam(value = "商户id", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return enterpriseServiceProviderService.queryCooperationServiceProviderList(enterpriseId, null, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询所有商户", notes = "查询所有商户")
    public R queryEnterpriseList(EnterpriseListDTO enterpriseListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseList(null, null, enterpriseListDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

}
