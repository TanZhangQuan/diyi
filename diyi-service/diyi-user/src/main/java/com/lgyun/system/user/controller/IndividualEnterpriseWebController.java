package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.wrapper.IndividualEnterpriseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@RestController
@RequestMapping("/web/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "个独相关接口(管理端)", tags = "个独相关接口(管理端)")
public class IndividualEnterpriseWebController {

    private IIndividualEnterpriseService individualEnterpriseService;

    @GetMapping("/self_help_invoice_statistics")
    @ApiOperation(value = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额", notes = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额")
    public R selfHelpInvoiceStatistics(@ApiParam(value = "个独ID", required = true) @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.selfHelpInvoiceStatistics(individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
    }

    @GetMapping("/self_help_invoice_list")
    @ApiOperation(value = "查询个独开票记录", notes = "查询个独开票记录")
    public R selfHelpInvoiceList(Query query, @ApiParam(value = "个独ID", required = true) @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.selfHelpInvoiceList(query, individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
    }

    @GetMapping("/query_enterprise_reports")
    @ApiOperation(value = "查询个独年审信息", notes = "查询个独年审信息")
    public R queryEnterpriseReports(Query query, @ApiParam(value = "个独ID", required = true) @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.queryEnterpriseReports(query, individualEnterpriseId);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "个独逻辑删除", notes = "个独逻辑删除")
    public R remove(@ApiParam(value = "个独ID集合", required = true) @NotBlank(message = "请选择要删除的个独") @RequestParam(required = false) String ids) {
        return R.status(individualEnterpriseService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查询个独详情", notes = "查询个独详情")
    public R detail(@ApiParam(value = "个独ID", required = true) @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        IndividualEnterpriseEntity individualEnterpriseEntity = individualEnterpriseService.getById(individualEnterpriseId);
        return R.data(IndividualEnterpriseWrapper.build().entityVO(individualEnterpriseEntity));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改个独信息", notes = "修改个独信息")
    public R update(@Valid @RequestBody IndividualEnterpriseEntity individualEnterprise) {
        return R.status(individualEnterpriseService.updateById(individualEnterprise));
    }

}
