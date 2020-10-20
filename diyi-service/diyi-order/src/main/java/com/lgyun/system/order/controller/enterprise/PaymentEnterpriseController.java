package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDTO;
import com.lgyun.system.order.dto.PayEnterpriseDTO;
import com.lgyun.system.order.dto.PayEnterpriseUploadDTO;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商户端---支付管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/enterprise/payment")
@Validated
@AllArgsConstructor
@Api(value = "商户端---支付管理模块相关接口", tags = "商户端---支付管理模块相关接口")
public class PaymentEnterpriseController {

    private IUserClient iUserClient;
    private IPayEnterpriseService payEnterpriseService;
    private IAcceptPaysheetService acceptPaysheetService;

    @PostMapping("/upload-charge-list")
    @ApiOperation(value = "上传总包支付清单", notes = "上传总包支付清单")
    public R upload(@Valid @RequestBody PayEnterpriseUploadDTO payEnterpriseUploadDto, BladeUser bladeUser) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.upload(payEnterpriseUploadDto, enterpriseWorkerEntity.getEnterpriseId());
    }

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

    @GetMapping("/get_pay_enterprises_by_enterprise")
    @ApiOperation(value = "查询当前商户所有总包支付清单", notes = "查询当前商户所有总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payEnterpriseId", value = "总包支付清单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "serviceProviderName", value = "服务商名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "payEnterpriseAuditState", value = "审核状态", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByEnterprise(PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
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
    public R getPayMakerListByPayEnterpriseId(@ApiParam(value = "支付清单编号", required = true) @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getPayMakerListByPayEnterprise(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/submit")
    @ApiOperation(value = "当前商户提交支付清单", notes = "当前商户提交支付清单")
    public R submit(@ApiParam(value = "支付清单编号", required = true) @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.submit(payEnterpriseId, enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/upload_accept_paysheet")
    @ApiOperation(value = "上传总包交付支付验收单", notes = "上传总包交付支付验收单")
    public R uploadAcceptPaysheet(@Valid @RequestBody AcceptPaysheetSaveDTO acceptPaysheetSaveDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return acceptPaysheetService.upload(acceptPaysheetSaveDto, enterpriseWorkerEntity.getEnterpriseId(), "商户上传", enterpriseWorkerEntity.getWorkerName());
    }

}
