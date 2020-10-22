package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.admin.ToExamineSelfHelpInvoiceDTO;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 平台端---自助开票管理模块相关接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@RestController
@RequestMapping("/admin/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "平台端---自助开票管理模块相关接口", tags = "平台端---自助开票管理模块相关接口")
public class SelfHelpInvoiceAdminController {

    private IUserClient userClient;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-self-help-invoice-list")
    @ApiOperation(value = "查询自助开票", notes = "查询自助开票")
    public R querySelfHelpInvoiceList(@ApiParam(value = "商户") @RequestParam(required = false) String enterpriseName, @ApiParam(value = "开始时间") @RequestParam(required = false) String startTime,
                                      @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime, @ApiParam(value = "创客身份") MakerType makerType, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getAdminMakerTypeSelfHelpInvoice(enterpriseName, startTime, endTime, makerType, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-self-help-invoice-detail")
    @ApiOperation(value = "查询自助开票详情", notes = "查询自助开票详情")
    public R querySelfHelpInvoiceDetail(Long selfHelpInvoiceId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getMakerTypeSelfHelpInvoiceDetails(selfHelpInvoiceId);
    }

    @PostMapping("/audit-self-help-invoice")
    @ApiOperation(value = "审核自助开票", notes = "审核自助开票")
    public R auditSelfHelpInvoice(@Valid @RequestBody ToExamineSelfHelpInvoiceDTO toExamineSelfHelpInvoiceDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.toExamineSelfHelpInvoice(toExamineSelfHelpInvoiceDto);
    }

    @PostMapping("/upload-pay-receipt-and-match-service-provider")
    @ApiOperation(value = "上传支付回单和匹配服务商", notes = "上传支付回单和匹配服务商")
    public R uploadPayReceiptAndMatchServiceProvider(Long selfHelpInvoiceId, Long selfHelpInvoiceFeeId, Long serviceProviderId, String payCertificate, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.matchServiceProvider(selfHelpInvoiceId, selfHelpInvoiceFeeId, serviceProviderId, payCertificate);
    }

    @PostMapping("/upload-express")
    @ApiOperation(value = "上传快递", notes = "上传快递")
    public R uploadExpress(Long selfHelpInvoiceId, Long serviceProviderId, String expressNo, String expressCompanyName, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.uploadAdminExpress(selfHelpInvoiceId, serviceProviderId, expressNo, expressCompanyName);
    }

    @PostMapping("/upload-invoice")
    @ApiOperation(value = "上传发票", notes = "上传发票")
    public R uploadInvoice(Long selfHelpInvoiceApplyProviderDetailId, String invoiceScanPictures, String taxScanPictures, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.uploadAdminInvoice(selfHelpInvoiceApplyProviderDetailId, invoiceScanPictures, taxScanPictures);
    }
}
