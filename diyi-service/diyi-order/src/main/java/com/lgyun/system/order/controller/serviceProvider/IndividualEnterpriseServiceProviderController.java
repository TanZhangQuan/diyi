package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
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
@RequestMapping("/service-provider/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---个独管理模块相关接口", tags = "服务商端---个独管理模块相关接口")
public class IndividualEnterpriseServiceProviderController {

    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-invoice-number-and-money")
    @ApiOperation(value = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额", notes = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额")
    public R queryInvoiceNumberAndMoney(@ApiParam(value = "个独", required = true) @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId) {
        return selfHelpInvoiceService.selfHelpInvoiceStatistics(individualEnterpriseId, MakerType.INDIVIDUALENTERPRISE);
    }

    @GetMapping("/query-invoice-list")
    @ApiOperation(value = "查询个独开票记录", notes = "查询个独开票记录")
    public R queryInvoiceList(Query query, @ApiParam(value = "个独", required = true) @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId) {
        return selfHelpInvoiceService.selfHelpInvoiceList(Condition.getPage(query.setDescs("t1.create_time")), individualEnterpriseId, MakerType.INDIVIDUALENTERPRISE);
    }

}
