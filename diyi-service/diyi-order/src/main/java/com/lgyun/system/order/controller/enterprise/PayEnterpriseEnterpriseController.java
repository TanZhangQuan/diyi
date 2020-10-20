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

/**
 * 商户端---发票/税票管理模块相关接口
 *
 * @author tzq
 * @since 2020-07-17 20:01:13
 */
@RestController
@RequestMapping("/enterprise/pay_enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户端---商户支付清单相关接口(管理端)", tags = "商户端---商户支付清单相关接口(管理端)")
public class PayEnterpriseEnterpriseController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient iUserClient;

    @GetMapping("/get_pay_maker_list_by_pay_enterprise_id")
    @ApiOperation(value = "根据支付清单ID查询创客支付明细", notes = "根据支付清单ID查询创客支付明细")
    public R getPayMakerListByPayEnterpriseId(@ApiParam(value = "支付清单编号", required = true) @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query) {
        return payEnterpriseService.getPayMakerListByPayEnterprise(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/get_pay_enterprises_by_enterprise_service_provider")
    @ApiOperation(value = "根据当前服务商，商户查询总包支付清单", notes = "根据当前服务商，商户查询总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseMakerId", value = "总包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "enterpriseName", value = "商户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByEnterprisesServiceProvider(@ApiParam(value = "商户ID", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterprisesByEnterprisesServiceProvider(enterpriseId, serviceProviderWorkerEntity.getServiceProviderId(), payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

}
