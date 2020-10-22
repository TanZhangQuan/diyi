package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.PayEnterpriseDTO;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/enterprise/pay-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户端---商户支付清单相关接口", tags = "商户端---商户支付清单相关接口(管理端)")
public class PayEnterpriseEnterpriseController {

    private IUserClient iUserClient;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/query-pay-maker-list")
    @ApiOperation(value = "根据支付清单查询创客支付明细", notes = "根据支付清单查询创客支付明细")
    public R queryPayMakerList(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择支付清单") @RequestParam(required = false) Long payEnterpriseId, Query query) {
        return payEnterpriseService.getPayMakerListByPayEnterprise(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-pay-enterprise-list")
    @ApiOperation(value = "根据当前服务商，商户查询总包支付清单", notes = "根据当前服务商，商户查询总包支付清单")
    public R queryPayEnterpriseList(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterprisesByEnterprisesServiceProvider(enterpriseId, serviceProviderWorkerEntity.getServiceProviderId(), payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

}
