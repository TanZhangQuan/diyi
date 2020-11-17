package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDTO;
import com.lgyun.system.order.dto.PayEnterpriseCreateOrUpdateDTO;
import com.lgyun.system.order.dto.PayEnterpriseDTO;
import com.lgyun.system.order.dto.WorksheetFinishedListDTO;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/enterprise/payment")
@Validated
@AllArgsConstructor
@Api(value = "商户端---支付管理模块相关接口", tags = "商户端---支付管理模块相关接口")
public class PaymentEnterpriseController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private IAcceptPaysheetService acceptPaysheetService;
    private IWorksheetService worksheetService;

    @GetMapping("/query-finished-worksheet-list")
    @ApiOperation(value = "查询当前商户所有已完毕的总包+分包类型的工单", notes = "查询当前商户所有已完毕的总包+分包类型的工单")
    public R queryFinishedWorksheetList(WorksheetFinishedListDTO worksheetFinishedListDTO, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return worksheetService.queryWorksheetListByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), worksheetFinishedListDTO, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("read-pay-enterprise-excel")
    @ApiOperation(value = "总包支付清单Excel文件读取", notes = "总包支付清单Excel文件读取")
    public R readPayEnterpriseExcel(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请上传总包支付清单") @RequestParam(required = false) String chargeListUrl, BladeUser bladeUser) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.readPayEnterpriseExcel(chargeListUrl);
    }

    @PostMapping("/create-or-update-pay-enterprise")
    @ApiOperation(value = "上传或编辑总包支付清单", notes = "上传或编辑总包支付清单")
    public R createOrUpdatePayEnterprise(@Valid @RequestBody PayEnterpriseCreateOrUpdateDTO payEnterpriseCreateOrUpdateDto, BladeUser bladeUser) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.createOrUpdatePayEnterprise(payEnterpriseCreateOrUpdateDto, enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query-pay-enterprise-list")
    @ApiOperation(value = "查询当前商户所有总包支付清单", notes = "查询当前商户所有总包支付清单")
    public R queryPayEnterpriseList(PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterpriseList(enterpriseWorkerEntity.getEnterpriseId(), null, payEnterpriseDto, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-pay-enterprise-detail")
    @ApiOperation(value = "查询总包支付清单详情", notes = "查询总包支付清单详情")
    public R queryPayEnterpriseDetail(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseDetail(payEnterpriseId);
    }

    @GetMapping("/query-pay-enterprise-update-detail")
    @ApiOperation(value = "查询编辑总包支付清单详情", notes = "查询编辑总包支付清单详情")
    public R queryPayEnterpriseUpdateDetail(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseUpdateDetail(payEnterpriseId);
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

    @GetMapping("/query-pay-maker-list")
    @ApiOperation(value = "根据支付清单查询创客支付明细", notes = "根据支付清单查询创客支付明细")
    public R queryPayMakerList(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getPayMakerListByPayEnterprise(payEnterpriseId, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/submit-pay-enterprise")
    @ApiOperation(value = "当前商户提交支付清单", notes = "当前商户提交支付清单")
    public R submitPayEnterprise(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.submit(payEnterpriseId, enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/upload-accept-paysheet")
    @ApiOperation(value = "上传交付支付验收单", notes = "上传交付支付验收单")
    public R uploadAcceptPaysheet(@Valid @RequestBody AcceptPaysheetSaveDTO acceptPaysheetSaveDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return acceptPaysheetService.uploadAcceptPaysheet(enterpriseWorkerEntity.getEnterpriseId(), null, acceptPaysheetSaveDto, "商户上传", enterpriseWorkerEntity.getWorkerName());
    }

}
