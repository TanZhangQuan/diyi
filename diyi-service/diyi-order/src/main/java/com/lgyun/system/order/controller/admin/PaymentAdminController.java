package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.MakerInvoiceType;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDTO;
import com.lgyun.system.order.dto.PayEnterpriseDTO;
import com.lgyun.system.order.dto.PayEnterpriseUploadDTO;
import com.lgyun.system.order.dto.SelfHelpInvoicesByEnterpriseDTO;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.AdminEntity;
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

    @GetMapping("/query-total-list-by-enterprise-id")
    @ApiOperation(value = "根据商户查询总包", notes = "根据商户查询总包")
    public R queryTotalListByEnterpriseId(@ApiParam(value = "商户编号", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getPayEnterpriseList(enterpriseId, null, payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-total-list-by-service-provider-id")
    @ApiOperation(value = "根据服务商查询总包", notes = "根据服务商查询总包")
    public R queryTotalByServiceProviderId(@ApiParam(value = "服务商编号", required = true) @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId, PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getPayEnterpriseList(null, serviceProviderId, payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/upload-pay-enterprise")
    @ApiOperation(value = "选择商户上传总包支付清单", notes = "选择商户上传总包支付清单")
    public R uploadPayEnterprise(@ApiParam(value = "商户编号", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, @Valid @RequestBody PayEnterpriseUploadDTO payEnterpriseUploadDto, BladeUser bladeUser) throws Exception {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.upload(payEnterpriseUploadDto, enterpriseId);
    }

    @GetMapping("/query-sub-list")
    @ApiOperation(value = "根据总包查询分包", notes = "根据总包查询分包")
    public R querySubList(@ApiParam(value = "总包编号", required = true) @NotNull(message = "请输入总包编号") @RequestParam(required = false) Long payEnterpriseId, Query query, BladeUser bladeUser) {
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
    public R queryCrowdList(@ApiParam(value = "商户编号", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId,
                            @ApiParam(value = "创客类型", required = true) @NotNull(message = "请选择创客类型") @RequestParam(required = false) InvoicePeopleType invoicePeopleType,
                            SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getSelfHelfInvoicesByEnterprise(enterpriseId, invoicePeopleType, selfHelpInvoicesByEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/audit-pay-enterprise")
    @ApiOperation(value = "总包审核", notes = "总包审核")
    public R auditPayEnterprise(@ApiParam(value = "服务商编号", required = true) @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId,
                                @ApiParam(value = "总包编号", required = true) @NotNull(message = "请输入总包编号") @RequestParam(required = false) Long payEnterpriseId,
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
