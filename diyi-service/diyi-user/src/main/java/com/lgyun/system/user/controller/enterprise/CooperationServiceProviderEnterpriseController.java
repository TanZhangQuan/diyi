package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enterprise/cooperation-service-provider")
@Validated
@AllArgsConstructor
@Api(value = "商户端---合作服务商管理模块相关接口", tags = "商户端---合作服务商管理模块相关接口")
public class CooperationServiceProviderEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private IEnterpriseServiceProviderService enterpriseProviderService;

    @GetMapping("/query-cooperation-service-provider-list")
    @ApiOperation(value = "查询当前商户合作服务商", notes = "查询当前商户合作服务商")
    public R queryCooperationServiceProviderList(String keyWord, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseProviderService.getServiceProvidersByEnterpriseId(enterpriseWorkerEntity.getEnterpriseId(), keyWord, Condition.getPage(query.setDescs("t1.create_time")));
    }

}
