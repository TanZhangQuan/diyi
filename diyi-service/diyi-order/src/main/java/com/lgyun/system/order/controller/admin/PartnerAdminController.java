package com.lgyun.system.order.controller.admin;


import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.feign.IUserClient;
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
@RequestMapping("/admin/partner")
@Validated
@AllArgsConstructor
@Api(value = "平台端---合伙人管理模块相关接口", tags = "平台端---合伙人管理模块相关接口")
public class PartnerAdminController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/query-partner-transaction")
    @ApiOperation(value = "查询合伙人交易数据", notes = "查询合伙人交易数据")
    public R queryPartnerTransaction(@ApiParam(value = "合伙人", required = true) @NotNull(message = "请选择合伙人") @RequestParam(required = false) Long partnerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.queryPartnerTransaction(partnerId);
    }

}
