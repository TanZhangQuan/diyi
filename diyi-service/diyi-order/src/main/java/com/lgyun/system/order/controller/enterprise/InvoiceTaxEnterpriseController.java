package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.ContractApplyInvoiceDTO;
import com.lgyun.system.order.service.IEnterpriseServiceProviderInvoiceCatalogsService;
import com.lgyun.system.order.service.IInvoiceApplicationService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/enterprise/invoice-tax")
@Validated
@AllArgsConstructor
@Api(value = "商户端---商户发票和完税证明接口", tags = "商户端---商户发票和完税证明接口")
public class InvoiceTaxEnterpriseController {

    private IPayEnterpriseService payEnterpriseService;
    private IEnterpriseServiceProviderInvoiceCatalogsService enterpriseProviderInvoiceCatalogsService;
    private IInvoiceApplicationService invoiceApplicationService;
    private IUserClient iUserClient;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-total-invoice-list")
    @ApiOperation(value = "根据商户查询总包发票", notes = "根据商户查询总包发票")
    public R queryTotalInvoiceList(@ApiParam(value = "发票号码") @RequestParam(required = false) String invoiceSerialNo, @ApiParam(value = "服务商名字") @RequestParam(required = false) String serviceProviderName,
                                          @ApiParam(value = "开始时间") @RequestParam(required = false) String startTime, @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
                                          Query query, BladeUser bladeUser) {

        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterpriseLumpSumInvoice(invoiceSerialNo, serviceProviderName, startTime, endTime, enterpriseWorkerEntity.getEnterpriseId(), Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/cancel-apply")
    @ApiOperation(value = "取消申请", notes = "取消申请")
    public R cancelApply(Long applicationId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.cancelApply(applicationId);
    }

    @GetMapping("/query-total-invoice-detail")
    @ApiOperation(value = "查看总包发票详情", notes = "查看总包发票详情")
    public R queryTotalInvoiceDetail(Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.findPayEnterpriseDetails(payEnterpriseId);
    }

    @GetMapping("/query-pay-enterprise-list")
    @ApiOperation(value = "根据商户查询支付清单", notes = "根据商户查询支付清单")
    public R queryPayEnterpriseList(@RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterprisePaymentList(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-invoice-catalogs-list")
    @ApiOperation(value = "查询开票类目", notes = "查询开票类目")
    public R queryInvoiceCatalogsList(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseProviderInvoiceCatalogsService.queryInvoiceCatalogsList(serviceProviderId, enterpriseWorkerEntity.getEnterpriseId(), Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/apply-total-invoice")
    @ApiOperation(value = "申请总包发票", notes = "申请总包发票")
    public R applyTotalInvoice(@Valid @RequestBody ContractApplyInvoiceDTO contractApplyInvoiceDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return invoiceApplicationService.contractApplyInvoice(contractApplyInvoiceDto, enterpriseWorkerEntity.getEnterpriseId(), payEnterpriseService);
    }

    @GetMapping("/query-all-open-sub-list")
    @ApiOperation(value = "根据商户查询汇总代开分包列表", notes = "根据商户查询汇总代开分包列表")
    public R queryAllOpenSubList(@RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterpriseSubcontractSummary(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-all-open-sub-detail")
    @ApiOperation(value = "根据商户查询汇总代开分包详情", notes = "根据商户查询汇总代开分包详情")
    public R queryAllOpenSubDetail(Long makerTotalInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.findDetailSummary(makerTotalInvoiceId);
    }

    @GetMapping("/query-single-open-sub-list")
    @ApiOperation(value = "根据商户查询门征单开分包列表", notes = "根据商户查询门征单开分包列表")
    public R querySingleOpenSubList(@RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterpriseSubcontractPortal(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-single-open-sub-detail")
    @ApiOperation(value = "根据商户查询门征单开分包详情", notes = "根据商户查询门征单开分包详情")
    public R querySingleOpenSubDetail(Long makerInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.findDetailSubcontractPortal(makerInvoiceId);
    }

    @GetMapping("/query-crowd-list")
    @ApiOperation(value = "根据商户查询众包/众采", notes = "根据商户查询众包/众采")
    public R queryCrowdList(@RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.findEnterpriseCrowdSourcing(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-crowd-detail")
    @ApiOperation(value = "查询众包/众采详情", notes = "查询众包/众采详情")
    public R queryCrowdDetail(Long selfHelpInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.findDetailCrowdSourcing(selfHelpInvoiceId);
    }
}
