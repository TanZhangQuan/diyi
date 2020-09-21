package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.MakerInvoiceType;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.dto.PayEnterpriseDto;
import com.lgyun.system.order.dto.PayEnterpriseUploadDto;
import com.lgyun.system.order.dto.SelfHelpInvoicesByEnterpriseDto;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 平台端支付管理controller
 *
 * @author tzq
 * @since 2020-09-9 20:01:13
 */
@Slf4j
@RestController
@RequestMapping("/admin/payment-management")
@Validated
@AllArgsConstructor
@Api(value = "平台端---支付管理模块相关接口", tags = "平台端---支付管理模块相关接口")
public class PaymentAdminController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient userClient;
    private IAcceptPaysheetService acceptPaysheetService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-pay-enterprise-list-by-enterprise")
    @ApiOperation(value = "根据商户查询总包", notes = "根据商户查询总包")
    public R queryPayEnterpriseListByEnterprise(@ApiParam(value = "商户编号") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, PayEnterpriseDto payEnterpriseDto, Query query) {

        log.info("根据商户查询总包支付");
        try {
            return payEnterpriseService.getPayEnterpriseList(enterpriseId, null, payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据商户查询总包异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-pay-enterprise-list-by-service-provider")
    @ApiOperation(value = "根据服务商查询总包", notes = "根据服务商查询总包")
    public R queryPayEnterpriseListByServiceProvider(@ApiParam(value = "服务商编号") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId, PayEnterpriseDto payEnterpriseDto, Query query) {

        log.info("根据服务商查询总包");
        try {
            return payEnterpriseService.getPayEnterpriseList(null, serviceProviderId, payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据服务商查询总包异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/upload-pay-enterprise-by-enterprise")
    @ApiOperation(value = "选择商户上传总包支付清单", notes = "选择商户上传总包支付清单")
    public R uploadPayEnterpriseByEnterprise(@ApiParam(value = "商户编号") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, @Valid @RequestBody PayEnterpriseUploadDto payEnterpriseUploadDto) {

        log.info("选择商户上传总包支付清单");
        try {
            return payEnterpriseService.upload(payEnterpriseUploadDto, enterpriseId);
        } catch (Exception e) {
            log.error("选择商户上传总包支付清单异常", e);
        }
        return R.fail("上传失败");
    }

    @GetMapping("/query-pay-maker-list-by-pay-enterprise")
    @ApiOperation(value = "根据总包查询分包", notes = "根据总包查询分包")
    public R queryPayMakerListByPayEnterprise(@ApiParam(value = "总包编号") @NotNull(message = "请输入总包编号") @RequestParam(required = false) Long payEnterpriseId, Query query) {

        log.info("根据总包查询分包");
        try {
            return payEnterpriseService.getPayMakerListByPayEnterprise(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据总包查询分包异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/upload-accept-paysheet-by-enterprise")
    @ApiOperation(value = "上传交付支付验收单", notes = "上传交付支付验收单")
    public R uploadAcceptPaysheetByEnterprise(@Valid @RequestBody AcceptPaysheetSaveDto acceptPaysheetSaveDto, BladeUser bladeUser) {

        log.info("上传总包交付支付验收单");
        try {
            //查询当前管理员
            R<AdminEntity> result = userClient.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();

            return acceptPaysheetService.upload(acceptPaysheetSaveDto, null, "平台上传", adminEntity.getName());
        } catch (Exception e) {
            log.error("上传总包交付支付验收单异常", e);
        }

        return R.fail("上传失败");
    }

    @GetMapping("/query-self-helf-invoice-list")
    @ApiOperation(value = "根据商户查询众包/众采", notes = "根据商户查询众包/众采")
    public R getSelfHelfInvoiceList(@ApiParam(value = "商户编号") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId,
                                             @ApiParam(value = "创客类型") @NotNull(message = "请选择创客类型") @RequestParam(required = false) InvoicePeopleType invoicePeopleType,
                                             SelfHelpInvoicesByEnterpriseDto selfHelpInvoicesByEnterpriseDto, Query query) {

        log.info("根据商户查询众包/众采");
        try {
            return selfHelpInvoiceService.getSelfHelfInvoicesByEnterprise(enterpriseId, invoicePeopleType, selfHelpInvoicesByEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据商户查询众包/众采异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/pay-enterprise-audit")
    @ApiOperation(value = "总包审核", notes = "总包审核")
    public R payEnterpriseAudit(@ApiParam(value = "服务商编号") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId,
                                @ApiParam(value = "总包编号") @NotNull(message = "请输入总包编号") @RequestParam(required = false) Long payEnterpriseId,
                                @ApiParam(value = "支付清单审核状态") @NotNull(message = "请选择支付清单审核状态") @RequestParam(required = false) PayEnterpriseAuditState auditState,
                                MakerInvoiceType makerInvoiceType) {

        log.info("支付清单审核");
        try {
            return payEnterpriseService.audit(payEnterpriseId, serviceProviderId, auditState, makerInvoiceType);
        } catch (Exception e) {
            log.error("支付清单审核异常", e);
        }
        return R.fail("审核失败");
    }

}
