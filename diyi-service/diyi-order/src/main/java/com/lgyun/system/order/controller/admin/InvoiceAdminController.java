package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * (平台)发票和完税证明接口
 *
 * @author jun.
 * @date 2020/7/22.
 * @time 16:24.
 */
@Slf4j
@RestController
@RequestMapping("/invoice/admin")
@Validated
@AllArgsConstructor
@Api(value = "(平台)发票和完税证明接口", tags = "(平台)发票和完税证明接口")
public class InvoiceAdminController {

    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private IUserClient iUserClient;

    @GetMapping("/getAdminLumpSumInvoice")
    @ApiOperation(value = "平台查询查询总包发票", notes = "平台查询查询总包发票")
    public R getAdminLumpSumInvoice(Query query, BladeUser bladeUser,
                               @RequestParam(required = false) String enterpriseName,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime,
                               @RequestParam InvoiceState companyInvoiceState) {
        log.info("平台查询查询总包发票");
        try {
            return payEnterpriseService.getServiceLumpSumInvoice(null,enterpriseName,startTime,endTime,companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("平台查询查询总包发票失败",e);
        }
        return R.fail("平台查询查询总包发票失败");
    }

    /**
     * 平台查询总包发票详情
     */
    @GetMapping("/getAdminLumpSumInvoiceDetails")
    @ApiOperation(value = "平台查询总包发票详情", notes = "平台查询总包发票详情")
    public R getAdminLumpSumInvoiceDetails(BladeUser bladeUser,Long payEnterpriseId) {
        log.info("平台查询总包发票详情");
        try {
            return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("平台查询总包发票详情失败",e);
        }
        return R.fail("平台查询总包发票详情失败");
    }

    /**
     * 平台总包开票
     */
    @PostMapping("/saveAdminLumpSumInvoice")
    @ApiOperation(value = "平台总包开票", notes = "平台总包开票")
    public R saveAdminLumpSumInvoice(BladeUser bladeUser,Long payEnterpriseId,Long applicationId,String companyInvoiceUrl,String expressSheetNo,String expressCompanyName,String invoiceDesc) {
        log.info("平台总包开票");
        try {
            return payEnterpriseService.saveServiceLumpSumInvoice(null,payEnterpriseId,null,applicationId,companyInvoiceUrl,expressSheetNo,expressCompanyName,invoiceDesc);
        } catch (Exception e) {
            log.error("平台总包开票失败",e);
        }
        return R.fail("平台总包开票失败");
    }


    @GetMapping("/getSubcontractInvoice")
    @ApiOperation(value = "平台查询未开票分包发票", notes = "平台查询未开票分包发票")
    public R getSubcontractInvoice(Query query, BladeUser bladeUser,
                                   @RequestParam(required = false) String enterpriseName,
                                   @RequestParam(required = false) String startTime,
                                   @RequestParam(required = false) String endTime) {
        log.info("平台查询未开票分包发票");
        try {
            return payEnterpriseService.getSubcontractInvoice(null,enterpriseName,startTime,endTime, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("平台查询未开票分包发票失败",e);
        }
        return R.fail("平台查询未开票分包发票失败");
    }


    @GetMapping("/getSubcontractInvoiceDetails")
    @ApiOperation(value = "平台查看未开票分包发票详情", notes = "平台查看未开票分包发票详情")
    public R getSubcontractInvoice(@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        log.info("平台查看未开票分包发票详情");
        try {
            return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("平台查看未开票分包发票详情失败",e);
        }
        return R.fail("平台查看未开票分包发票详情失败");
    }

    @GetMapping("/applySummaryInvoice")
    @ApiOperation(value = "平台申请汇总代开发票", notes = "平台申请汇总代开发票")
    public R applySummaryInvoice(@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        log.info("平台申请汇总代开发票");
        try {
            return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("平台申请汇总代开发票失败",e);
        }
        return R.fail("平台申请汇总代开发票失败");
    }

    /**
     * 平台汇总代开开票
     */
    @PostMapping("/saveSummaryInvoice")
    @ApiOperation(value = "平台汇总代开开票", notes = "平台汇总代开开票")
    public R saveSummaryInvoice(BladeUser bladeUser,
                                @ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId,
                                @ApiParam(value = "发票代码") @NotBlank(message = "请输入发票代码") String invoiceTypeNo,
                                @ApiParam(value = "发票号码") @NotBlank(message = "请输入发票号码") String invoiceSerialNo,
                                @ApiParam(value = "货物或应税劳务、服务名称") @NotBlank(message = "请输入货物或应税劳务、服务名称") String invoiceCategory,
                                @ApiParam(value = "汇总代开发票URL") @NotBlank(message = "请输入汇总代开发票URL") String companyInvoiceUrl,
                                @ApiParam(value = "总完税证明URL") @NotBlank(message = "请输入总完税证明URL") String makerTaxUrl,
                                @ApiParam(value = "清单式完税凭证URL") @NotBlank(message = "请输入清单式完税凭证URL") String makerTaxListUrl) {
        log.info("平台总包开票");
        try {
            return payEnterpriseService.saveSummaryInvoice(null,payEnterpriseId,null,invoiceTypeNo,invoiceSerialNo,invoiceCategory,companyInvoiceUrl,makerTaxUrl,makerTaxListUrl);
        } catch (Exception e) {
            log.error("平台汇总代开开票失败",e);
        }
        return R.fail("平台汇总代开开票失败");
    }

    @GetMapping("/applyPortalSignInvoice")
    @ApiOperation(value = "平台申请门征单开发票", notes = "平台申请门征单开发票")
    public R applyPortalSignInvoice(BladeUser bladeUser,@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId) {
        log.info("平台申请门征单开发票");
        try {
            return payEnterpriseService.applyPortalSignInvoice(payEnterpriseId);
        } catch (Exception e) {
            log.error("平台申请门征单开发票失败",e);
        }
        return R.fail("平台申请门征单开发票失败");
    }

    @PostMapping("/savePortalSignInvoice")
    @ApiOperation(value = "平台门征单开发票开票", notes = "平台门征单开发票开票")
    public R savePortalSignInvoice(BladeUser bladeUser,
                                   @ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId,
                                   @ApiParam(value = "创客支付明细json") @NotBlank(message = "请输入创客支付明细json") @RequestParam(required = false) String payMakers) {
        log.info("平台门征单开发票开票");
        try {
            return payEnterpriseService.savePortalSignInvoice(null,payEnterpriseId,payMakers,null);
        } catch (Exception e) {
            log.error("平台门征单开发票开票失败",e);
        }
        return R.fail("平台门征单开发票开票失败");
    }

    @GetMapping("/getSummaryInvoice")
    @ApiOperation(value = "平台查询已汇总代开的发票", notes = "平台查询已汇总代开的发票")
    public R getSummaryInvoice(BladeUser bladeUser,Query query,
                               @RequestParam(required = false) String enterpriseName,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime) {
        log.info("平台申请门征单开发票");
        try {
            return payEnterpriseService.getServiceSummaryInvoice(null,enterpriseName,startTime,endTime,Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("平台申请门征单开发票失败",e);
        }
        return R.fail("平台申请门征单开发票失败");
    }

    @GetMapping("/getSummaryInvoiceDetails")
    @ApiOperation(value = "平台查询已汇总代开的发票详情", notes = "平台查询已汇总代开的发票详情")
    public R getSummaryInvoiceDetails(BladeUser bladeUser,@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId) {
        log.info("平台查询已汇总代开的发票详情");
        try {
            return payEnterpriseService.getSummaryInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("平台查询已汇总代开的发票详情失败",e);
        }
        return R.fail("平台查询已汇总代开的发票详情失败");
    }

    @GetMapping("/getPortalSignInvoice")
    @ApiOperation(value = "平台查询已门征单开的发票", notes = "平台查询已门征单开的发票")
    public R getPortalSignInvoice(BladeUser bladeUser,Query query,
                                  @RequestParam(required = false) String enterpriseName,
                                  @RequestParam(required = false) String startTime,
                                  @RequestParam(required = false) String endTime) {
        log.info("平台查询已门征单开的发票");
        try {
            return payEnterpriseService.getServicePortalSignInvoice(null,enterpriseName,startTime,endTime,Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("平台查询已门征单开的发票失败",e);
        }
        return R.fail("平台查询已门征单开的发票失败");
    }


    @GetMapping("/getPortalSignInvoiceDetails")
    @ApiOperation(value = "平台查询已门征单开的发票详情", notes = "平台查询已门征单开的发票详情")
    public R getPortalSignInvoiceDetails(BladeUser bladeUser,@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId) {
        log.info("平台查询已门征单开的发票详情");
        try {
            return payEnterpriseService.getServicePortalSignInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("平台查询已门征单开的发票详情失败",e);
        }
        return R.fail("平台查询已门征单开的发票详情失败");
    }


    @GetMapping("/getServiceCrowdSour")
    @ApiOperation(value = "平台查询众包发票", notes = "平台查询众包发票")
    public R getServiceCrowdSour(BladeUser bladeUser, Query query,
                                 @RequestParam(required = false) String enterpriseName,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime,
                                 SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState) {
        log.info("平台查询众包发票");
        try {
            return selfHelpInvoiceService.getServiceCrowdSour(null,enterpriseName,startTime,endTime,selfHelpInvoiceSpApplyState,Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("平台查询众包发票失败",e);
        }
        return R.fail("平台查询众包发票失败");
    }

    @GetMapping("/getServiceCrowdSourDetails")
    @ApiOperation(value = "平台查询众包发票详情", notes = "平台查询众包发票详情")
    public R getServiceCrowdSourDetails(BladeUser bladeUser,Long providerSelfHelpInvoiceId) {
        log.info("平台查询众包发票");
        try {
            return selfHelpInvoiceService.getServiceCrowdSourDetails(providerSelfHelpInvoiceId);
        } catch (Exception e) {
            log.error("平台查询众包发票详情失败",e);
        }
        return R.fail("平台查询众包发票详情失败");
    }

    @PostMapping("/saveServiceCrowdSour")
    @ApiOperation(value = "平台众包发票开票", notes = "平台众包发票开票")
    public R savePortalSignInvoice(BladeUser bladeUser,Long providerSelfHelpInvoiceId,String expressNo,String expressCompanyName,String invoiceScanPictures,String taxScanPictures) {
        log.info("平台众包发票开票");
        try {
            return selfHelpInvoiceService.savePortalSignInvoice(null,providerSelfHelpInvoiceId,expressNo,expressCompanyName,invoiceScanPictures,taxScanPictures);
        } catch (Exception e) {
            log.error("平台众包发票开票失败",e);
        }
        return R.fail("平台众包发票开票失败");
    }
}
