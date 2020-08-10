package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDto;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Slf4j
@RestController
@RequestMapping("/web/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "个体户相关接口(管理端)", tags = "个体户相关接口(管理端)")
public class IndividualBusinessWebController {

    private IIndividualBusinessService individualBusinessService;
    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/get_by_dto_enterprise")
    @ApiOperation(value = "查询当前商户的关联创客的所有个体户", notes = "查询当前商户的所有关联创客的所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个体户编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个体户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getByDtoEnterprise(@NotNull(message = "请选择个体户状态") @RequestParam(required = false) Ibstate ibstate, IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户的关联创客的所有个体户");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return individualBusinessService.getByDtoEnterprise(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), ibstate, individualBusinessEnterpriseDto);
        } catch (Exception e) {
            log.error("查询当前商户的关联创客的所有个体户异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/find_by_id_enterprise")
    @ApiOperation(value = "查询当前商户的关联创客的个体户详情", notes = "查询当前商户的关联创客的个体户详情")
    public R findByIdEnterprise(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询当前商户的关联创客的个体户详情");
        try {
            return individualBusinessService.findByIdEnterprise(individualBusinessId);
        } catch (Exception e) {
            log.error("查询当前商户的关联创客的个体户详情异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/find-by-id")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R findById(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户详情");
        try {
            return individualBusinessService.findById(individualBusinessId);
        } catch (Exception e) {
            log.error("查询个体户详情异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/year-month-money")
    @ApiOperation(value = "查询个体户月度开票金额和年度开票金额", notes = "查询个体户月度开票金额和年度开票金额")
    public R yearMonthMoney(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户月度开票金额和年度开票金额");
        try {
            return individualBusinessService.yearMonthMoney(individualBusinessId, InvoicePeopleType.INDIVIDUALBUSINESS);
        } catch (Exception e) {
            log.error("查询个体户月度开票金额和年度开票金额异常", e);
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

    @PostMapping("/save_by_enterprise")
    @ApiOperation(value = "当前商户申请创建个体户", notes = "当前商户申请创建个体户")
    public R saveByEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseWebAddDto individualBusinessEnterpriseWebAddDto, BladeUser bladeUser) {

        log.info("当前商户申请创建个体户");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return individualBusinessService.saveByEnterprise(individualBusinessEnterpriseWebAddDto, enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("当前商户申请创建个体户异常", e);
        }
        return R.fail("新增个体户失败");

    }

}