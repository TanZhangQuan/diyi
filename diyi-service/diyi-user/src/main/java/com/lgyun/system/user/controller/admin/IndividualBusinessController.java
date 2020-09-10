package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDto;
import com.lgyun.system.user.service.IIndividualBusinessService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 平台端---个体户管理controller
 *
 * @author tzq
 * @date 2020-09-9
 */
@Slf4j
@RestController
@RequestMapping("/admin/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "平台端---个体户管理模块相关接口", tags = "平台端---个体户管理模块相关接口")
public class IndividualBusinessController {

    private IIndividualBusinessService individualBusinessService;

    @GetMapping("/query")
    @ApiOperation(value = "查询所有个体户", notes = "查询所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个体户编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个体户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R query(IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto, Query query) {

        log.info("查询所有个体户");
        try {
            return individualBusinessService.getIndividualBusinessList(Condition.getPage(query.setDescs("create_time")), null, null, individualBusinessEnterpriseDto);
        } catch (Exception e) {
            log.error("查询所有个体户异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_enterprise_reports")
    @ApiOperation(value = "查询个体户年审信息", notes = "查询个体户年审信息")
    public R queryEnterpriseReports(Query query, @ApiParam(value = "个体户ID") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户年审信息");
        try {
            return individualBusinessService.queryEnterpriseReports(query, individualBusinessId);
        } catch (Exception e) {
            log.error("查询个体户年审信息异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/self_help_invoice_statistics")
    @ApiOperation(value = "查询个体户开票次数，月度开票金额，年度开票金额和总开票金额", notes = "查询个体户开票次数，月度开票金额，年度开票金额和总开票金额")
    public R selfHelpInvoiceStatistics(@ApiParam(value = "个体户ID") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户开票次数，月度开票金额，年度开票金额和总开票金额");
        try {
            return individualBusinessService.selfHelpInvoiceStatistics(individualBusinessId, InvoicePeopleType.INDIVIDUALBUSINESS);
        } catch (Exception e) {
            log.error("查询个体户开票次数，月度开票金额，年度开票金额和总开票金额异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/self_help_invoice_list")
    @ApiOperation(value = "查询个体户开票记录", notes = "查询个体户开票记录")
    public R selfHelpInvoiceList(Query query, @ApiParam(value = "个体户ID") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户开票记录");
        try {
            return individualBusinessService.selfHelpInvoiceList(query, individualBusinessId, InvoicePeopleType.INDIVIDUALBUSINESS);
        } catch (Exception e) {
            log.error("查询个体户开票记录异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/save")
    @ApiOperation(value = "创建个体户", notes = "创建个体户")
    public R create(@Valid @RequestBody IndividualBusinessEnterpriseWebAddDto individualBusinessEnterpriseWebAddDto) {

        log.info("当前商户申请创建个体户");
        try {
            return individualBusinessService.save(individualBusinessEnterpriseWebAddDto, null);
        } catch (Exception e) {
            log.error("当前商户申请创建个体户异常", e);
        }
        return R.fail("新增个体户失败");
    }



}
