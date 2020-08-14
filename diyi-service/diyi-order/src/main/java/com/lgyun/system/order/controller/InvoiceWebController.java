package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.ContractApplyInvoiceDto;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.service.IEnterpriseProviderInvoiceCatalogsService;
import com.lgyun.system.order.service.IInvoiceApplicationService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.wrapper.EnterpriseProviderInvoiceCatalogsWrapper;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商户发票和完税证明接口
 *
 * @author jun.
 * @date 2020/7/22.
 * @time 16:24.
 */
@Slf4j
@RestController
@RequestMapping("/web/invoice")
@Validated
@AllArgsConstructor
@Api(value = "商户发票和完税证明接口", tags = "商户发票和完税证明接口")
public class InvoiceWebController {

    private IPayEnterpriseService payEnterpriseService;
    private IEnterpriseProviderInvoiceCatalogsService enterpriseProviderInvoiceCatalogsService;
    private IInvoiceApplicationService invoiceApplicationService;
    private IUserClient iUserClient;

    @GetMapping("/findEnterpriseLumpSumInvoice")
    @ApiOperation(value = "根据商户查询总包发票", notes = "根据商户查询总包发票")
    public R findEnterpriseLumpSumInvoice(@RequestParam(required = false) String invoiceSerialNo,
                                          @RequestParam(required = false) String serviceProviderName,
                                          @RequestParam(required = false) String startTime,
                                          @RequestParam(required = false) String endTime,
                                          Query query, Long enterpriseId) {
        log.info("根据商户查询总包发票");
        try {
            return payEnterpriseService.findEnterpriseLumpSumInvoice(invoiceSerialNo,serviceProviderName,startTime,endTime,enterpriseId, Condition.getPage(query.setDescs("create_time")));
        }catch (Exception e){
            log.info("根据商户查询总包发票失败",e);
        }
        return R.fail("根据商户查询总包发票失败");
    }

    @PostMapping("/withdraw")
    @ApiOperation(value = "取消申请", notes = "取消申请")
    public R withdraw(@NotNull(message = "申请开票id不能为空") Long applicationId){
        log.info("取消申请");
        try {
            return  payEnterpriseService.withdraw(applicationId);
        }catch (Exception e){
            log.info("取消申请失败",e);
        }
        return R.fail("取消申请失败");
    }

    @GetMapping("/findPayEnterpriseDetails")
    @ApiOperation(value = "查看总包发票详情", notes = "查看总包发票详情")
    public R findPayEnterpriseDetails(@NotNull(message = "支付清单Id不能为空") Long payEnterpriseId){
        log.info("查看总包发票详情");
        try {
            return payEnterpriseService.findPayEnterpriseDetails(payEnterpriseId);
        }catch (Exception e){
            log.info("查看总包发票详情失败",e);
        }
        return R.fail("查看总包发票详情失败");
    }

    @GetMapping("/findEnterprisePaymentList")
    @ApiOperation(value = "根据商户查询支付清单", notes = "根据商户查询支付清单")
    public R findEnterprisePaymentList(Long enterpriseId,@RequestParam(required = false) String serviceProviderName,Query query){
        log.info("根据商户查询支付清单");
        try {
            return payEnterpriseService.findEnterprisePaymentList(enterpriseId,serviceProviderName,Condition.getPage(query.setDescs("create_time")));
        }catch (Exception e){
            log.info("根据商户查询支付清单失败",e);
        }
        return R.fail("根据商户查询支付清单失败");
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(Query query) {
        IPage<EnterpriseProviderInvoiceCatalogsEntity> pages = enterpriseProviderInvoiceCatalogsService.page(Condition.getPage(query.setDescs("create_time")));
        return R.data(EnterpriseProviderInvoiceCatalogsWrapper.build().pageVO(pages));
    }

    @PostMapping("/contractApplyInvoice")
    @ApiOperation(value = "查看总包发票详情", notes = "查看总包发票详情")
    public R contractApplyInvoice(@Valid @RequestBody ContractApplyInvoiceDto contractApplyInvoiceDto,BladeUser bladeUser){
        log.info("查看总包发票详情");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return invoiceApplicationService.contractApplyInvoice(contractApplyInvoiceDto, enterpriseWorkerEntity.getEnterpriseId());
        }catch (Exception e){
            log.info("查看总包发票详情失败",e);
        }
        return R.fail("查看总包发票详情失败");
    }

    /**
     * 根据商户查询分包列表-汇总
     */
    @GetMapping("/findEnterpriseSubcontractSummary")
    @ApiOperation(value = "根据商户查询分包列表-汇总", notes = "根据商户查询分包列表-汇总")
    public R findEnterpriseSubcontractSummary(Long enterpriseId,@RequestParam(required = false) String serviceProviderName,Query query){
        log.info("根据商户查询分包列表-汇总");
        try {
            return payEnterpriseService.findEnterpriseSubcontractSummary(enterpriseId,serviceProviderName,Condition.getPage(query.setDescs("create_time")));
        }catch (Exception e){
            log.info("根据商户查询分包列表-汇总失败",e);
        }
        return R.fail("根据商户查询分包列表-汇总失败");
    }


    /**
     * 查询详情接口-汇总
     */
    @GetMapping("/findDetailSummary")
    @ApiOperation(value = "查询详情接口-汇总", notes = "查询详情接口-汇总")
    public R findDetailSummary(Long makerTotalInvoiceId){
        log.info("查询详情接口-汇总");
        try {
            return payEnterpriseService.findDetailSummary(makerTotalInvoiceId);
        }catch (Exception e){
            log.info("查询详情接口-汇总失败",e);
        }
        return R.fail("查询详情接口-汇总失败");
    }


    /**
     * 根据商户查询分包列表-门征
     */
    @GetMapping("/findEnterpriseSubcontractPortal")
    @ApiOperation(value = "根据商户查询分包列表-门征", notes = "根据商户查询分包列表-门征")
    public R findEnterpriseSubcontractPortal(Long enterpriseId,@RequestParam(required = false) String serviceProviderName,Query query){
        log.info("根据商户查询分包列表-门征");
        try {
            return payEnterpriseService.findEnterpriseSubcontractPortal(enterpriseId,serviceProviderName,Condition.getPage(query.setDescs("create_time")));
        }catch (Exception e){
            log.info("根据商户查询分包列表-门征失败",e);
        }
        return R.fail("根据商户查询分包列表-门征失败");
    }

    /**
     * 查询详情接口-门征
     */
    @GetMapping("/findDetailSubcontractPortal")
    @ApiOperation(value = "查询详情接口-门征", notes = "查询详情接口-门征")
    public R findDetailSubcontractPortal(Long makerInvoiceId){
        log.info("查询详情接口-门征");
        try {
            return payEnterpriseService.findDetailSubcontractPortal(makerInvoiceId);
        }catch (Exception e){
            log.info("查询详情接口-门征失败",e);
        }
        return R.fail("查询详情接口-门征失败");
    }
}
