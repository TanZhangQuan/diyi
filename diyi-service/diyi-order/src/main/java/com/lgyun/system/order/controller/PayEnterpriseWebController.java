package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.dto.PayEnterpriseMakerListDto;
import com.lgyun.system.order.dto.PayEnterpriseUploadDto;
import com.lgyun.system.order.dto.SelfHelpInvoicePayDto;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Slf4j
@RestController
@RequestMapping("/web/pay_enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户支付清单相关接口(管理端)", tags = "商户支付清单相关接口(管理端)")
public class PayEnterpriseWebController {

    private IAcceptPaysheetService acceptPaysheetService;
    private IPayEnterpriseService payEnterpriseService;
    private IPayMakerService payMakerService;
    private IUserClient iUserClient;


    @GetMapping("/get_worksheet_by_enterprise_id")
    @ApiOperation(value = "获取当前商户所有已完毕的总包+分包类型的工单", notes = "获取当前商户所有已完毕的总包+分包类型的工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "worksheetNo", value = "工单编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "worksheetName", value = "工单名称", paramType = "query", dataType = "string")
    })
    public R getWorksheetByEnterpriseId(String worksheetNo, String worksheetName, Query query, BladeUser bladeUser) {

        log.info("获取当前商户所有已完毕的总包+分包类型的工单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return payEnterpriseService.getWorksheetByEnterpriseId(query, enterpriseWorkerEntity.getEnterpriseId(), WorkSheetType.SUBPACKAGE, worksheetNo, worksheetName);
        } catch (Exception e) {
            log.error("获取当前商户所有已完毕的总包+分包类型的工单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_service_provider_by_enterprise_id")
    @ApiOperation(value = "获取当前商户关联服务商", notes = "获取当前商户关联服务商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceProviderName", value = "工单编号", paramType = "query", dataType = "string")
    })
    public R getServiceProviderByEnterpriseId(String serviceProviderName, Query query, BladeUser bladeUser) {

        log.info("获取当前商户关联服务商");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return payEnterpriseService.getServiceProviderByEnterpriseId(query, enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName);
        } catch (Exception e) {
            log.error("获取当前商户关联服务商异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传支付清单", notes = "上传支付清单")
    public R upload(@Valid @RequestBody PayEnterpriseUploadDto payEnterpriseUploadDto, BladeUser bladeUser) {

        log.info("上传支付清单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return payEnterpriseService.upload(payEnterpriseUploadDto, enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("上传支付清单异常", e);
        }
        return R.fail("上传失败");
    }

    @GetMapping("/get_pay_enterprises_by_enterprise")
    @ApiOperation(value = "查询当前商户所有总包支付清单", notes = "查询当前商户所有总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseMakerId", value = "总包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "serviceProviderName", value = "服务商名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByEnterprise(PayEnterpriseMakerListDto payEnterpriseMakerListDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户所有总包支付清单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return payEnterpriseService.getPayEnterprises(enterpriseWorkerEntity.getEnterpriseId(), null, payEnterpriseMakerListDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户所有总包支付清单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_pay_maker_list_by_pay_enterprise_id")
    @ApiOperation(value = "根据支付清单ID查询创客支付明细", notes = "根据支付清单ID查询创客支付明细")
    public R getPayMakerListByPayEnterpriseId(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query) {

        log.info("根据支付清单ID查询创客支付明细");
        try {
            return payEnterpriseService.getPayMakerListByPayEnterpriseId(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据支付清单ID查询创客支付明细异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/upload_accept_paysheet")
    @ApiOperation(value = "上传总包交付支付验收单", notes = "上传总包交付支付验收单")
    public R uploadAcceptPaysheet(@Valid @RequestBody AcceptPaysheetSaveDto acceptPaysheetSaveDto, BladeUser bladeUser) {

        log.info("上传总包交付支付验收单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return acceptPaysheetService.upload(acceptPaysheetSaveDto, enterpriseWorkerEntity);
        } catch (Exception e) {
            log.error("上传总包交付支付验收单异常", e);
        }

        return R.fail("上传失败");
    }

    @GetMapping("/get_pay_makers_by_enterprise")
    @ApiOperation(value = "查询当前商户所有分包支付清单", notes = "查询当前商户所有分包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseMakerId", value = "分包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "serviceProviderName", value = "服务商名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayMakersByEnterprise(PayEnterpriseMakerListDto payEnterpriseMakerListDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户所有分包支付清单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return payMakerService.getPayMakersByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), null, payEnterpriseMakerListDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户所有分包支付清单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_self_helf_invoice_by_enterprise_id")
    @ApiOperation(value = "查询当前商户所有自主开票记录(众包)", notes = "查询当前商户所有自主开票记录(众包)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseMakerId", value = "自助开票ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getSelfHelfInvoiceByEnterpriseId(SelfHelpInvoicePayDto selfHelpInvoicePayDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户所有自主开票记录(众包)");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return payEnterpriseService.getSelfHelfInvoiceByEnterpriseId(enterpriseWorkerEntity.getEnterpriseId(), selfHelpInvoicePayDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户所有自主开票记录(众包)异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_pay_enterprises_by_service_provider")
    @ApiOperation(value = "查询当前服务商所有总包支付清单", notes = "查询当前服务商所有总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseMakerId", value = "总包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "enterpriseName", value = "商户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByServiceProvider(PayEnterpriseMakerListDto payEnterpriseMakerListDto, Query query, BladeUser bladeUser) {

        log.info("查询当前服务商所有总包支付清单");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return payEnterpriseService.getPayEnterprises(null, serviceProviderWorkerEntity.getServiceProviderId(), payEnterpriseMakerListDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前服务商所有总包支付清单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_pay_makers_by_service_provider")
    @ApiOperation(value = "查询当前服务商所有分包支付清单", notes = "查询当前服务商所有分包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseMakerId", value = "分包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "enterpriseName", value = "商户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayMakersByServiceProvider(PayEnterpriseMakerListDto payEnterpriseMakerListDto, Query query, BladeUser bladeUser) {

        log.info("查询当前服务商所有分包支付清单");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return payMakerService.getPayMakersByEnterprise(null, serviceProviderWorkerEntity.getServiceProviderId(), payEnterpriseMakerListDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前服务商所有分包支付清单异常", e);
        }
        return R.fail("查询失败");
    }

}
