package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.ContractApplyInvoiceDTO;
import com.lgyun.system.order.service.*;
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
@Api(value = "商户端---发票/完税证明管理模块相关接口", tags = "商户端---发票/完税证明管理模块相关接口")
public class InvoiceTaxEnterpriseController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private IEnterpriseServiceProviderInvoiceCatalogsService enterpriseProviderInvoiceCatalogsService;
    private IInvoiceApplicationService invoiceApplicationService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    private IWorksheetService worksheetService;

//    @GetMapping("/query-total-invoice-list")
//    @ApiOperation(value = "根据商户查询总包发票", notes = "根据商户查询总包发票")
//    public R queryTotalInvoiceList(@ApiParam(value = "发票号码") @RequestParam(required = false) String invoiceSerialNo, @ApiParam(value = "服务商名字") @RequestParam(required = false) String serviceProviderName,
//                                          @ApiParam(value = "开始时间") @RequestParam(required = false) String startTime, @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
//                                          Query query, BladeUser bladeUser) {
//
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//
//        return payEnterpriseService.findEnterpriseLumpSumInvoice(invoiceSerialNo, serviceProviderName, startTime, endTime, enterpriseWorkerEntity.getEnterpriseId(), Condition.getPage(query.setDescs("create_time")));
//    }

    @PostMapping("/cancel-apply")
    @ApiOperation(value = "取消申请", notes = "取消申请")
    public R cancelApply(Long applicationId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.cancelApply(applicationId);
    }

//    @GetMapping("/query-total-invoice-detail")
//    @ApiOperation(value = "查看总包发票详情", notes = "查看总包发票详情")
//    public R queryTotalInvoiceDetail(Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        return payEnterpriseService.findPayEnterpriseDetails(payEnterpriseId);
//    }

//    @GetMapping("/query-pay-enterprise-list")
//    @ApiOperation(value = "根据商户查询支付清单", notes = "根据商户查询支付清单")
//    public R queryPayEnterpriseList(@RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//
//        return payEnterpriseService.findEnterprisePaymentList(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
//    }







//    @GetMapping("/query-crowd-list")
//    @ApiOperation(value = "根据商户查询众包/众采", notes = "根据商户查询众包/众采")
//    public R queryCrowdList(@RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//
//        return selfHelpInvoiceService.findEnterpriseCrowdSourcing(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("create_time")));
//    }
//
//    @GetMapping("/query-crowd-detail")
//    @ApiOperation(value = "查询众包/众采详情", notes = "查询众包/众采详情")
//    public R queryCrowdDetail(Long selfHelpInvoiceId, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.findDetailCrowdSourcing(selfHelpInvoiceId);
//    }




















    @GetMapping("/query-total-invoice-list-enterprise")
    @ApiOperation(value = "商户端根据商户id查询总包", notes = "商户端根据商户id查询总包")
    public R queryTotalInvoiceListEnterprise(BladeUser bladeUser,Query query,@RequestParam(required = false) String serviceProviderName) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return payEnterpriseService.queryTotalInvoiceListEnterprise(enterpriseWorkerEntity.getEnterpriseId(),serviceProviderName,Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-total-invoice-list-enterprise-apply-details")
    @ApiOperation(value = "商户端根据商户id查询总包申请的详情商户支付清单", notes = "商户端根据商户id查询总包申请的详情商户支付清单")
    public R queryTotalInvoiceListEnterpriseApplyDetails(BladeUser bladeUser,@ApiParam(value = "总包申请id")@NotNull(message = "总包申请id不能为空") @RequestParam(required = false)Long invoiceApplicationId) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return payEnterpriseService.queryTotalInvoiceListEnterpriseApplyDetails(invoiceApplicationId,enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query-total-invoice-list-enterprise-invoice-details")
    @ApiOperation(value = "商户端根据商户id查询总包开票的详情商户支付清单", notes = "商户端根据商户id查询总包开票的详情商户支付清单")
    public R queryTotalInvoiceListEnterpriseInvoiceDetails(BladeUser bladeUser,@ApiParam(value = "总包id")@NotNull(message = "总包id不能为空") @RequestParam(required = false)Long invoicePrintId) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return payEnterpriseService.queryTotalInvoiceListEnterpriseInvoiceDetails(invoicePrintId,enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/apply-total-invoice")
    @ApiOperation(value = "申请总包发票", notes = "申请总包发票")
    public R applyTotalInvoice(@Valid @RequestBody ContractApplyInvoiceDTO contractApplyInvoiceDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return invoiceApplicationService.contractApplyInvoice(contractApplyInvoiceDto, enterpriseWorkerEntity.getEnterpriseId(), payEnterpriseService);
    }

    @GetMapping("/query-invoice-catalogs-list")
    @ApiOperation(value = "查询开票类目", notes = "查询开票类目")
    public R queryInvoiceCatalogsList(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseProviderInvoiceCatalogsService.queryInvoiceCatalogsList(serviceProviderId,enterpriseWorkerEntity.getEnterpriseId(), Condition.getPage(query.setDescs("id")));
    }

    @GetMapping("/query-relation-enterprise-service")
    @ApiOperation(value = "查询和商户关联的服务商", notes = "查询和商户关联的服务商")
    public R queryRelationEnterpriseService(Query query, BladeUser bladeUser,@RequestParam(required = false) String serviceProviderName) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryRelationEnterpriseService(enterpriseWorkerEntity.getEnterpriseId(),serviceProviderName,Condition.getPage(query.setDescs("a.create_time")));
    }

    @GetMapping("/query-enterprise-service-pay-list")
    @ApiOperation(value = "根据商户和服务商查询支付清单", notes = "根据商户和服务商查询支付清单")
    public R queryEnterpriseServicePayList(Query query,BladeUser bladeUser,@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false)Long serviceProviderId) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryEnterpriseServicePayList(enterpriseWorkerEntity.getEnterpriseId(),serviceProviderId,Condition.getPage(query.setDescs("a.create_time")));
    }

    @GetMapping("/query-all-open-sub-list")
    @ApiOperation(value = "根据商户查询汇总代开分包列表", notes = "根据商户查询汇总代开分包列表")
    public R queryAllOpenSubList(@RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterpriseSubcontractSummary(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("pe.create_time")));
    }

    @GetMapping("/query-all-open-sub-detail")
    @ApiOperation(value = "根据商户查询汇总代开分包详情", notes = "根据商户查询汇总代开分包详情")
    public R queryAllOpenSubDetail(Long makerTotalInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.findDetailSummary(makerTotalInvoiceId);
    }

    @GetMapping("/query-single-open-sub-list")
    @ApiOperation(value = "根据商户查询门征单开分包列表", notes = "根据商户查询门征单开分包列表")
    public R querySingleOpenSubList(@RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.findEnterpriseSubcontractPortal(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("pe.create_time")));
    }


    @GetMapping("/query-single-open-sub-detail")
    @ApiOperation(value = "根据商户查询门征单开分包详情", notes = "根据商户查询门征单开分包详情")
    public R querySingleOpenSubDetail(@ApiParam(value = "门征发票id") @NotNull(message = "门征发票id不能为空") @RequestParam(required = false)Long makerInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.findDetailSubcontractPortal(makerInvoiceId);
    }


    @GetMapping("query-worksheet-detail")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R queryWorksheetDetail(@NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getWorksheetWebDetails(Condition.getPage(query.setDescs("wm.create_time")), worksheetId);
    }

    @GetMapping("/query-pay-enterprise-detail")
    @ApiOperation(value = "查询总包支付清单详情", notes = "查询总包支付清单详情")
    public R queryPayEnterpriseDetail(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseDetail(payEnterpriseId);
    }
}
