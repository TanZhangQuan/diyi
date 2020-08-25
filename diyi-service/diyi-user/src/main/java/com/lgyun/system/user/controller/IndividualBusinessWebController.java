package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDto;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.wrapper.IndividualBusinessWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    private IServiceProviderWorkerService serviceProviderWorkerService;

    @GetMapping("/get_by_dto_enterprise")
    @ApiOperation(value = "查询当前商户的关联创客的所有个体户", notes = "查询当前商户的所有关联创客的所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个体户编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个体户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getByDtoEnterprise(@ApiParam(value = "个体户状态") @NotNull(message = "请选择个体户状态") @RequestParam(required = false) Ibstate ibstate, IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户的关联创客的所有个体户");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return individualBusinessService.getIndividualBusinessList(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), null, ibstate, individualBusinessEnterpriseDto);
        } catch (Exception e) {
            log.error("查询当前商户的关联创客的所有个体户异常", e);
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

    @GetMapping("/get_list_by_service_provider_id")
    @ApiOperation(value = "查询当前服务商关联的所有个体户", notes = "查询当前服务商关联的所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个体户编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个体户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getListByServiceProviderId(@ApiParam(value = "个体户状态") @NotNull(message = "请选择个体户状态") @RequestParam(required = false) Ibstate ibstate, IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {

        log.info("查询当前服务商关联的所有个体户");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return individualBusinessService.getIndividualBusinessList(Condition.getPage(query.setDescs("create_time")), null, serviceProviderWorkerEntity.getServiceProviderId(), ibstate, individualBusinessEnterpriseDto);
        } catch (Exception e) {
            log.error("查询当前服务商关联的所有个体户异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/cancell")
    @ApiOperation(value = "注销个体户", notes = "注销个体户")
    public R cancell(@ApiParam(value = "个体户ID") @NotNull(message = "请选择要注销的个体户") @RequestParam(required = false) Long individualBusinessId) {

        log.info("注销个体户");
        try {
            return individualBusinessService.cancell(individualBusinessId);
        } catch (Exception e) {
            log.error("注销个体户异常", e);
        }
        return R.fail("注销失败");
    }

    @PostMapping("/remove")
    @ApiOperation(value = "个体户逻辑删除", notes = "个体户逻辑删除")
    public R remove(@ApiParam(value = "个体户ID集合") @NotBlank(message = "请选择要删除的个体户") @RequestParam(required = false) String ids) {

        log.info("个体户逻辑删除");
        try {
            return R.status(individualBusinessService.removeByIds(Func.toLongList(ids)));
        } catch (Exception e) {
            log.error("个体户逻辑删除异常", e);
        }
        return R.fail("删除失败");
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取个体户详情", notes = "获取个体户详情")
    public R detail(@ApiParam(value = "个体户ID") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("获取个体户详情");
        try {
            IndividualBusinessEntity individualBusinessEntity = individualBusinessService.getById(individualBusinessId);
            return R.data(IndividualBusinessWrapper.build().entityVO(individualBusinessEntity));
        } catch (Exception e) {
            log.error("获取个体户详情异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改个体户", notes = "修改个体户")
    public R update(@Valid @RequestBody IndividualBusinessEntity individualBusiness) {

        log.info("修改个体户");
        try {
            return R.status(individualBusinessService.updateById(individualBusiness));
        } catch (Exception e) {
            log.error("修改个体户异常", e);
        }

        return R.fail("修改失败");
    }

}