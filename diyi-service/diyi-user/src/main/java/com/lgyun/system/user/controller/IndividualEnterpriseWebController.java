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
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.wrapper.IndividualEnterpriseWrapper;
import io.swagger.annotations.*;
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
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IServiceProviderWorkerService serviceProviderWorkerService;

    @GetMapping("/get_by_dto_enterprise")
    @ApiOperation(value = "查询当前商户的所有关联创客的所有个独", notes = "查询当前商户的所有关联创客的所有个独")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个独编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个独名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getByDtoEnterprise(IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualEnterpriseService.getIndividualEnterpriseList(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), null, individualBusinessEnterpriseDto);
    }

    @GetMapping("/self_help_invoice_statistics")
    @ApiOperation(value = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额", notes = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额")
    public R selfHelpInvoiceStatistics(@ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.selfHelpInvoiceStatistics(individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
    }

    @GetMapping("/self_help_invoice_list")
    @ApiOperation(value = "查询个独开票记录", notes = "查询个独开票记录")
    public R selfHelpInvoiceList(Query query, @ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.selfHelpInvoiceList(query, individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
    }

    @PostMapping("/save_by_enterprise")
    @ApiOperation(value = "当前商户申请创建个独", notes = "当前商户申请创建个独")
    public R saveByEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseWebAddDto individualBusinessEnterpriseWebAddDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualEnterpriseService.saveByEnterprise(individualBusinessEnterpriseWebAddDto, enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_enterprise_reports")
    @ApiOperation(value = "查询个独年审信息", notes = "查询个独年审信息")
    public R queryEnterpriseReports(Query query, @ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.queryEnterpriseReports(query, individualEnterpriseId);
    }

    @GetMapping("/get_list_by_service_provider_id")
    @ApiOperation(value = "查询当前服务商关联的所有个独", notes = "查询当前服务商关联的所有个独")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个独编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个独名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getListByServiceProviderId(IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return individualEnterpriseService.getIndividualEnterpriseList(Condition.getPage(query.setDescs("create_time")), null, serviceProviderWorkerEntity.getServiceProviderId(), individualBusinessEnterpriseDto);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "个独逻辑删除", notes = "个独逻辑删除")
    public R remove(@ApiParam(value = "个独ID集合") @NotBlank(message = "请选择要删除的个独") @RequestParam(required = false) String ids) {
        return R.status(individualEnterpriseService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查询个独详情", notes = "查询个独详情")
    public R detail(@ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        IndividualEnterpriseEntity individualEnterpriseEntity = individualEnterpriseService.getById(individualEnterpriseId);
        return R.data(IndividualEnterpriseWrapper.build().entityVO(individualEnterpriseEntity));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改个独信息", notes = "修改个独信息")
    public R update(@Valid @RequestBody IndividualEnterpriseEntity individualEnterprise) {
        return R.status(individualEnterpriseService.updateById(individualEnterprise));
    }

    @PostMapping("/update_ibstate")
    @ApiOperation(value = "修改个独状态", notes = "修改个独状态")
    public R updateIbstate(@ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId, @ApiParam(value = "个独状态") @NotNull(message = "请选择个独状态") @RequestParam(required = false) Ibstate ibstate, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return individualEnterpriseService.updateIbstate(serviceProviderWorkerEntity.getServiceProviderId(), individualEnterpriseId, ibstate);
    }

}
