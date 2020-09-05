package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class ServiceInvoiceController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient iUserClient;

    @GetMapping("/getLumpSumInvoice")
    @ApiOperation(value = "服务商查询总包发票", notes = "服务商查询总包发票")
    public R getLumpSumInvoice(Query query, BladeUser bladeUser,
                               @RequestParam(required = false) String enterpriseName,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime,
                               @RequestParam InvoiceState companyInvoiceState) {
        log.info("服务商查询总包发票");
        try {
            //获取当前服务商员工
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
            //获取当前服务商员工
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
    public R saveLumpSumInvoice(BladeUser bladeUser,Long payEnterpriseId,String serviceProviderName,Long applicationId,String companyInvoiceUrl,String expressSheetNo,String expressCompanyName,String invoiceDesc) {
        log.info("服务商总包开票");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return payEnterpriseService.saveServiceLumpSumInvoice(serviceProviderWorkerEntity.getId(),payEnterpriseId,serviceProviderName,applicationId,companyInvoiceUrl,expressSheetNo,expressCompanyName,invoiceDesc);
        } catch (Exception e) {
            log.error("服务商总包开票失败",e);
        }
        return R.fail("服务商总包开票失败");
    }

    @GetMapping("/getSummaryInvoice")
    @ApiOperation(value = "服务商查询汇总代开发票", notes = "服务商查询汇总代开发票")
    public R getSummaryInvoice(Query query, BladeUser bladeUser,
                               @RequestParam(required = false) String enterpriseName,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime,
                               @RequestParam InvoiceState companyInvoiceState) {
        log.info("服务商查询汇总代开发票");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return payEnterpriseService.getServiceSummaryInvoice(serviceProviderWorkerEntity.getServiceProviderId(),enterpriseName,startTime,endTime,companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("服务商查询汇总代开发票失败",e);
        }
        return R.fail("服务商查询汇总代开发票失败");
    }
}
