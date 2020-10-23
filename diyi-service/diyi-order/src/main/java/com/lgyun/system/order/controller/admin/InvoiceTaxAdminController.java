package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/invoice-tax")
@Validated
@AllArgsConstructor
@Api(value = "平台端---发票/完税证明管理模块相关接口", tags = "平台端---发票/完税证明管理模块相关接口")
public class InvoiceTaxAdminController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-total-invoice-list")
    @ApiOperation(value = "查询总包发票", notes = "查询总包发票")
    public R queryTotalInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                   @RequestParam(required = false) String endTime, @RequestParam InvoiceState companyInvoiceState, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServiceLumpSumInvoice(null, enterpriseName, startTime, endTime, companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-total-invoice-detail")
    @ApiOperation(value = "查询总包发票详情", notes = "查询总包发票详情")
    public R queryTotalInvoiceDetail(Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
    }

    @PostMapping("/create-total-invoice")
    @ApiOperation(value = "总包开票", notes = "总包开票")
    public R createTotalInvoice(Long payEnterpriseId, Long applicationId, String companyInvoiceUrl,
                                String expressSheetNo, String expressCompanyName,
                                String invoiceDesc, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.saveServiceLumpSumInvoice(null, payEnterpriseId, null, applicationId, companyInvoiceUrl, expressSheetNo, expressCompanyName, invoiceDesc);
    }

    @GetMapping("/query-unopen-sub-invoice-list")
    @ApiOperation(value = "查询未开票分包发票", notes = "查询未开票分包发票")
    public R queryUnopenSubInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                       @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoice(null, enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/query-unopen-sub-invoice-detail")
    @ApiOperation(value = "查看未开票分包发票详情", notes = "查看未开票分包发票详情")
    public R queryUnopenSubInvoiceDetail(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/apply-all-open-invoice")
    @ApiOperation(value = "申请汇总代开发票", notes = "申请汇总代开发票")
    public R applyAllOpenInvoice(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
    }

    @PostMapping("/create-all-open-invoice")
    @ApiOperation(value = "汇总代开开票", notes = "汇总代开开票")
    public R createAllOpenInvoice(@ApiParam(value = "商户支付清单") @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId,
                                  @ApiParam(value = "发票代码") @NotBlank(message = "请输入发票代码") String invoiceTypeNo,
                                  @ApiParam(value = "发票号码") @NotBlank(message = "请输入发票号码") String invoiceSerialNo,
                                  @ApiParam(value = "货物或应税劳务、服务名称") @NotBlank(message = "请输入货物或应税劳务、服务名称") String invoiceCategory,
                                  @ApiParam(value = "汇总代开发票URL") @NotBlank(message = "请上传汇总代开发票") String companyInvoiceUrl,
                                  @ApiParam(value = "总完税证明URL") @NotBlank(message = "请上传总完税证明") String makerTaxUrl,
                                  @ApiParam(value = "清单式完税凭证URL") @NotBlank(message = "请上传清单式完税凭证") String makerTaxListUrl, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.saveSummaryInvoice(null, payEnterpriseId, null, invoiceTypeNo, invoiceSerialNo, invoiceCategory, companyInvoiceUrl, makerTaxUrl, makerTaxListUrl);
    }

    @GetMapping("/apply-sign-open-invoice")
    @ApiOperation(value = "申请门征单开发票", notes = "申请门征单开发票")
    public R applySignOpenInvoice(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.applyPortalSignInvoice(payEnterpriseId);
    }

    @PostMapping("/create-single-open-invoice")
    @ApiOperation(value = "门征单开发票开票", notes = "门征单开发票开票")
    public R createSingleOpenInvoice(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId,
                                     @ApiParam(value = "创客支付明细", required = true) @NotBlank(message = "请输入创客支付明细") @RequestParam(required = false) String payMakers, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.savePortalSignInvoice(null, payEnterpriseId, payMakers, null);
    }

    @GetMapping("/query-all-open-invoice-list")
    @ApiOperation(value = "查询已汇总代开的发票", notes = "查询已汇总代开的发票")
    public R queryAllOpenInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                     @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServiceSummaryInvoice(null, enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-all-open-invoice-detail")
    @ApiOperation(value = "查询已汇总代开的发票详情", notes = "查询已汇总代开的发票详情")
    public R queryAllOpenInvoiceDetail(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSummaryInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/query-single-open-invoice-list")
    @ApiOperation(value = "查询已门征单开的发票", notes = "查询已门征单开的发票")
    public R querySingleOpenInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                        @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServicePortalSignInvoice(null, enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/query-single-open-invoice-detail")
    @ApiOperation(value = "查询已门征单开的发票详情", notes = "查询已门征单开的发票详情")
    public R querySingleOpenInvoiceDetail(@ApiParam(value = "商户支付清单", required = true) @NotNull(message = "请选择商户支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServicePortalSignInvoiceDetails(payEnterpriseId);
    }


    @GetMapping("/query-crowd-invoice-list")
    @ApiOperation(value = "查询众包发票", notes = "查询众包发票")
    public R queryCrowdInvoiceList(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
                                   SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getServiceCrowdSour(null, enterpriseName, startTime, endTime, selfHelpInvoiceSpApplyState, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-crowd-invoice-detail")
    @ApiOperation(value = "查询众包发票详情", notes = "查询众包发票详情")
    public R queryCrowdInvoiceDetail(Long providerSelfHelpInvoiceId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getServiceCrowdSourDetails(providerSelfHelpInvoiceId);
    }

    @PostMapping("/create-crowd-invoice")
    @ApiOperation(value = "众包发票开票", notes = "众包发票开票")
    public R createCrowdInvoice(Long providerSelfHelpInvoiceId, String expressNo, String expressCompanyName, String invoiceScanPictures, String taxScanPictures, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.savePortalSignInvoice(null, providerSelfHelpInvoiceId, expressNo, expressCompanyName, invoiceScanPictures, taxScanPictures);
    }
}
