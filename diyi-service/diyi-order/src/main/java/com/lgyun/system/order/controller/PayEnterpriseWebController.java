package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerInvoiceType;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.dto.PayEnterpriseDto;
import com.lgyun.system.order.dto.PayEnterpriseUploadDto;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
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
@RestController
@RequestMapping("/web/pay_enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户支付清单相关接口(管理端)", tags = "商户支付清单相关接口(管理端)")
public class PayEnterpriseWebController {

    private IAcceptPaysheetService acceptPaysheetService;
    private IPayEnterpriseService payEnterpriseService;
    private IUserClient iUserClient;


    @GetMapping("/get_worksheet_by_enterprise_id")
    @ApiOperation(value = "查询当前商户所有已完毕的总包+分包类型的工单", notes = "查询当前商户所有已完毕的总包+分包类型的工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "worksheetNo", value = "工单编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "worksheetName", value = "工单名称", paramType = "query", dataType = "string")
    })
    public R getWorksheetByEnterpriseId(String worksheetNo, String worksheetName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.getWorksheetByEnterpriseId(query, enterpriseWorkerEntity.getEnterpriseId(), WorkSheetType.SUBPACKAGE, worksheetNo, worksheetName);
    }

    @GetMapping("/get_service_provider_by_enterprise_id")
    @ApiOperation(value = "查询当前商户关联服务商", notes = "查询当前商户关联服务商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceProviderName", value = "工单编号", paramType = "query", dataType = "string")
    })
    public R getServiceProviderByEnterpriseId(String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.getServiceProviderByEnterpriseId(query, enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "当前商户上传总包支付清单", notes = "当前商户上传总包支付清单")
    public R upload(@Valid @RequestBody PayEnterpriseUploadDto payEnterpriseUploadDto, BladeUser bladeUser) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.upload(payEnterpriseUploadDto, enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/submit")
    @ApiOperation(value = "当前商户提交支付清单", notes = "当前商户提交支付清单")
    public R submit(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.submit(payEnterpriseId, enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/get_pay_enterprises_by_enterprise")
    @ApiOperation(value = "查询当前商户所有总包支付清单", notes = "查询当前商户所有总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseId", value = "总包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "serviceProviderName", value = "服务商名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "payEnterpriseAuditState", value = "审核状态", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByEnterprise(PayEnterpriseDto payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterpriseList(enterpriseWorkerEntity.getEnterpriseId(), null, payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/get_pay_maker_list_by_pay_enterprise_id")
    @ApiOperation(value = "根据支付清单ID查询创客支付明细", notes = "根据支付清单ID查询创客支付明细")
    public R getPayMakerListByPayEnterpriseId(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query) {
        return payEnterpriseService.getPayMakerListByPayEnterprise(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/upload_accept_paysheet")
    @ApiOperation(value = "上传总包交付支付验收单", notes = "上传总包交付支付验收单")
    public R uploadAcceptPaysheet(@Valid @RequestBody AcceptPaysheetSaveDto acceptPaysheetSaveDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return acceptPaysheetService.upload(acceptPaysheetSaveDto, enterpriseWorkerEntity.getEnterpriseId(), "商户上传", enterpriseWorkerEntity.getWorkerName());
    }

    @GetMapping("/get_pay_enterprises_by_service_provider")
    @ApiOperation(value = "查询当前服务商所有总包支付清单", notes = "查询当前服务商所有总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseId", value = "总包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "enterpriseName", value = "商户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "payEnterpriseAuditState", value = "审核状态", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByServiceProvider(PayEnterpriseDto payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterpriseList(null, serviceProviderWorkerEntity.getServiceProviderId(), payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/get_pay_enterprises_by_enterprise_service_provider")
    @ApiOperation(value = "根据当前服务商，商户ID查询总包支付清单", notes = "根据当前服务商，商户ID查询总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseMakerId", value = "总包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "enterpriseName", value = "商户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByEnterprisesServiceProvider(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, PayEnterpriseDto payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterprisesByEnterprisesServiceProvider(enterpriseId, serviceProviderWorkerEntity.getServiceProviderId(), payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/audit")
    @ApiOperation(value = "支付清单审核", notes = "支付清单审核")
    public R audit(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId,
                   @ApiParam(value = "支付清单审核状态") @NotNull(message = "请选择支付清单审核状态") @RequestParam(required = false) PayEnterpriseAuditState auditState,
                   MakerInvoiceType makerInvoiceType, BladeUser bladeUser) {

        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.audit(payEnterpriseId, serviceProviderWorkerEntity.getServiceProviderId(), auditState, makerInvoiceType);
    }

    @GetMapping("/transaction_by_enterprise")
    @ApiOperation(value = "查询当前商户首页交易情况数据", notes = "查询当前商户首页交易情况数据")
    public R transactionByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.transactionByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/transaction_by_service_provider")
    @ApiOperation(value = "查询当前服务商首页交易情况数据", notes = "查询当前服务商首页交易情况数据")
    public R transactionByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.transactionByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/query_total_sub_year_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户总包+分包全年流水", notes = "查询当前商户总包+分包全年流水")
    public R queryTotalSubYearTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubYearTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_total_sub_year_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商总包+分包全年流水", notes = "查询当前服务商总包+分包全年流水")
    public R queryTotalSubYearTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubYearTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/query_total_sub_month_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户总包+分包本月流水", notes = "查询当前商户总包+分包本月流水")
    public R queryTotalSubMonthTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubMonthTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_total_sub_month_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商总包+分包本月流水", notes = "查询当前服务商总包+分包本月流水")
    public R queryTotalSubMonthTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubMonthTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/query_total_sub_week_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户总包+分包本周流水", notes = "查询当前商户总包+分包本周流水")
    public R queryTotalSubWeekTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubWeekTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_total_sub_week_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商总包+分包本周流水", notes = "查询当前服务商总包+分包本周流水")
    public R queryTotalSubWeekTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubWeekTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/query_total_sub_day_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户总包+分包今日流水", notes = "查询当前商户总包+分包今日流水")
    public R queryTotalSubDayTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubDayTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_total_sub_day_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商总包+分包今日流水", notes = "查询当前服务商总包+分包今日流水")
    public R queryTotalSubDayTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubDayTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

}
