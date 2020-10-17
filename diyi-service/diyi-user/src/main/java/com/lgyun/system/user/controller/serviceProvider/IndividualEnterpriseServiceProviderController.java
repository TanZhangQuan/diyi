package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 服务商端---个独管理模块相关接口
 *
 * @author tzq
 * @date 2020-09-9
 */
@RestController
//@RequestMapping("/service-provider/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---个独管理模块相关接口", tags = "服务商端---个独管理模块相关接口")
public class IndividualEnterpriseServiceProviderController {

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IIndividualEnterpriseService individualEnterpriseService;

    @GetMapping("/web/individual-enterprise/get_list_by_service_provider_id")
    @ApiOperation(value = "查询当前服务商关联的所有个独", notes = "查询当前服务商关联的所有个独")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个独编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个独名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getListByServiceProviderId(IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return individualEnterpriseService.getIndividualEnterpriseList(Condition.getPage(query.setDescs("create_time")), null, serviceProviderWorkerEntity.getServiceProviderId(), individualBusinessEnterpriseDto);
    }

    @PostMapping("/web/individual-enterprise/update_ibstate")
    @ApiOperation(value = "修改个独状态", notes = "修改个独状态")
    public R updateIbstate(@ApiParam(value = "个独ID", required = true) @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId, @ApiParam(value = "个独状态") @NotNull(message = "请选择个独状态") @RequestParam(required = false) Ibstate ibstate, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return individualEnterpriseService.updateIbstate(serviceProviderWorkerEntity.getServiceProviderId(), individualEnterpriseId, ibstate);
    }

}
