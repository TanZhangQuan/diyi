package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptSheetAndCsListDTO;
import com.lgyun.system.order.service.*;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/enterprise/accept-paysheet")
@Validated
@AllArgsConstructor
@Api(value = "商户端---交付支付验收单管理模块相关接口", tags = "商户端---交付支付验收单管理模块相关接口")
public class AcceptPaysheetEnterpriseController {

    private IUserClient userClient;
    private IAcceptPaysheetService acceptPaysheetService;
    private IAcceptPaysheetCsService acceptPaysheetCsService;
    private IWorksheetService worksheetService;
    private IPayMakerService payMakerService;
    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;

    @GetMapping("/query-total-sub-accept-paysheet-list")
    @ApiOperation(value = "查询当前商户所有总包+分包交付支付验收单", notes = "查询当前商户所有总包+分包交付支付验收单")
    public R queryTotalSubAcceptPaysheetList(AcceptSheetAndCsListDTO acceptSheetAndCsListDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return acceptPaysheetService.queryTotalSubAcceptPaysheetListEnterprise(enterpriseWorkerEntity.getEnterpriseId(), acceptSheetAndCsListDto, Condition.getPage(query.setDescs("temp.create_time")));
    }

    @GetMapping("/query-total-sub-accept-paysheet-detail")
    @ApiOperation(value = "查询当前商户所有总包+分包交付支付验收单详情", notes = "查询当前商户所有总包+分包交付支付验收单详情")
    public R queryTotalSubAcceptPaysheetDetail(@ApiParam(value = "总包+分包交付支付验收单") @NotNull(message = "请选择总包+分包交付支付验收单") @RequestParam(required = false) Long acceptPaysheetId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return acceptPaysheetService.queryTotalSubAcceptPaysheetDetailEnterprise(acceptPaysheetId);
    }

    @GetMapping("/query-total-sub-accept-paysheet-pay-maker-list")
    @ApiOperation(value = "查询总包+分包交付支付验收单的创客支付明细", notes = "查询总包+分包交付支付验收单的创客支付明细")
    public R queryTotalSubAcceptPaysheetPayMakerList(@ApiParam(value = "总包+分包交付支付验收单") @NotNull(message = "请选择总包+分包交付支付验收单") @RequestParam(required = false) Long acceptPaysheetId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payMakerService.queryTotalSubAcceptPaysheetPayMakerList(acceptPaysheetId, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-crowd-accept-paysheet-list")
    @ApiOperation(value = "查询当前商户所有众包交付支付验收单", notes = "查询当前商户所有众包交付支付验收单")
    public R queryCrowdAcceptPaysheetList(AcceptSheetAndCsListDTO acceptSheetAndCsListDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return acceptPaysheetCsService.queryCrowdAcceptPaysheetListEnterprise(enterpriseWorkerEntity.getEnterpriseId(), acceptSheetAndCsListDto, Condition.getPage(query.setDescs("temp.create_time")));
    }

    @GetMapping("/query-crowd-accept-paysheet-detail")
    @ApiOperation(value = "查询众包交付支付验收单详情", notes = "查询众包交付支付验收单详情")
    public R queryCrowdAcceptPaysheetDetail(@ApiParam(value = "众包交付支付验收单") @NotNull(message = "请选择众包交付支付验收单") @RequestParam(required = false) Long acceptPaysheetCsId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return acceptPaysheetCsService.queryCrowdAcceptPaysheetDetailEnterprise(acceptPaysheetCsId);
    }

    @GetMapping("/query-crowd-accept-paysheet-self-help-invoice-detail-list")
    @ApiOperation(value = "查询众包交付支付验收单的自主开票明细", notes = "查询众包交付支付验收单的自主开票明细")
    public R queryCrowdAcceptPaysheetSelfHelpInvoiceDetailList(@ApiParam(value = "众包交付支付验收单") @NotNull(message = "请选择众包交付支付验收单") @RequestParam(required = false) Long acceptPaysheetCsId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceDetailService.queryCrowdAcceptPaysheetSelfHelpInvoiceDetailList(acceptPaysheetCsId, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-worksheet-detail")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R queryTotalSubAcceptPaysheetList(@ApiParam(value = "工单", required = true) @NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getByWorksheetId(worksheetId);
    }

}
