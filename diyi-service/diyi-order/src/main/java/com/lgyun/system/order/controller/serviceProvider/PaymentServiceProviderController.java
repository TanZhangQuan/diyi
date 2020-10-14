package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerInvoiceType;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 服务商端---支付管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
//@RequestMapping("/service-provider/payment")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---支付管理模块相关接口", tags = "服务商端---支付管理模块相关接口")
public class PaymentServiceProviderController {

    private IUserClient iUserClient;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/web/pay_enterprise/get_pay_enterprises_by_service_provider")
    @ApiOperation(value = "查询当前服务商所有总包支付清单", notes = "查询当前服务商所有总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseId", value = "总包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "enterpriseName", value = "商户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "payEnterpriseAuditState", value = "审核状态", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByServiceProvider(PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterpriseList(null, serviceProviderWorkerEntity.getServiceProviderId(), payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/web/pay_enterprise/audit")
    @ApiOperation(value = "支付清单审核", notes = "支付清单审核")
    public R audit(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId,
                   @ApiParam(value = "支付清单审核状态") @NotNull(message = "请选择支付清单审核状态") @RequestParam(required = false) PayEnterpriseAuditState auditState,
                   MakerInvoiceType makerInvoiceType, BladeUser bladeUser) {

        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.audit(payEnterpriseId, serviceProviderWorkerEntity.getServiceProviderId(), auditState, makerInvoiceType);
    }

}