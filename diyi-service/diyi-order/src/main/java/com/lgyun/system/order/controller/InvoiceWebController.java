package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.ContractApplyInvoiceDto;
import com.lgyun.system.order.entity.EnterpriseServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.service.IEnterpriseServiceProviderInvoiceCatalogsService;
import com.lgyun.system.order.service.IInvoiceApplicationService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.order.wrapper.EnterpriseServiceProviderInvoiceCatalogsWrapper;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商户发票和完税证明接口
 *
 * @author jun.
 * @date 2020/7/22.
 * @time 16:24.
 */
@RestController
@RequestMapping("/web/invoice")
@Validated
@AllArgsConstructor
@Api(value = "商户发票和完税证明接口", tags = "商户发票和完税证明接口")
public class InvoiceWebController {

    private IPayEnterpriseService payEnterpriseService;
    private IEnterpriseServiceProviderInvoiceCatalogsService enterpriseProviderInvoiceCatalogsService;
    private IInvoiceApplicationService invoiceApplicationService;
    private IUserClient iUserClient;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/findEnterpriseLumpSumInvoice")
    @ApiOperation(value = "根据商户查询总包发票", notes = "根据商户查询总包发票")
    public R findEnterpriseLumpSumInvoice(@RequestParam(required = false) String invoiceSerialNo,
                                          @RequestParam(required = false) String serviceProviderName,
                                          @RequestParam(required = false) String startTime,
                                          @RequestParam(required = false) String endTime,
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
    public R withdraw(Long applicationId) {
        return payEnterpriseService.withdraw(applicationId);
    }

    @GetMapping("/findPayEnterpriseDetails")
    @ApiOperation(value = "查看总包发票详情", notes = "查看总包发票详情")
    public R findPayEnterpriseDetails(Long payEnterpriseId) {
        return payEnterpriseService.findPayEnterpriseDetails(payEnterpriseId);
    }

    @GetMapping("/findEnterprisePaymentList")
    @ApiOperation(value = "根据商户查询支付清单", notes = "根据商户查询支付清单")
    public R findEnterprisePaymentList(BladeUser bladeUser, @RequestParam(required = false) String serviceProviderName, Query query) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterprisePaymentList(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询服务商开票类目", notes = "查询服务商开票类目")
    public R list(Query query) {
        IPage<EnterpriseServiceProviderInvoiceCatalogsEntity> pages = enterpriseProviderInvoiceCatalogsService.page(Condition.getPage(query.setDescs("create_time")));
        return R.data(EnterpriseServiceProviderInvoiceCatalogsWrapper.build().pageVO(pages));
    }

    @PostMapping("/contractApplyInvoice")
    @ApiOperation(value = "申请总包发票", notes = "申请总包发票")
    public R contractApplyInvoice(@Valid @RequestBody ContractApplyInvoiceDto contractApplyInvoiceDto, BladeUser bladeUser) {
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
    public R findDetailSummary(Long makerTotalInvoiceId) {
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
    public R findDetailSubcontractPortal(Long makerInvoiceId) {
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
    public R findDetailCrowdSourcing(Long selfHelpInvoiceId) {
        return selfHelpInvoiceService.findDetailCrowdSourcing(selfHelpInvoiceId);
    }
}
