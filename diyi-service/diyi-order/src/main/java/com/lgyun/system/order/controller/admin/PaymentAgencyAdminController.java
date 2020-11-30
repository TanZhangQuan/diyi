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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/payment-agency")
@Validated
@AllArgsConstructor
@Api(value = "平台端---支付机构管理模块相关接口", tags = "平台端---支付机构管理模块相关接口")
public class PaymentAgencyAdminController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;

    @PostMapping("/query-payment-agency-service-provider-transaction")
    @ApiOperation(value = "查询支付机构-服务商交易情况数据", notes = "查询支付机构-服务商交易情况数据")
    public R queryPaymentAgencyServiceProviderTransaction(@ApiParam("支付机构") @NotNull(message = "请选择支付机构") @RequestParam(required = false) Long relBureauId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.queryRelBureauServiceProviderTransaction(relBureauId);
    }

}