package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/service-provider/natural-person-maker")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---自然人创客管理模块相关接口", tags = "服务商端---自然人创客管理模块相关接口")
public class NaturalPersonMakerServiceProviderController {

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IEnterpriseServiceProviderService enterpriseServiceProviderService;
    private IMakerService makerService;

    @GetMapping("/query-relevance-maker-list")
    @ApiOperation(value = "查询当前服务商的所有关联创客", notes = "查询当前服务商的所有关联创客")
    public R queryRelevanceMakerList(@ApiParam(value = "创客姓名/手机号/身份证号") @RequestParam(required = false) String keyword, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return makerService.queryMakerList(null, serviceProviderWorkerEntity.getServiceProviderId(), null, null, null, keyword, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-maker-detail")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情")
    public R queryMakerDetail(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.queryMakerDetail(makerId);
    }

    @GetMapping("/query-relevance-enterprise-list")
    @ApiOperation(value = "查询服务商关联的所有商户", notes = "查询服务商关联的所有商户")
    public R queryRelevanceEnterpriseList(@ApiParam(value = "商户名称") @RequestParam(required = false) String keyword, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return enterpriseServiceProviderService.queryRelevanceEnterpriseList(serviceProviderWorkerEntity.getServiceProviderId(), keyword, Condition.getPage(query.setDescs("t4.create_time")));
    }

    @GetMapping("/query-relevance-maker-list-by-enterprise-id")
    @ApiOperation(value = "根据商户查询所有关联创客", notes = "根据商户查询所有关联创客")
    public R queryRelevanceMakerListByEnterpriseId(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                                   @ApiParam(value = "创客姓名/手机号/身份证号") @RequestParam(required = false) String keyword, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.queryMakerList(enterpriseId, null, null, RelationshipType.RELEVANCE, null, keyword, Condition.getPage(query.setDescs("t1.create_time")));
    }

}
