package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDto;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 平台端---个独管理管理controller
 *
 * @author tzq
 * @date 2020-09-9
 */
@Slf4j
@RestController
@RequestMapping("/admin/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "平台端---个独管理管理模块相关接口", tags = "平台端---个独管理管理模块相关接口")
public class IndividualEnterpriseAdminController {

    private IIndividualEnterpriseService individualEnterpriseService;
    private IEnterpriseReportService enterpriseReportService;

    @GetMapping("/query-individual-enterprise-list")
    @ApiOperation(value = "查询所有个独", notes = "查询所有个独")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个独编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个独名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R queryIndividualEnterpriseList(IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto, Query query) {

        log.info("查询所有个独");
        try {
            return individualEnterpriseService.getIndividualEnterpriseList(Condition.getPage(query.setDescs("create_time")), null, null, individualBusinessEnterpriseDto);
        } catch (Exception e) {
            log.error("查询所有个独异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个独年审信息", notes = "查询个独年审信息")
    public R queryEnterpriseReportList(Query query, @ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {

        log.info("查询个独年审信息");
        try {
            return enterpriseReportService.findByBodyTypeAndBodyId(query, BodyType.INDIVIDUALENTERPRISE, individualEnterpriseId);
        } catch (Exception e) {
            log.error("查询个独年审信息异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-self-help-invoice-statistics")
    @ApiOperation(value = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额", notes = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额")
    public R querySelfHelpInvoiceStatistics(@ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {

        log.info("查询个独开票次数，月度开票金额，年度开票金额和总开票金额");
        try {
            return individualEnterpriseService.selfHelpInvoiceStatistics(individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
        } catch (Exception e) {
            log.error("查询个独开票次数，月度开票金额，年度开票金额和总开票金额异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-self-help-invoice-list")
    @ApiOperation(value = "查询个独开票记录", notes = "查询个独开票记录")
    public R querySelfHelpInvoiceList(Query query, @ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {

        log.info("查询个独开票记录");
        try {
            return individualEnterpriseService.selfHelpInvoiceList(query, individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
        } catch (Exception e) {
            log.error("查询个独开票记录异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/save-individual-enterprise")
    @ApiOperation(value = "创建个独", notes = "当前商户申请创建个独")
    public R saveIndividualEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseWebAddDto individualBusinessEnterpriseWebAddDto) {

        log.info("当前商户申请创建个独");
        try {
            return individualEnterpriseService.saveByEnterprise(individualBusinessEnterpriseWebAddDto, null);
        } catch (Exception e) {
            log.error("当前商户申请创建个独异常", e);
        }
        return R.fail("新增个独失败");
    }

}
