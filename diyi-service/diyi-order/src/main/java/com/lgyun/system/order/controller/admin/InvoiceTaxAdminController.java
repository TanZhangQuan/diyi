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

/**
 * 平台端---发票/完税证明管理模块相关接口
 *
 * @author tzq
 * @date 2020/7/22.
 * @time 16:24.
 */
@RestController
@RequestMapping("/admin/invoice-tax")
@Validated
@AllArgsConstructor
@Api(value = "平台端---发票/完税证明管理模块相关接口", tags = "平台端---发票/完税证明管理模块相关接口")
public class InvoiceTaxAdminController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/getAdminLumpSumInvoice")
    @ApiOperation(value = "平台查询查询总包发票", notes = "平台查询查询总包发票")
    public R getAdminLumpSumInvoice(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                    @RequestParam(required = false) String endTime, @RequestParam InvoiceState companyInvoiceState, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServiceLumpSumInvoice(null, enterpriseName, startTime, endTime, companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/getAdminLumpSumInvoiceDetails")
    @ApiOperation(value = "平台查询总包发票详情", notes = "平台查询总包发票详情")
    public R getAdminLumpSumInvoiceDetails(Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
    }

    @PostMapping("/saveAdminLumpSumInvoice")
    @ApiOperation(value = "平台总包开票", notes = "平台总包开票")
    public R saveAdminLumpSumInvoice(Long payEnterpriseId, Long applicationId, String companyInvoiceUrl, String expressSheetNo, String expressCompanyName,
                                     String invoiceDesc, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.saveServiceLumpSumInvoice(null, payEnterpriseId, null, applicationId, companyInvoiceUrl, expressSheetNo, expressCompanyName, invoiceDesc);
    }


    @GetMapping("/getSubcontractInvoice")
    @ApiOperation(value = "平台查询未开票分包发票", notes = "平台查询未开票分包发票")
    public R getSubcontractInvoice(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                   @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoice(null, enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/getSubcontractInvoiceDetails")
    @ApiOperation(value = "平台查看未开票分包发票详情", notes = "平台查看未开票分包发票详情")
    public R getSubcontractInvoice(@ApiParam(value = "商户支付清单ID", required = true) @NotNull(message = "请输入商户支付清单编号") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/applySummaryInvoice")
    @ApiOperation(value = "平台申请汇总代开发票", notes = "平台申请汇总代开发票")
    public R applySummaryInvoice(@ApiParam(value = "商户支付清单Id", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
    }

    @PostMapping("/saveSummaryInvoice")
    @ApiOperation(value = "平台汇总代开开票", notes = "平台汇总代开开票")
    public R saveSummaryInvoice(@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId,
                                @ApiParam(value = "发票代码") @NotBlank(message = "请输入发票代码") String invoiceTypeNo,
                                @ApiParam(value = "发票号码") @NotBlank(message = "请输入发票号码") String invoiceSerialNo,
                                @ApiParam(value = "货物或应税劳务、服务名称") @NotBlank(message = "请输入货物或应税劳务、服务名称") String invoiceCategory,
                                @ApiParam(value = "汇总代开发票URL") @NotBlank(message = "请输入汇总代开发票URL") String companyInvoiceUrl,
                                @ApiParam(value = "总完税证明URL") @NotBlank(message = "请输入总完税证明URL") String makerTaxUrl,
                                @ApiParam(value = "清单式完税凭证URL") @NotBlank(message = "请输入清单式完税凭证URL") String makerTaxListUrl, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.saveSummaryInvoice(null, payEnterpriseId, null, invoiceTypeNo, invoiceSerialNo, invoiceCategory, companyInvoiceUrl, makerTaxUrl, makerTaxListUrl);
    }

    @GetMapping("/applyPortalSignInvoice")
    @ApiOperation(value = "平台申请门征单开发票", notes = "平台申请门征单开发票")
    public R applyPortalSignInvoice(@ApiParam(value = "商户支付清单Id", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.applyPortalSignInvoice(payEnterpriseId);
    }

    @PostMapping("/savePortalSignInvoice")
    @ApiOperation(value = "平台门征单开发票开票", notes = "平台门征单开发票开票")
    public R savePortalSignInvoice(@ApiParam(value = "商户支付清单Id", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId,
                                   @ApiParam(value = "创客支付明细json", required = true) @NotBlank(message = "请输入创客支付明细json") @RequestParam(required = false) String payMakers, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.savePortalSignInvoice(null, payEnterpriseId, payMakers, null);
    }

    @GetMapping("/getSummaryInvoice")
    @ApiOperation(value = "平台查询已汇总代开的发票", notes = "平台查询已汇总代开的发票")
    public R getSummaryInvoice(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServiceSummaryInvoice(null, enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/getSummaryInvoiceDetails")
    @ApiOperation(value = "平台查询已汇总代开的发票详情", notes = "平台查询已汇总代开的发票详情")
    public R getSummaryInvoiceDetails(@ApiParam(value = "商户支付清单ID", required = true) @NotNull(message = "请输入商户支付清单编号") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getSummaryInvoiceDetails(payEnterpriseId);
    }

    @GetMapping("/getPortalSignInvoice")
    @ApiOperation(value = "平台查询已门征单开的发票", notes = "平台查询已门征单开的发票")
    public R getPortalSignInvoice(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime,
                                  @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServicePortalSignInvoice(null, enterpriseName, startTime, endTime, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/getPortalSignInvoiceDetails")
    @ApiOperation(value = "平台查询已门征单开的发票详情", notes = "平台查询已门征单开的发票详情")
    public R getPortalSignInvoiceDetails(@ApiParam(value = "商户支付清单ID", required = true) @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getServicePortalSignInvoiceDetails(payEnterpriseId);
    }


    @GetMapping("/getServiceCrowdSour")
    @ApiOperation(value = "平台查询众包发票", notes = "平台查询众包发票")
    public R getServiceCrowdSour(@RequestParam(required = false) String enterpriseName, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
                                 SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getServiceCrowdSour(null, enterpriseName, startTime, endTime, selfHelpInvoiceSpApplyState, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/getServiceCrowdSourDetails")
    @ApiOperation(value = "平台查询众包发票详情", notes = "平台查询众包发票详情")
    public R getServiceCrowdSourDetails(Long providerSelfHelpInvoiceId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getServiceCrowdSourDetails(providerSelfHelpInvoiceId);
    }

    @PostMapping("/saveServiceCrowdSour")
    @ApiOperation(value = "平台众包发票开票", notes = "平台众包发票开票")
    public R savePortalSignInvoice(Long providerSelfHelpInvoiceId, String expressNo, String expressCompanyName, String invoiceScanPictures, String taxScanPictures, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.savePortalSignInvoice(null, providerSelfHelpInvoiceId, expressNo, expressCompanyName, invoiceScanPictures, taxScanPictures);
    }
}
