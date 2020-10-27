package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.AdminEntity;
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
@RequestMapping("/admin/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "平台端---个独管理模块相关接口", tags = "平台端---个独管理模块相关接口")
public class IndividualEnterpriseAdminController {

    private IUserClient userClient;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-invoice-number-and-money")
    @ApiOperation(value = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额", notes = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额")
    public R queryInvoiceNumberAndMoney(@ApiParam(value = "个独") @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.selfHelpInvoiceStatistics(individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
    }

    @GetMapping("/query-invoice-list")
    @ApiOperation(value = "查询个独开票记录", notes = "查询个独开票记录")
    public R queryInvoiceList(@ApiParam(value = "个独") @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.selfHelpInvoiceList(Condition.getPage(query.setDescs("create_time")), individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
    }

}
