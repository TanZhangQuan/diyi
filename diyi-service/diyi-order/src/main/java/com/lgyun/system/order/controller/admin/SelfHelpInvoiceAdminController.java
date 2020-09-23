package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.admin.ToExamineSelfHelpInvoiceDto;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
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
@RequestMapping("/service/selfhelpinvoice")
@Validated
@AllArgsConstructor
@Api(value = "平台端---自助开票管理模块相关接口", tags = "平台端---自助开票管理模块相关接口")
public class SelfHelpInvoiceAdminController {

    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/getMakerTypeSelfHelpInvoice")
    @ApiOperation(value = "平台跟据创客身份查询自助开票", notes = "平台跟据创客身份查询自助开票")
    public R getMakerTypeSelfHelpInvoice(@ApiParam(value = "商户") @RequestParam(required = false) String enterpriseName,
                                         @ApiParam(value = "开始时间") @RequestParam(required = false) String startTime,
                                         @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
                                         @ApiParam(value = "创客身份") MakerType makerType,
                                         Query query) {

        return selfHelpInvoiceService.getAdminMakerTypeSelfHelpInvoice(enterpriseName, startTime, endTime, makerType, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/getMakerTypeSelfHelpInvoiceDetails")
    @ApiOperation(value = "平台跟据创客身份查询自助开票详情", notes = "平台跟据创客身份查询自助开票详情")
    public R getMakerTypeSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
        return selfHelpInvoiceService.getMakerTypeSelfHelpInvoiceDetails(selfHelpInvoiceId);
    }

    @PostMapping("/toExamineSelfHelpInvoice")
    @ApiOperation(value = "平台审核自助开票", notes = "平台审核自助开票")
    public R toExamineSelfHelpInvoice(@Valid @RequestBody ToExamineSelfHelpInvoiceDto toExamineSelfHelpInvoiceDto) {
        return selfHelpInvoiceService.toExamineSelfHelpInvoice(toExamineSelfHelpInvoiceDto);
    }

    @PostMapping("/matchServiceProvider")
    @ApiOperation(value = "平台上传支付回单和匹配服务商", notes = "平台上传支付回单和匹配服务商")
    public R matchServiceProvider(Long selfHelpInvoiceId, Long selfHelpInvoiceFeeId, Long serviceProviderId, String payCertificate) {
        return selfHelpInvoiceService.matchServiceProvider(selfHelpInvoiceId, selfHelpInvoiceFeeId, serviceProviderId, payCertificate);
    }

    @PostMapping("/uploadAdminExpress")
    @ApiOperation(value = "平台上传快递", notes = "平台上传快递")
    public R uploadAdminExpress(Long selfHelpInvoiceId, Long serviceProviderId, String expressNo, String expressCompanyName) {
        return selfHelpInvoiceService.uploadAdminExpress(selfHelpInvoiceId, serviceProviderId, expressNo, expressCompanyName);
    }

    @PostMapping("/uploadAdminInvoice")
    @ApiOperation(value = "平台上传发票", notes = "平台上传发票")
    public R uploadAdminInvoice(Long selfHelpInvoiceApplyProviderDetailId, String invoiceScanPictures, String taxScanPictures) {
        return selfHelpInvoiceService.uploadAdminInvoice(selfHelpInvoiceApplyProviderDetailId, invoiceScanPictures, taxScanPictures);
    }
}
