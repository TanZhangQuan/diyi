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

/**
 * 商户端---发票/税票管理模块相关接口
 *
 * @author tzq
 * @date 2020/7/22.
 * @time 16:24.
 */
@RestController
@RequestMapping("/enterprise/invoice")
@Validated
@AllArgsConstructor
@Api(value = "商户端---商户发票和完税证明接口", tags = "商户端---商户发票和完税证明接口")
public class InvoiceEnterpriseController {

    private IPayEnterpriseService payEnterpriseService;
    private IEnterpriseServiceProviderInvoiceCatalogsService enterpriseProviderInvoiceCatalogsService;
    private IInvoiceApplicationService invoiceApplicationService;
    private IUserClient iUserClient;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/findEnterpriseLumpSumInvoice")
    @ApiOperation(value = "根据商户查询总包发票", notes = "根据商户查询总包发票")
    public R findEnterpriseLumpSumInvoice(@ApiParam(value = "发票号码") @RequestParam(required = false) String invoiceSerialNo,
                                          @ApiParam(value = "服务商名字") @RequestParam(required = false) String serviceProviderName,
                                          @ApiParam(value = "开始时间") @RequestParam(required = false) String startTime,
                                          @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
                                          Query query, BladeUser bladeUser) {

        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterpriseLumpSumInvoice(invoiceSerialNo, serviceProviderName, startTime, endTime, enterpriseWorkerEntity.getEnterpriseId(), Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/withdraw")
    @ApiOperation(value = "取消申请", notes = "取消申请")
    public R withdraw(Long applicationId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.withdraw(applicationId);
    }

    @GetMapping("/findPayEnterpriseDetails")
    @ApiOperation(value = "查看总包发票详情", notes = "查看总包发票详情")
    public R findPayEnterpriseDetails(Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.findPayEnterpriseDetails(payEnterpriseId);
    }

    @GetMapping("/findEnterprisePaymentList")
    @ApiOperation(value = "根据商户查询支付清单", notes = "根据商户查询支付清单")
    public R findEnterprisePaymentList(@RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
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

    @PostMapping("/contractApplyInvoice")
    @ApiOperation(value = "申请总包发票", notes = "申请总包发票")
    public R contractApplyInvoice(@Valid @RequestBody ContractApplyInvoiceDTO contractApplyInvoiceDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return invoiceApplicationService.contractApplyInvoice(contractApplyInvoiceDto, enterpriseWorkerEntity.getEnterpriseId(), payEnterpriseService);
    }

    @GetMapping("/findEnterpriseSubcontractSummary")
    @ApiOperation(value = "根据商户查询分包列表-汇总", notes = "根据商户查询分包列表-汇总")
    public R findEnterpriseSubcontractSummary(BladeUser bladeUser, @RequestParam(required = false) String serviceProviderName, Query query) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterpriseSubcontractSummary(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/findDetailSummary")
    @ApiOperation(value = "查询详情接口-汇总", notes = "查询详情接口-汇总")
    public R findDetailSummary(Long makerTotalInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.findDetailSummary(makerTotalInvoiceId);
    }

    @GetMapping("/findEnterpriseSubcontractPortal")
    @ApiOperation(value = "根据商户查询分包列表-门征", notes = "根据商户查询分包列表-门征")
    public R findEnterpriseSubcontractPortal(BladeUser bladeUser, @RequestParam(required = false) String serviceProviderName, Query query) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterpriseSubcontractPortal(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/findDetailSubcontractPortal")
    @ApiOperation(value = "查询详情接口-门征", notes = "查询详情接口-门征")
    public R findDetailSubcontractPortal(Long makerInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.findDetailSubcontractPortal(makerInvoiceId);
    }

    @GetMapping("/findEnterpriseCrowdSourcing")
    @ApiOperation(value = "根据商户查询众包/众采", notes = "根据商户查询众包/众采")
    public R findEnterpriseCrowdSourcing(BladeUser bladeUser, @RequestParam(required = false) String serviceProviderName, Query query) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.findEnterpriseCrowdSourcing(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/findDetailCrowdSourcing")
    @ApiOperation(value = "查询详情接口-众包/众采", notes = "查询详情接口-众包/众采")
    public R findDetailCrowdSourcing(Long selfHelpInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.findDetailCrowdSourcing(selfHelpInvoiceId);
    }
}
