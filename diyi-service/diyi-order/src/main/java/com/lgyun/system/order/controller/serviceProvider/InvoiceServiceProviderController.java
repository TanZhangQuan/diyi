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
 * (服务商)发票和完税证明接口
 *
 * @author tzq
 * @date 2020/7/22.
 * @time 16:24.
 */
@RestController
@RequestMapping("/invoice/service")
@Validated
@AllArgsConstructor
@Api(value = "(服务商)发票和完税证明接口", tags = "(服务商)发票和完税证明接口")
public class InvoiceServiceProviderController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient iUserClient;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/getLumpSumInvoice")
    @ApiOperation(value = "服务商查询总包发票", notes = "服务商查询总包发票")
    public R getLumpSumInvoice(Query query, BladeUser bladeUser,
                               @RequestParam(required = false) String enterpriseName,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime,
                               @RequestParam InvoiceState companyInvoiceState) {

        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getServiceLumpSumInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/getLumpSumInvoiceDetails")
    @ApiOperation(value = "服务商查询总包发票详情", notes = "服务商查询总包发票详情")
    public R getLumpSumInvoiceDetails(BladeUser bladeUser, Long payEnterpriseId) {

        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
    }

    @PostMapping("/saveLumpSumInvoice")
    @ApiOperation(value = "服务商总包开票", notes = "服务商总包开票")
    public R saveLumpSumInvoice(BladeUser bladeUser, @Valid @RequestBody LumpSumInvoiceDTO lumpSumInvoiceDto) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.saveServiceLumpSumInvoice(serviceProviderWorkerEntity.getServiceProviderId(), lumpSumInvoiceDto.getPayEnterpriseId(), lumpSumInvoiceDto.getServiceProviderName(), lumpSumInvoiceDto.getApplicationId(), lumpSumInvoiceDto.getCompanyInvoiceUrl(), lumpSumInvoiceDto.getExpressSheetNo(), lumpSumInvoiceDto.getExpressCompanyName(), lumpSumInvoiceDto.getInvoiceDesc());
    }


    @GetMapping("/getSubcontractInvoice")
    @ApiOperation(value = "服务商查询未开票分包发票", notes = "服务商查询未开票分包发票")
    public R getSubcontractInvoice(Query query, BladeUser bladeUser,
                                   @RequestParam(required = false) String enterpriseName,
                                   @RequestParam(required = false) String startTime,
                                   @RequestParam(required = false) String endTime) {

        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getSubcontractInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/getSubcontractInvoiceDetails")
    @ApiOperation(value = "服务商查看未开票分包发票详情", notes = "服务商查看未开票分包发票详情")
    public R getSubcontractInvoice(@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/applySummaryInvoice")
    @ApiOperation(value = "服务商申请汇总代开发票", notes = "服务商申请汇总代开发票")
    public R applySummaryInvoice(@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
    }

    @PostMapping("/saveSummaryInvoice")
    @ApiOperation(value = "服务商汇总代开开票", notes = "服务商汇总代开开票")
    public R saveSummaryInvoice(BladeUser bladeUser, @Valid @RequestBody SummaryInvoiceDTO summaryInvoiceDto) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.saveSummaryInvoice(serviceProviderWorkerEntity.getServiceProviderId(), summaryInvoiceDto.getPayEnterpriseId(), summaryInvoiceDto.getServiceProviderName(), summaryInvoiceDto.getInvoiceTypeNo(), summaryInvoiceDto.getInvoiceSerialNo(), summaryInvoiceDto.getInvoiceCategory(), summaryInvoiceDto.getCompanyInvoiceUrl(), summaryInvoiceDto.getMakerTaxUrl(), summaryInvoiceDto.getMakerTaxListUrl());
    }

    @GetMapping("/applyPortalSignInvoice")
    @ApiOperation(value = "服务商申请门征单开发票", notes = "服务商申请门征单开发票")
    public R applyPortalSignInvoice(BladeUser bladeUser, @ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.applyPortalSignInvoice(payEnterpriseId);
    }

    @PostMapping("/savePortalSignInvoice")
    @ApiOperation(value = "服务商门征单开发票开票", notes = "服务商门征单开发票开票")
    public R savePortalSignInvoice(BladeUser bladeUser, @Valid @RequestBody ReleaseWorksheetDTO releaseWorksheetDto) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.savePortalSignInvoice(serviceProviderWorkerEntity.getId(), releaseWorksheetDto.getEnterpriseId(), releaseWorksheetDto.getMakerIds(), releaseWorksheetDto.getWorksheetName());
    }

    @GetMapping("/getSummaryInvoice")
    @ApiOperation(value = "服务商查询已汇总代开的发票", notes = "服务商查询已汇总代开的发票")
    public R getSummaryInvoice(BladeUser bladeUser, Query query,
                               @RequestParam(required = false) String enterpriseName,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime) {

        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getServiceSummaryInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/getSummaryInvoiceDetails")
    @ApiOperation(value = "服务商查询已汇总代开的发票详情", notes = "服务商查询已汇总代开的发票详情")
    public R getSummaryInvoiceDetails(BladeUser bladeUser, @ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSummaryInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/getPortalSignInvoice")
    @ApiOperation(value = "服务商查询已门征单开的发票", notes = "服务商查询已门征单开的发票")
    public R getPortalSignInvoice(BladeUser bladeUser, Query query,
                                  @RequestParam(required = false) String enterpriseName,
                                  @RequestParam(required = false) String startTime,
                                  @RequestParam(required = false) String endTime) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getServicePortalSignInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/getPortalSignInvoiceDetails")
    @ApiOperation(value = "服务商查询已门征单开的发票详情", notes = "服务商查询已门征单开的发票详情")
    public R getPortalSignInvoiceDetails(BladeUser bladeUser, @ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServicePortalSignInvoiceDetails(payEnterpriseId);
    }


    @GetMapping("/getServiceCrowdSour")
    @ApiOperation(value = "服务商查询众包发票", notes = "服务商查询众包发票")
    public R getServiceCrowdSour(BladeUser bladeUser, Query query,
                                 @RequestParam(required = false) String enterpriseName,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime,
                                 SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState) {
        //获取当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.getServiceCrowdSour(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, selfHelpInvoiceSpApplyState, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/getServiceCrowdSourDetails")
    @ApiOperation(value = "服务商查询众包发票详情", notes = "服务商查询众包发票详情")
    public R getServiceCrowdSourDetails(BladeUser bladeUser, Long providerSelfHelpInvoiceId) {
        //获取当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getServiceCrowdSourDetails(providerSelfHelpInvoiceId);
    }

    @PostMapping("/saveServiceCrowdSour")
    @ApiOperation(value = "服务商众包发票开票", notes = "服务商众包发票开票")
    public R savePortalSignInvoice(BladeUser bladeUser, String serviceProviderName, Long providerSelfHelpInvoiceId, String expressNo, String expressCompanyName, String invoiceScanPictures, String taxScanPictures) {
        return selfHelpInvoiceService.savePortalSignInvoice(serviceProviderName, providerSelfHelpInvoiceId, expressNo, expressCompanyName, invoiceScanPictures, taxScanPictures);
    }
}
