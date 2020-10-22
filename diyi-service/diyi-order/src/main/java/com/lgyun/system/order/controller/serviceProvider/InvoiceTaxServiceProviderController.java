package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.LumpSumInvoiceDTO;
import com.lgyun.system.order.dto.ReleaseWorksheetDTO;
import com.lgyun.system.order.dto.SummaryInvoiceDTO;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
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
 * 服务商端---发票和完税证明管理模块相关接口
 *
 * @author tzq
 * @date 2020/7/22.
 * @time 16:24.
 */
@RestController
@RequestMapping("/service-provider/invoice-tax")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---发票和完税证明管理模块相关接口", tags = "服务商端---发票和完税证明管理模块相关接口")
public class InvoiceTaxServiceProviderController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient iUserClient;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-total-invoice")
    @ApiOperation(value = "查询总包发票", notes = "查询总包发票")
    public R queryTotalInvoice(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime, @RequestParam InvoiceState companyInvoiceState, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getServiceLumpSumInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-total-invoice-detail")
    @ApiOperation(value = "查询总包发票详情", notes = "查询总包发票详情")
    public R queryTotalInvoiceDetail(BladeUser bladeUser, Long payEnterpriseId) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
    }

    @PostMapping("/create-total-invoice")
    @ApiOperation(value = "总包开票", notes = "总包开票")
    public R createTotalInvoice(@Valid @RequestBody LumpSumInvoiceDTO lumpSumInvoiceDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.saveServiceLumpSumInvoice(serviceProviderWorkerEntity.getServiceProviderId(), lumpSumInvoiceDto.getPayEnterpriseId(), lumpSumInvoiceDto.getServiceProviderName(), lumpSumInvoiceDto.getApplicationId(), lumpSumInvoiceDto.getCompanyInvoiceUrl(), lumpSumInvoiceDto.getExpressSheetNo(), lumpSumInvoiceDto.getExpressCompanyName(), lumpSumInvoiceDto.getInvoiceDesc());
    }

    @GetMapping("/query-unopen-sub-invoice-list")
    @ApiOperation(value = "服务商查询未开票分包发票", notes = "服务商查询未开票分包发票")
    public R queryUnopenSubInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                       @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getSubcontractInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-unopen-sub-invoice-detail")
    @ApiOperation(value = "查看未开票分包发票详情", notes = "查看未开票分包发票详情")
    public R queryUnopenSubInvoiceDetail(@ApiParam(value = "商户支付清单Id", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/apply-all-open-invoice")
    @ApiOperation(value = "申请汇总代开发票", notes = "申请汇总代开发票")
    public R applyAllOpenInvoice(@ApiParam(value = "商户支付清单Id", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
    }

    @PostMapping("/create-all-open-invoice")
    @ApiOperation(value = "汇总代开开票", notes = "汇总代开开票")
    public R createAllOpenInvoice(@Valid @RequestBody SummaryInvoiceDTO summaryInvoiceDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.saveSummaryInvoice(serviceProviderWorkerEntity.getServiceProviderId(), summaryInvoiceDto.getPayEnterpriseId(), summaryInvoiceDto.getServiceProviderName(), summaryInvoiceDto.getInvoiceTypeNo(), summaryInvoiceDto.getInvoiceSerialNo(), summaryInvoiceDto.getInvoiceCategory(), summaryInvoiceDto.getCompanyInvoiceUrl(), summaryInvoiceDto.getMakerTaxUrl(), summaryInvoiceDto.getMakerTaxListUrl());
    }

    @GetMapping("/apply-sign-open-invoice")
    @ApiOperation(value = "申请门征单开发票", notes = "申请门征单开发票")
    public R applySignOpenInvoice(@ApiParam(value = "商户支付清单Id", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.applyPortalSignInvoice(payEnterpriseId);
    }

    @PostMapping("/create-single-open-invoice")
    @ApiOperation(value = "门征单开发票开票", notes = "门征单开发票开票")
    public R createSingleOpenInvoice(@Valid @RequestBody ReleaseWorksheetDTO releaseWorksheetDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.savePortalSignInvoice(serviceProviderWorkerEntity.getId(), releaseWorksheetDto.getEnterpriseId(), releaseWorksheetDto.getMakerIds(), releaseWorksheetDto.getWorksheetName());
    }

    @GetMapping("/query-all-open-invoice-list")
    @ApiOperation(value = "查询汇总代开的发票", notes = "查询汇总代开的发票")
    public R queryAllOpenInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                     @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {

        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getServiceSummaryInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-all-open-invoice-detail")
    @ApiOperation(value = "查询汇总代开的发票详情", notes = "查询汇总代开的发票详情")
    public R queryAllOpenInvoiceDetail(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSummaryInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/query-single-open-invoice-list")
    @ApiOperation(value = "查询门征单开的发票", notes = "查询门征单开的发票")
    public R querySingleOpenInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                        @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getServicePortalSignInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/query-single-open-invoice-detail")
    @ApiOperation(value = "查询门征单开的发票详情", notes = "查询门征单开的发票详情")
    public R querySingleOpenInvoiceDetail(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServicePortalSignInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/query-crowd-invoice-list")
    @ApiOperation(value = "查询众包发票", notes = "查询众包发票")
    public R queryCrowdInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                   @RequestParam(required = false) String endTime, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, Query query, BladeUser bladeUser) {
        //获取当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.getServiceCrowdSour(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, selfHelpInvoiceSpApplyState, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-crowd-invoice-detail")
    @ApiOperation(value = "查询众包发票详情", notes = "查询众包发票详情")
    public R queryCrowdInvoiceDetail(Long providerSelfHelpInvoiceId, BladeUser bladeUser) {
        //获取当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getServiceCrowdSourDetails(providerSelfHelpInvoiceId);
    }

    @PostMapping("/create-crowd-invoice")
    @ApiOperation(value = "众包发票开票", notes = "众包发票开票")
    public R createCrowdInvoice(String serviceProviderName, Long providerSelfHelpInvoiceId, String expressNo, String expressCompanyName, String invoiceScanPictures, String taxScanPictures, BladeUser bladeUser) {
        //获取当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.savePortalSignInvoice(serviceProviderName, providerSelfHelpInvoiceId, expressNo, expressCompanyName, invoiceScanPictures, taxScanPictures);
    }
}
