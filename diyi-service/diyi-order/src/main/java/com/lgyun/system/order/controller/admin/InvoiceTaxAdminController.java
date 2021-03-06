package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.*;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.AdminEntity;
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
@RequestMapping("/admin/invoice-tax")
@Validated
@AllArgsConstructor
@Api(value = "平台端---发票/完税证明管理模块相关接口", tags = "平台端---发票/完税证明管理模块相关接口")
public class InvoiceTaxAdminController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private IWorksheetService worksheetService;
//    @GetMapping("/query-total-invoice-list")
//    @ApiOperation(value = "查询总包发票", notes = "查询总包发票")
//    public R queryTotalInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                                   @RequestParam(required = false) String endTime, @RequestParam CompanyInvoiceState companyInvoiceState, Query query, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getServiceLumpSumInvoice(null, enterpriseName, startTime, endTime, companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
//    }

//    @GetMapping("/query-total-invoice-detail")
//    @ApiOperation(value = "查询总包发票详情", notes = "查询总包发票详情")
//    public R queryTotalInvoiceDetail(Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
//    }

//    @PostMapping("/create-total-invoice")
//    @ApiOperation(value = "总包开票", notes = "总包开票")
//    public R createTotalInvoice(Long payEnterpriseId, Long applicationId, String companyInvoiceUrl,
//                                String expressSheetNo, String expressCompanyName,
//                                String invoiceDesc, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.saveServiceLumpSumInvoice(null, payEnterpriseId, null, applicationId, companyInvoiceUrl, expressSheetNo, expressCompanyName, invoiceDesc);
//    }

//    @GetMapping("/query-unopen-sub-invoice-list")
//    @ApiOperation(value = "查询未开票分包发票", notes = "查询未开票分包发票")
//    public R queryUnopenSubInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                                       @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getSubcontractInvoice(null, enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
//    }


//    @GetMapping("/query-unopen-sub-invoice-detail")
//    @ApiOperation(value = "查看未开票分包发票详情", notes = "查看未开票分包发票详情")
//    public R queryUnopenSubInvoiceDetail(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
//    }

//    @GetMapping("/apply-all-open-invoice")
//    @ApiOperation(value = "申请汇总代开发票", notes = "申请汇总代开发票")
//    public R applyAllOpenInvoice(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
//    }

//    @PostMapping("/create-all-open-invoice")
//    @ApiOperation(value = "汇总代开开票", notes = "汇总代开开票")
//    public R createAllOpenInvoice(@ApiParam(value = "总包支付清单") @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId,
//                                  @ApiParam(value = "发票代码") @NotBlank(message = "请输入发票代码") String invoiceTypeNo,
//                                  @ApiParam(value = "发票号码") @NotBlank(message = "请输入发票号码") String invoiceSerialNo,
//                                  @ApiParam(value = "货物或应税劳务、服务名称") @NotBlank(message = "请输入货物或应税劳务、服务名称") String invoiceCategory,
//                                  @ApiParam(value = "汇总代开发票URL") @NotBlank(message = "请上传汇总代开发票") String companyInvoiceUrl,
//                                  @ApiParam(value = "总完税证明URL") @NotBlank(message = "请上传总完税证明") String makerTaxUrl,
//                                  @ApiParam(value = "清单式完税凭证URL") @NotBlank(message = "请上传清单式完税凭证") String makerTaxListUrl, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.saveSummaryInvoice(null, payEnterpriseId, null, invoiceTypeNo, invoiceSerialNo, invoiceCategory, companyInvoiceUrl, makerTaxUrl, makerTaxListUrl);
//    }

//    @GetMapping("/apply-sign-open-invoice")
//    @ApiOperation(value = "申请门征单开发票", notes = "申请门征单开发票")
//    public R applySignOpenInvoice(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.applyPortalSignInvoice(payEnterpriseId);
//    }

//    @PostMapping("/create-single-open-invoice")
//    @ApiOperation(value = "门征单开发票开票", notes = "门征单开发票开票")
//    public R createSingleOpenInvoice(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId,
//                                     @ApiParam(value = "分包支付明细", required = true) @NotBlank(message = "请输入分包支付明细") @RequestParam(required = false) String payMakers, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.savePortalSignInvoice(null, payEnterpriseId, payMakers, null);
//    }

//    @GetMapping("/query-all-open-invoice-list")
//    @ApiOperation(value = "查询已汇总代开的发票", notes = "查询已汇总代开的发票")
//    public R queryAllOpenInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                                     @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getServiceSummaryInvoice(null, enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
//    }

//    @GetMapping("/query-all-open-invoice-detail")
//    @ApiOperation(value = "查询已汇总代开的发票详情", notes = "查询已汇总代开的发票详情")
//    public R queryAllOpenInvoiceDetail(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getSummaryInvoiceDetails(payEnterpriseId);
//    }

//    @GetMapping("/query-single-open-invoice-list")
//    @ApiOperation(value = "查询已门征单开的发票", notes = "查询已门征单开的发票")
//    public R querySingleOpenInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
//                                        @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getServicePortalSignInvoice(null, enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
//    }


//    @GetMapping("/query-single-open-invoice-detail")
//    @ApiOperation(value = "查询已门征单开的发票详情", notes = "查询已门征单开的发票详情")
//    public R querySingleOpenInvoiceDetail(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getServicePortalSignInvoiceDetails(payEnterpriseId);
//    }


//    @GetMapping("/query-crowd-invoice-list")
//    @ApiOperation(value = "查询众包发票", notes = "查询众包发票")
//    public R queryCrowdInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
//                                   SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, Query query, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.getServiceCrowdSour(null, enterpriseName, startTime, endTime, selfHelpInvoiceSpApplyState, Condition.getPage(query.setDescs("create_time")));
//    }

//    @GetMapping("/query-crowd-invoice-detail")
//    @ApiOperation(value = "查询众包发票详情", notes = "查询众包发票详情")
//    public R queryCrowdInvoiceDetail(Long providerSelfHelpInvoiceId, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.getServiceCrowdSourDetails(providerSelfHelpInvoiceId);
//    }

//    @PostMapping("/create-crowd-invoice")
//    @ApiOperation(value = "众包发票开票", notes = "众包发票开票")
//    public R createCrowdInvoice(Long providerSelfHelpInvoiceId, String expressNo, String expressCompanyName, String invoiceScanPictures, String taxScanPictures, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.savePortalSignInvoice(null, providerSelfHelpInvoiceId, expressNo, expressCompanyName, invoiceScanPictures, taxScanPictures);
//    }


    @GetMapping("/query-total-invoice")
    @ApiOperation(value = "平台查询总包发票列表", notes = "平台查询总包发票列表")
    public R queryTotalInvoice(@RequestParam(required = false) String enterpriseName, @ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime, @RequestParam CompanyInvoiceState companyInvoiceState, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.getServiceLumpSumInvoice(serviceProviderId, enterpriseName, startTime, endTime, companyInvoiceState, Condition.getPage(query.setDescs("tmp.create_time")));
    }

    @GetMapping("/query-total-opened-invoice-detail")
    @ApiOperation(value = "平台查询已开总包发票详情", notes = "平台查询已开总包发票详情")
    public R queryOpenedTotalInvoiceDetail(BladeUser bladeUser, Long invoicePrintId) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryTotalInvoiceListEnterpriseInvoiceDetails(invoicePrintId);
    }

//    @GetMapping("/query-total-invoice-detail")
//    @ApiOperation(value = "平台查询未开总包发票详情", notes = "平台查询未开总包发票详情")
//    public R queryTotalInvoiceDetail(BladeUser bladeUser, Long payEnterpriseId) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
//    }

    @GetMapping("/query-total-merge-invoice")
    @ApiOperation(value = "平台总包合并开票", notes = "平台总包合并开票")
    public R queryTotalMergeInvoice(BladeUser bladeUser, String payEnterpriseIds) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryTotalMergeInvoice(payEnterpriseIds);
    }

    @GetMapping("/query-total-apply-invoice")
    @ApiOperation(value = "平台根据总包申请开票", notes = "平台根据总包申请开票")
    public R queryTotalApplyInvoice(BladeUser bladeUser, Long invoiceApplicationId) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryTotalInvoiceListEnterpriseApplyDetails(invoiceApplicationId);
    }

//    @PostMapping("/create-total-invoice")
//    @ApiOperation(value = "总包开票", notes = "总包开票")
//    public R createTotalInvoice(@Valid @RequestBody LumpSumInvoiceDTO lumpSumInvoiceDto, BladeUser bladeUser) {
//        //查询当前管理员
//        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return payEnterpriseService.saveServiceLumpSumInvoice(lumpSumInvoiceDto.getServiceProviderId(), lumpSumInvoiceDto.getPayEnterpriseId(), lumpSumInvoiceDto.getServiceProviderName(), lumpSumInvoiceDto.getCompanyInvoiceUrl(), lumpSumInvoiceDto.getExpressSheetNo(), lumpSumInvoiceDto.getExpressCompanyName(), lumpSumInvoiceDto.getInvoiceDesc(),lumpSumInvoiceDto.getInvoiceTypeNo(),lumpSumInvoiceDto.getInvoiceSerialNo(),lumpSumInvoiceDto.getInvoiceCategory());
//    }


    @PostMapping("/create-total-merge-invoice")
    @ApiOperation(value = "平台总包合并开票", notes = "平台总包合并开票")
    public R createTotalMergeInvoice(@Valid @RequestBody LumpSumMergeInvoiceDTO lumpSumInvoiceDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.saveServiceLumpSumMergeInvoice(lumpSumInvoiceDto.getServiceProviderId(), lumpSumInvoiceDto.getPayEnterpriseIds(), lumpSumInvoiceDto.getServiceProviderName(), lumpSumInvoiceDto.getCompanyInvoiceUrl(), lumpSumInvoiceDto.getExpressSheetNo(), lumpSumInvoiceDto.getExpressCompanyName(), lumpSumInvoiceDto.getInvoiceDesc(), null, null, lumpSumInvoiceDto.getInvoiceCategory(), lumpSumInvoiceDto.getInvoiceMode(), lumpSumInvoiceDto.getPartInvoiceAmount());
    }


    @PostMapping("/create-total-apply-invoice")
    @ApiOperation(value = "平台总包根据申请开票", notes = "平台总包根据申请开票")
    public R createTotalApplyInvoice(@Valid @RequestBody LumpSumApplyInvoiceDTO lumpSumInvoiceDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.createTotalApplyInvoice(lumpSumInvoiceDto.getServiceProviderId(), lumpSumInvoiceDto.getServiceProviderName(), lumpSumInvoiceDto.getApplicationId(), lumpSumInvoiceDto.getCompanyInvoiceUrl(), lumpSumInvoiceDto.getExpressSheetNo(), lumpSumInvoiceDto.getExpressCompanyName(), lumpSumInvoiceDto.getInvoiceDesc(), null, null, lumpSumInvoiceDto.getInvoiceCategory(), lumpSumInvoiceDto.getInvoiceMode(), lumpSumInvoiceDto.getPartInvoiceAmount());
    }

    @PostMapping("/update-total-invoice")
    @ApiOperation(value = "服务商根据总包开票id修改总包发票", notes = "服务商根据总包开票id修改总包发票")
    public R updateTotalInvoice(@Valid @RequestBody LumpInvoiceDTO lumpInvoiceDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.updateTotalInvoice(lumpInvoiceDTO);
    }


    @GetMapping("/query-all-sub-list")
    @ApiOperation(value = "根据服务商查询分包列表", notes = "根据服务商查询分包列表")
    public R queryAllOpenSubList(@RequestParam(required = false) String enterprise_name,
                                 @RequestParam CompanyInvoiceState companyInvoiceState, Query query, BladeUser bladeUser,
                                 @ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.findServiceSubcontractSummary(serviceProviderId, enterprise_name, companyInvoiceState, Condition.getPage(query.setDescs("pe.create_time")));
    }

    @GetMapping("/query-all-sub-detail")
    @ApiOperation(value = "服务商根据总包支付清单查询分包详情", notes = "服务商根据总包支付清单查询分包详情")
    public R queryAllOpenSubDetail(String payEnterpriseIds, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.findServiceDetailSummary(payEnterpriseIds);
    }

    @PostMapping("/create-summary-agency-invoice")
    @ApiOperation(value = "服务商汇总代开发票", notes = "服务商汇总代开发票")
    public R createSummaryAgencyInvoice(@Valid @RequestBody SummaryInvoiceDTO summaryInvoiceDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.createSummaryAgencyInvoice(summaryInvoiceDTO);
    }


    @PostMapping("/create-door-sign-invoice")
    @ApiOperation(value = "服务商门征发票", notes = "服务商门征发票")
    public R createDoorSignInvoice(@Valid @RequestBody DoorSignInvoiceDTO doorSignInvoiceDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.createDoorSignInvoice(doorSignInvoiceDTO);
    }

    @GetMapping("query-worksheet-detail")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R queryWorksheetDetail(@NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getWorksheetWebDetails(Condition.getPage(query.setDescs("wm.create_time")), worksheetId);
    }

    @GetMapping("/query-pay-enterprise-detail")
    @ApiOperation(value = "查询总包支付清单详情", notes = "查询总包支付清单详情")
    public R queryPayEnterpriseDetail(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseDetail(payEnterpriseId);
    }

    @GetMapping("/query-pay-enterprise-maker-list")
    @ApiOperation(value = "根据支付清单查询支付明细", notes = "根据支付清单查询支付明细")
    public R queryPayEnterpriseMakerList(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseMakerList(payEnterpriseId);
    }

}
