package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.*;
import com.lgyun.system.order.service.IPayEnterpriseService;
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
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/service-provider/invoice-tax")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---发票/完税证明管理模块相关接口", tags = "服务商端---发票/完税证明管理模块相关接口")
public class InvoiceTaxServiceProviderController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private IWorksheetService worksheetService;


//    @GetMapping("/query-total-invoice")
//    @ApiOperation(value = "查询总包发票", notes = "查询总包发票")
//    public R queryTotalInvoice(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                               @RequestParam(required = false) String endTime, @RequestParam InvoiceState companyInvoiceState, Query query, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return payEnterpriseService.getServiceLumpSumInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
//    }

//    @GetMapping("/query-total-invoice-detail")
//    @ApiOperation(value = "查询总包发票详情", notes = "查询总包发票详情")
//    public R queryTotalInvoiceDetail(BladeUser bladeUser, Long payEnterpriseId) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
//    }
//
//    @PostMapping("/create-total-invoice")
//    @ApiOperation(value = "总包开票", notes = "总包开票")
//    public R createTotalInvoice(@Valid @RequestBody LumpSumInvoiceDTO lumpSumInvoiceDto, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return payEnterpriseService.saveServiceLumpSumInvoice(serviceProviderWorkerEntity.getServiceProviderId(), lumpSumInvoiceDto.getPayEnterpriseId(), lumpSumInvoiceDto.getServiceProviderName(), lumpSumInvoiceDto.getApplicationId(), lumpSumInvoiceDto.getCompanyInvoiceUrl(), lumpSumInvoiceDto.getExpressSheetNo(), lumpSumInvoiceDto.getExpressCompanyName(), lumpSumInvoiceDto.getInvoiceDesc());
//    }
//
//    @GetMapping("/query-unopen-sub-invoice-list")
//    @ApiOperation(value = "服务商查询未开票分包发票", notes = "服务商查询未开票分包发票")
//    public R queryUnopenSubInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                                       @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return payEnterpriseService.getSubcontractInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
//    }
//
//    @GetMapping("/query-unopen-sub-invoice-detail")
//    @ApiOperation(value = "查看未开票分包发票详情", notes = "查看未开票分包发票详情")
//    public R queryUnopenSubInvoiceDetail(@ApiParam(value = "商户支付清单Id", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
//    }
//
//    @GetMapping("/apply-all-open-invoice")
//    @ApiOperation(value = "申请汇总代开发票", notes = "申请汇总代开发票")
//    public R applyAllOpenInvoice(@ApiParam(value = "商户支付清单Id", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
//    }
//
//    @PostMapping("/create-all-open-invoice")
//    @ApiOperation(value = "汇总代开开票", notes = "汇总代开开票")
//    public R createAllOpenInvoice(@Valid @RequestBody SummaryInvoiceDTO summaryInvoiceDto, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return payEnterpriseService.saveSummaryInvoice(serviceProviderWorkerEntity.getServiceProviderId(), summaryInvoiceDto.getPayEnterpriseId(), summaryInvoiceDto.getServiceProviderName(), summaryInvoiceDto.getInvoiceTypeNo(), summaryInvoiceDto.getInvoiceSerialNo(), summaryInvoiceDto.getInvoiceCategory(), summaryInvoiceDto.getCompanyInvoiceUrl(), summaryInvoiceDto.getMakerTaxUrl(), summaryInvoiceDto.getMakerTaxListUrl());
//    }
//
//    @GetMapping("/apply-sign-open-invoice")
//    @ApiOperation(value = "申请门征单开发票", notes = "申请门征单开发票")
//    public R applySignOpenInvoice(@ApiParam(value = "商户支付清单Id", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.applyPortalSignInvoice(payEnterpriseId);
//    }
//
//    @PostMapping("/create-single-open-invoice")
//    @ApiOperation(value = "门征单开发票开票", notes = "门征单开发票开票")
//    public R createSingleOpenInvoice(@Valid @RequestBody ReleaseWorksheetDTO releaseWorksheetDto, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return payEnterpriseService.savePortalSignInvoice(serviceProviderWorkerEntity.getId(), releaseWorksheetDto.getEnterpriseId(), releaseWorksheetDto.getMakerIds(), releaseWorksheetDto.getWorksheetName());
//    }
//
//    @GetMapping("/query-all-open-invoice-list")
//    @ApiOperation(value = "查询已汇总代开的发票", notes = "查询已汇总代开的发票")
//    public R queryAllOpenInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                                     @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
//
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return payEnterpriseService.getServiceSummaryInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
//    }
//
//    @GetMapping("/query-all-open-invoice-detail")
//    @ApiOperation(value = "查询已汇总代开的发票详情", notes = "查询已汇总代开的发票详情")
//    public R queryAllOpenInvoiceDetail(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getSummaryInvoiceDetails(payEnterpriseId);
//    }
//
//    @GetMapping("/query-single-open-invoice-list")
//    @ApiOperation(value = "查询已门征单开的发票", notes = "查询已门征单开的发票")
//    public R querySingleOpenInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                                        @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return payEnterpriseService.getServicePortalSignInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
//    }
//
//
//    @GetMapping("/query-single-open-invoice-detail")
//    @ApiOperation(value = "查询已门征单开的发票详情", notes = "查询已门征单开的发票详情")
//    public R querySingleOpenInvoiceDetail(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getServicePortalSignInvoiceDetails(payEnterpriseId);
//    }
//
//    @GetMapping("/query-crowd-invoice-list")
//    @ApiOperation(value = "查询众包发票", notes = "查询众包发票")
//    public R queryCrowdInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                                   @RequestParam(required = false) String endTime, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, Query query, BladeUser bladeUser) {
//        //获取当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return selfHelpInvoiceService.getServiceCrowdSour(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, selfHelpInvoiceSpApplyState, Condition.getPage(query.setDescs("create_time")));
//    }
//
//    @GetMapping("/query-crowd-invoice-detail")
//    @ApiOperation(value = "查询众包发票详情", notes = "查询众包发票详情")
//    public R queryCrowdInvoiceDetail(Long providerSelfHelpInvoiceId, BladeUser bladeUser) {
//        //获取当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.getServiceCrowdSourDetails(providerSelfHelpInvoiceId);
//    }
//
//    @PostMapping("/create-crowd-invoice")
//    @ApiOperation(value = "众包发票开票", notes = "众包发票开票")
//    public R createCrowdInvoice(String serviceProviderName, Long providerSelfHelpInvoiceId, String expressNo, String expressCompanyName, String invoiceScanPictures, String taxScanPictures, BladeUser bladeUser) {
//        //获取当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.savePortalSignInvoice(serviceProviderName, providerSelfHelpInvoiceId, expressNo, expressCompanyName, invoiceScanPictures, taxScanPictures);
//    }























    @GetMapping("/query-total-invoice")
    @ApiOperation(value = "服务商查询总包发票列表", notes = "服务商查询总包发票列表")
    public R queryTotalInvoice(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime, @RequestParam InvoiceState companyInvoiceState, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getServiceLumpSumInvoice(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, companyInvoiceState, Condition.getPage(query.setDescs("tmp.create_time")));
    }

    @GetMapping("/query-total-opened-invoice-detail")
    @ApiOperation(value = "服务商查询已开总包发票详情", notes = "服务商查询已开总包发票详情")
    public R queryOpenedTotalInvoiceDetail(BladeUser bladeUser, Long invoicePrintId) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryOpenedTotalInvoiceDetail(invoicePrintId);
    }




    @GetMapping("/query-total-invoice-detail")
    @ApiOperation(value = "服务商查询未开总包发票详情", notes = "服务商查询未开总包发票详情")
    public R queryTotalInvoiceDetail(BladeUser bladeUser, Long payEnterpriseId) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/query-total-merge-invoice")
    @ApiOperation(value = "服务商总包合并开票", notes = "服务商总包合并开票")
    public R queryTotalMergeInvoice(BladeUser bladeUser, String payEnterpriseIds) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryTotalMergeInvoice(payEnterpriseIds);
    }

    @GetMapping("/query-total-apply-invoice")
    @ApiOperation(value = "服务商根据总包申请开票", notes = "服务商根据总包申请开票")
    public R queryTotalApplyInvoice(BladeUser bladeUser, Long invoiceApplicationId) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryTotalApplyInvoice(invoiceApplicationId);
    }

    @PostMapping("/create-total-invoice")
    @ApiOperation(value = "总包开票", notes = "总包开票")
    public R createTotalInvoice(@Valid @RequestBody LumpSumInvoiceDTO lumpSumInvoiceDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.saveServiceLumpSumInvoice(serviceProviderWorkerEntity.getServiceProviderId(), lumpSumInvoiceDto.getPayEnterpriseId(), lumpSumInvoiceDto.getServiceProviderName(), lumpSumInvoiceDto.getCompanyInvoiceUrl(), lumpSumInvoiceDto.getExpressSheetNo(), lumpSumInvoiceDto.getExpressCompanyName(), lumpSumInvoiceDto.getInvoiceDesc(),lumpSumInvoiceDto.getInvoiceTypeNo(),lumpSumInvoiceDto.getInvoiceSerialNo(),lumpSumInvoiceDto.getInvoiceCategory());
    }


    @PostMapping("/create-total-merge-invoice")
    @ApiOperation(value = "服务商总包合并开票", notes = "服务商总包合并开票")
    public R createTotalMergeInvoice(@Valid @RequestBody LumpSumMergeInvoiceDTO lumpSumInvoiceDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.saveServiceLumpSumMergeInvoice(serviceProviderWorkerEntity.getServiceProviderId(), lumpSumInvoiceDto.getPayEnterpriseIds(), lumpSumInvoiceDto.getServiceProviderName(), lumpSumInvoiceDto.getCompanyInvoiceUrl(), lumpSumInvoiceDto.getExpressSheetNo(), lumpSumInvoiceDto.getExpressCompanyName(), lumpSumInvoiceDto.getInvoiceDesc(),lumpSumInvoiceDto.getInvoiceTypeNo(),lumpSumInvoiceDto.getInvoiceSerialNo(),lumpSumInvoiceDto.getInvoiceCategory());
    }


    @PostMapping("/create-total-apply-invoice")
    @ApiOperation(value = "服务商总包根据申请开票", notes = "服务商总包根据申请开票")
    public R createTotalApplyInvoice(@Valid @RequestBody LumpSumApplyInvoiceDTO lumpSumInvoiceDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.createTotalApplyInvoice(serviceProviderWorkerEntity.getServiceProviderId(), lumpSumInvoiceDto.getServiceProviderName(), lumpSumInvoiceDto.getApplicationId(), lumpSumInvoiceDto.getCompanyInvoiceUrl(), lumpSumInvoiceDto.getExpressSheetNo(), lumpSumInvoiceDto.getExpressCompanyName(), lumpSumInvoiceDto.getInvoiceDesc(),lumpSumInvoiceDto.getInvoiceTypeNo(),lumpSumInvoiceDto.getInvoiceSerialNo(),lumpSumInvoiceDto.getInvoiceCategory());
    }


    @PostMapping("/update-total-invoice")
    @ApiOperation(value = "服务商根据总包开票id修改总包发票", notes = "服务商根据总包开票id修改总包发票")
    public R updateTotalInvoice(@Valid @RequestBody LumpInvoiceDTO lumpInvoiceDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        lumpInvoiceDTO.setServiceProviderId(serviceProviderWorkerEntity.getServiceProviderId());
        return payEnterpriseService.updateTotalInvoice(lumpInvoiceDTO);
    }



    @GetMapping("/query-all-sub-list")
    @ApiOperation(value = "根据服务商查询分包列表", notes = "根据服务商查询分包列表")
    public R queryAllOpenSubList(@RequestParam(required = false) String enterprise_name,@RequestParam InvoiceState companyInvoiceState, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.findServiceSubcontractSummary(serviceProviderWorkerEntity.getServiceProviderId(), enterprise_name, companyInvoiceState,Condition.getPage(query.setDescs("pe.create_time")));
    }

    @GetMapping("/query-all-sub-detail")
    @ApiOperation(value = "服务商根据商户支付清单查询分包详情", notes = "服务商根据商户支付清单查询分包详情")
    public R queryAllOpenSubDetail(String payEnterpriseIds, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.findServiceDetailSummary(payEnterpriseIds);
    }

    @PostMapping("/create-summary-agency-invoice")
    @ApiOperation(value = "服务商汇总代开发票", notes = "服务商汇总代开发票")
    public R createSummaryAgencyInvoice(@Valid @RequestBody SummaryInvoiceDTO summaryInvoiceDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.createSummaryAgencyInvoice(summaryInvoiceDTO);
    }


    @PostMapping("/create-door-sign-invoice")
    @ApiOperation(value = "服务商门征发票", notes = "服务商门征发票")
    public R createDoorSignInvoice(@Valid @RequestBody DoorSignInvoiceDTO doorSignInvoiceDTO,  BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.createDoorSignInvoice( doorSignInvoiceDTO);
    }


//    @GetMapping("/query-single-open-invoice-detail")
//    @ApiOperation(value = "查询已门征单开的发票详情", notes = "查询已门征单开的发票详情")
//    public R querySingleOpenInvoiceDetail(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
////        //查询当前服务商员工
////        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
////        if (!(result.isSuccess())) {
////            return result;
////        }
//
//        return payEnterpriseService.getServicePortalSignInvoiceDetails(payEnterpriseId);
//    }


    @GetMapping("query-worksheet-detail")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R queryWorksheetDetail(@NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getWorksheetWebDetails(Condition.getPage(query.setDescs("wm.create_time")), worksheetId);
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

    @GetMapping("/query-pay-enterprise-maker-list")
    @ApiOperation(value = "根据支付清单id查询支付明细", notes = "根据支付清单id查询支付明细")
    public R queryPayEnterpriseMakerList(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseMakerList(payEnterpriseId);
    }

//    @GetMapping("/query-crowd-invoice-list")
//    @ApiOperation(value = "查询众包发票", notes = "查询众包发票")
//    public R queryCrowdInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                                   @RequestParam(required = false) String endTime, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, Query query, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return selfHelpInvoiceService.getServiceCrowdSour(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, startTime, endTime, selfHelpInvoiceSpApplyState, Condition.getPage(query.setDescs("create_time")));
//    }

//    @GetMapping("/query-crowd-invoice-detail")
//    @ApiOperation(value = "查询众包发票详情", notes = "查询众包发票详情")
//    public R queryCrowdInvoiceDetail(Long providerSelfHelpInvoiceId, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.getServiceCrowdSourDetails(providerSelfHelpInvoiceId);
//    }
//
//    @PostMapping("/create-crowd-invoice")
//    @ApiOperation(value = "众包发票开票", notes = "众包发票开票")
//    public R createCrowdInvoice(String serviceProviderName, Long providerSelfHelpInvoiceId, String expressNo, String expressCompanyName, String invoiceScanPictures, String taxScanPictures, BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.savePortalSignInvoice(serviceProviderName, providerSelfHelpInvoiceId, expressNo, expressCompanyName, invoiceScanPictures, taxScanPictures);
//    }
}
