package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerInvoiceType;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDTO;
import com.lgyun.system.order.dto.PayEnterpriseDTO;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IPayMakerReceiptService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/service-provider/payment")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---支付管理模块相关接口", tags = "服务商端---支付管理模块相关接口")
public class PaymentServiceProviderController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private IWorksheetService worksheetService;
    private IAcceptPaysheetService acceptPaysheetService;
    private IPayMakerReceiptService payMakerReceiptService;

    @GetMapping("/query-pay-enterprise-list")
    @ApiOperation(value = "查询当前服务商所有总包支付清单", notes = "查询当前服务商所有总包支付清单")
    public R queryPayEnterpriseList(PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterpriseList(null, serviceProviderWorkerEntity.getServiceProviderId(), payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-pay-enterprise-detail")
    @ApiOperation(value = "查询总包支付清单详情", notes = "查询总包支付清单详情")
    public R queryPayEnterpriseDetail(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseDetail(payEnterpriseId);
    }

    @GetMapping("/query-worksheet-detail")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R queryTotalSubAcceptPaysheetList(@ApiParam(value = "工单", required = true) @NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getByWorksheetId(worksheetId);
    }

    @GetMapping("/query-pay-maker-list")
    @ApiOperation(value = "根据支付清单查询创客支付明细", notes = "根据支付清单查询创客支付明细")
    public R queryPayMakerList(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择支付清单") @RequestParam(required = false) Long payEnterpriseId, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getPayMakerListByPayEnterprise(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/audit-pay-enterprise")
    @ApiOperation(value = "支付清单审核", notes = "支付清单审核")
    public R auditPayEnterprise(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择支付清单") @RequestParam(required = false) Long payEnterpriseId,
                                @ApiParam(value = "支付清单审核状态", required = true) @NotNull(message = "请选择支付清单审核状态") @RequestParam(required = false) PayEnterpriseAuditState auditState,
                                MakerInvoiceType makerInvoiceType, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.audit(payEnterpriseId, serviceProviderWorkerEntity.getServiceProviderId(), auditState, makerInvoiceType);
    }

    @PostMapping("/upload-accept-paysheet")
    @ApiOperation(value = "上传交付支付验收单", notes = "上传交付支付验收单")
    public R uploadAcceptPaysheet(@Valid @RequestBody AcceptPaysheetSaveDTO acceptPaysheetSaveDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return acceptPaysheetService.uploadAcceptPaysheet(null, serviceProviderWorkerEntity.getServiceProviderId(), acceptPaysheetSaveDto, "服务商上传", serviceProviderWorkerEntity.getWorkerName());
    }

    @PostMapping("/upload-pay-maker-receipt")
    @ApiOperation(value = "上传支付回单", notes = "上传支付回单")
    public R uploadPayMakerReceipt(@ApiParam(value = "支付清单明细", required = true) @NotNull(message = "请选择支付清单明细") @RequestParam(required = false) Long payMakerId,
                                   @ApiParam(value = "支付回单", required = true) @NotBlank(message = "请上传支付回单") @RequestParam(required = false) String makerPayReceiptUrls, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payMakerReceiptService.uploadPayMakerReceipt(serviceProviderWorkerEntity.getServiceProviderId(), payMakerId, makerPayReceiptUrls);
    }

}
