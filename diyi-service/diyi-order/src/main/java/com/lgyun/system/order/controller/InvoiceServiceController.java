package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.LumpSumInvoiceDto;
import com.lgyun.system.order.dto.ReleaseWorksheetDto;
import com.lgyun.system.order.dto.SummaryInvoiceDto;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.MakerEntity;
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
 * (服务商)发票和完税证明接口
 *
 * @author jun.
 * @date 2020/7/22.
 * @time 16:24.
 */
@Slf4j
@RestController
@RequestMapping("/invoice/service")
@Validated
@AllArgsConstructor
@Api(value = "(服务商)发票和完税证明接口", tags = "(服务商)发票和完税证明接口")
public class InvoiceServiceController {

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
        log.info("服务商查询总包发票");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return payEnterpriseService.getServiceLumpSumInvoice(serviceProviderWorkerEntity.getServiceProviderId(),enterpriseName,startTime,endTime,companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("服务商查询总包发票失败",e);
        }
        return R.fail("服务商查询总包发票失败");
    }

    /**
     * 服务商查询总包发票详情
     */
    @GetMapping("/getLumpSumInvoiceDetails")
    @ApiOperation(value = "服务商查询总包发票详情", notes = "服务商查询总包发票详情")
    public R getLumpSumInvoiceDetails(BladeUser bladeUser,Long payEnterpriseId) {
        log.info("服务商查询总包发票详情");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            return payEnterpriseService.getServiceLumpSumInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("服务商查询总包发票详情失败",e);
        }
        return R.fail("服务商查询总包发票详情失败");
    }

    /**
     * 服务商总包开票
     */
    @PostMapping("/saveLumpSumInvoice")
    @ApiOperation(value = "服务商总包开票", notes = "服务商总包开票")
    public R saveLumpSumInvoice(BladeUser bladeUser, LumpSumInvoiceDto lumpSumInvoiceDto) {
        log.info("服务商总包开票");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return payEnterpriseService.saveServiceLumpSumInvoice(serviceProviderWorkerEntity.getId(),lumpSumInvoiceDto.getPayEnterpriseId(),lumpSumInvoiceDto.getServiceProviderName(),lumpSumInvoiceDto.getApplicationId(),lumpSumInvoiceDto.getCompanyInvoiceUrl(),lumpSumInvoiceDto.getExpressSheetNo(),lumpSumInvoiceDto.getExpressCompanyName(),lumpSumInvoiceDto.getInvoiceDesc());
        } catch (Exception e) {
            log.error("服务商总包开票失败",e);
        }
        return R.fail("服务商总包开票失败");
    }


    @GetMapping("/getSubcontractInvoice")
    @ApiOperation(value = "服务商查询未开票分包发票", notes = "服务商查询未开票分包发票")
    public R getSubcontractInvoice(Query query, BladeUser bladeUser,
                               @RequestParam(required = false) String enterpriseName,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime) {
        log.info("服务商查询未开票分包发票");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return payEnterpriseService.getSubcontractInvoice(serviceProviderWorkerEntity.getServiceProviderId(),enterpriseName,startTime,endTime, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("服务商查询未开票分包发票失败",e);
        }
        return R.fail("服务商查询未开票分包发票失败");
    }


    @GetMapping("/getSubcontractInvoiceDetails")
    @ApiOperation(value = "服务商查看未开票分包发票详情", notes = "服务商查看未开票分包发票详情")
    public R getSubcontractInvoice(@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        log.info("服务商查看未开票分包发票详情");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("服务商查看未开票分包发票详情失败",e);
        }
        return R.fail("服务商查看未开票分包发票详情失败");
    }

    @GetMapping("/applySummaryInvoice")
    @ApiOperation(value = "服务商申请汇总代开发票", notes = "服务商申请汇总代开发票")
    public R applySummaryInvoice(@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        log.info("服务商申请汇总代开发票");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            return payEnterpriseService.getSubcontractInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("服务商申请汇总代开发票失败",e);
        }
        return R.fail("服务商申请汇总代开发票失败");
    }

    /**
     * 服务商汇总代开开票
     */
    @PostMapping("/saveSummaryInvoice")
    @ApiOperation(value = "服务商汇总代开开票", notes = "服务商汇总代开开票")
    public R saveSummaryInvoice(BladeUser bladeUser, SummaryInvoiceDto summaryInvoiceDto) {
        log.info("服务商总包开票");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return payEnterpriseService.saveSummaryInvoice(serviceProviderWorkerEntity.getId(),summaryInvoiceDto.getPayEnterpriseId(),summaryInvoiceDto.getServiceProviderName(),summaryInvoiceDto.getInvoiceTypeNo(),summaryInvoiceDto.getInvoiceSerialNo(),summaryInvoiceDto.getInvoiceCategory(),summaryInvoiceDto.getCompanyInvoiceUrl(),summaryInvoiceDto.getMakerTaxUrl(),summaryInvoiceDto.getMakerTaxListUrl());
        } catch (Exception e) {
            log.error("服务商汇总代开开票失败",e);
        }
        return R.fail("服务商汇总代开开票失败");
    }

    @GetMapping("/applyPortalSignInvoice")
    @ApiOperation(value = "服务商申请门征单开发票", notes = "服务商申请门征单开发票")
    public R applyPortalSignInvoice(BladeUser bladeUser,@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId) {
        log.info("服务商申请门征单开发票");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            return payEnterpriseService.applyPortalSignInvoice(payEnterpriseId);
        } catch (Exception e) {
            log.error("服务商申请门征单开发票失败",e);
        }
        return R.fail("服务商申请门征单开发票失败");
    }

    @PostMapping("/savePortalSignInvoice")
    @ApiOperation(value = "服务商门征单开发票开票", notes = "服务商门征单开发票开票")
    public R savePortalSignInvoice(BladeUser bladeUser, ReleaseWorksheetDto releaseWorksheetDto) {
        log.info("服务商门征单开发票开票");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return payEnterpriseService.savePortalSignInvoice(serviceProviderWorkerEntity.getId(),releaseWorksheetDto.getEnterpriseId(),releaseWorksheetDto.getMakerIds(),releaseWorksheetDto.getWorksheetName());
        } catch (Exception e) {
            log.error("服务商门征单开发票开票失败",e);
        }
        return R.fail("服务商门征单开发票开票失败");
    }

    @GetMapping("/getSummaryInvoice")
    @ApiOperation(value = "服务商查询已汇总代开的发票", notes = "服务商查询已汇总代开的发票")
    public R getSummaryInvoice(BladeUser bladeUser,Query query,
                               @RequestParam(required = false) String enterpriseName,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime) {
        log.info("服务商申请门征单开发票");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return payEnterpriseService.getServiceSummaryInvoice(serviceProviderWorkerEntity.getServiceProviderId(),enterpriseName,startTime,endTime,Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("服务商申请门征单开发票失败",e);
        }
        return R.fail("服务商申请门征单开发票失败");
    }

    @GetMapping("/getSummaryInvoiceDetails")
    @ApiOperation(value = "服务商查询已汇总代开的发票详情", notes = "服务商查询已汇总代开的发票详情")
    public R getSummaryInvoiceDetails(BladeUser bladeUser,@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId) {
        log.info("服务商查询已汇总代开的发票详情");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            return payEnterpriseService.getSummaryInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("服务商查询已汇总代开的发票详情失败",e);
        }
        return R.fail("服务商查询已汇总代开的发票详情失败");
    }

    @GetMapping("/getPortalSignInvoice")
    @ApiOperation(value = "服务商查询已门征单开的发票", notes = "服务商查询已门征单开的发票")
    public R getPortalSignInvoice(BladeUser bladeUser,Query query,
                                      @RequestParam(required = false) String enterpriseName,
                                      @RequestParam(required = false) String startTime,
                                      @RequestParam(required = false) String endTime) {
        log.info("服务商查询已门征单开的发票");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return payEnterpriseService.getServicePortalSignInvoice(serviceProviderWorkerEntity.getServiceProviderId(),enterpriseName,startTime,endTime,Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("服务商查询已门征单开的发票失败",e);
        }
        return R.fail("服务商查询已门征单开的发票失败");
    }


    @GetMapping("/getPortalSignInvoiceDetails")
    @ApiOperation(value = "服务商查询已门征单开的发票详情", notes = "服务商查询已门征单开的发票详情")
    public R getPortalSignInvoiceDetails(BladeUser bladeUser,@ApiParam(value = "商户支付清单Id") @NotNull(message = "请输入商户支付清单Id") @RequestParam(required = false) Long payEnterpriseId) {
        log.info("服务商查询已门征单开的发票详情");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            return payEnterpriseService.getServicePortalSignInvoiceDetails(payEnterpriseId);
        } catch (Exception e) {
            log.error("服务商查询已门征单开的发票详情失败",e);
        }
        return R.fail("服务商查询已门征单开的发票详情失败");
    }


    @GetMapping("/getServiceCrowdSour")
    @ApiOperation(value = "服务商查询众包发票", notes = "服务商查询众包发票")
    public R getServiceCrowdSour(BladeUser bladeUser, Query query,
                                 @RequestParam(required = false) String enterpriseName,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime,
                                 SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState) {
        log.info("服务商查询众包发票");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return selfHelpInvoiceService.getServiceCrowdSour(serviceProviderWorkerEntity.getServiceProviderId(),enterpriseName,startTime,endTime,selfHelpInvoiceSpApplyState,Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("服务商查询众包发票失败",e);
        }
        return R.fail("服务商查询众包发票失败");
    }

    @GetMapping("/getServiceCrowdSourDetails")
    @ApiOperation(value = "服务商查询众包发票详情", notes = "服务商查询众包发票详情")
    public R getServiceCrowdSourDetails(BladeUser bladeUser,Long selfHelpInvoiceApplyProviderId) {
        log.info("服务商查询众包发票");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            return selfHelpInvoiceService.getServiceCrowdSourDetails(selfHelpInvoiceApplyProviderId);
        } catch (Exception e) {
            log.error("服务商查询众包发票详情失败",e);
        }
        return R.fail("服务商查询众包发票详情失败");
    }

    @PostMapping("/saveServiceCrowdSour")
    @ApiOperation(value = "服务商众包发票开票", notes = "服务商众包发票开票")
    public R savePortalSignInvoice(BladeUser bladeUser,Long providerSelfHelpInvoiceId,String expressNo,String expressCompanyName,String invoiceScanPictures,String taxScanPictures) {
        log.info("服务商众包发票开票");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return selfHelpInvoiceService.savePortalSignInvoice(serviceProviderWorkerEntity.getId(),providerSelfHelpInvoiceId,expressNo,expressCompanyName,invoiceScanPictures,taxScanPictures);
        } catch (Exception e) {
            log.error("服务商众包发票开票失败",e);
        }
        return R.fail("服务商众包发票开票失败");
    }
}
