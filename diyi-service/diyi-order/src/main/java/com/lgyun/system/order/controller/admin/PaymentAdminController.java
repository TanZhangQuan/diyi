package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerInvoiceType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.*;
import com.lgyun.system.order.service.*;
import com.lgyun.system.user.entity.AdminEntity;
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
@RequestMapping("/admin/payment")
@Validated
@AllArgsConstructor
@Api(value = "平台端---支付管理模块相关接口", tags = "平台端---支付管理模块相关接口")
public class PaymentAdminController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private IAcceptPaysheetService acceptPaysheetService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private IWorksheetService worksheetService;

    @GetMapping("/query-finished-worksheet-list")
    @ApiOperation(value = "查询商户所有已完毕的总包+分包类型的工单", notes = "查询商户所有已完毕的总包+分包类型的工单")
    public R queryFinishedWorksheetList(WorksheetFinishedListDTO worksheetFinishedListDTO, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return worksheetService.queryWorksheetListByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), worksheetFinishedListDTO, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/create-or-update-pay-enterprise")
    @ApiOperation(value = "选择商户上传或编辑总包支付清单", notes = "选择商户上传或编辑总包支付清单")
    public R createOrUpdatePayEnterprise(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, @Valid @RequestBody PayEnterpriseCreateOrUpdateDTO payEnterpriseCreateOrUpdateDto, BladeUser bladeUser) throws Exception {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.createOrUpdatePayEnterprise(payEnterpriseCreateOrUpdateDto, enterpriseId);
    }

    @GetMapping("/query-pay-enterprise-list-by-enterprise")
    @ApiOperation(value = "查询商户所有总包+分包-总包支付", notes = "查询商户所有总包+分包-总包支付")
    public R queryPayEnterpriseListByEnterprise(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getPayEnterpriseList(enterpriseId, null, payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-pay-enterprise-list-by-service-provider")
    @ApiOperation(value = "查询服务商所有总包+分包-总包支付", notes = "查询服务商所有总包+分包-总包支付")
    public R queryPayEnterpriseListByServiceProvider(@ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getPayEnterpriseList(null, serviceProviderId, payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-pay-enterprise-detail")
    @ApiOperation(value = "查询总包支付清单详情", notes = "查询总包支付清单详情")
    public R queryPayEnterpriseDetail(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseDetail(payEnterpriseId);
    }

    @GetMapping("/query-pay-enterprise-update-detail")
    @ApiOperation(value = "查询编辑总包支付清单详情", notes = "查询编辑总包支付清单详情")
    public R queryPayEnterpriseUpdateDetail(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseUpdateDetail(payEnterpriseId);
    }

    @GetMapping("/query-worksheet-detail")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R queryTotalSubAcceptPaysheetList(@ApiParam(value = "工单", required = true) @NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getByWorksheetId(worksheetId);
    }

    @GetMapping("/query-pay-maker-list")
    @ApiOperation(value = "根据支付清单查询创客支付明细", notes = "根据支付清单查询创客支付明细")
    public R queryPayMakerList(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getPayMakerListByPayEnterprise(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/upload-accept-paysheet")
    @ApiOperation(value = "上传交付支付验收单", notes = "上传交付支付验收单")
    public R uploadAcceptPaysheet(@Valid @RequestBody AcceptPaysheetSaveDTO acceptPaysheetSaveDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return acceptPaysheetService.upload(acceptPaysheetSaveDto, null, "平台上传", adminEntity.getName());
    }

    @GetMapping("/query-crowd-list")
    @ApiOperation(value = "根据商户查询众包/众采", notes = "根据商户查询众包/众采")
    public R queryCrowdList(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                            @ApiParam(value = "创客类型", required = true) @NotNull(message = "请选择创客类型") @RequestParam(required = false) MakerType makerType,
                            SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getSelfHelfInvoicesByEnterprise(enterpriseId, makerType, selfHelpInvoicesByEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/audit-pay-enterprise")
    @ApiOperation(value = "总包审核", notes = "总包审核")
    public R auditPayEnterprise(@ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                @ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId,
                                @ApiParam(value = "支付清单审核状态", required = true) @NotNull(message = "请选择支付清单审核状态") @RequestParam(required = false) PayEnterpriseAuditState auditState,
                                MakerInvoiceType makerInvoiceType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.audit(payEnterpriseId, serviceProviderId, auditState, makerInvoiceType);
    }

}
