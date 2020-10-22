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
    private IServiceProviderMakerService serviceProviderMakerService;
    private IEnterpriseServiceProviderService enterpriseServiceProviderService;
    private IMakerService makerService;
    private IMakerEnterpriseService makerEnterpriseService;

    @GetMapping("/query-relevance-maker-list")
    @ApiOperation(value = "查询当前服务商的所有关联创客", notes = "查询当前服务商的所有关联创客")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "搜索关键字(创客编号，姓名，手机号)", value = "搜索关键字(创客编号，姓名，手机号)", paramType = "query", dataType = "string"),
    })
    public R queryRelevanceMakerList(String keyword, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderMakerService.getServiceProviderMakers(Condition.getPage(query.setDescs("create_time")), serviceProviderWorkerEntity.getServiceProviderId(), keyword);
    }

    @GetMapping("/query-maker-detail")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情")
    public R queryMakerDetail(@ApiParam(value = "创客ID", required = true) @NotNull(message = "请输入创客编号") @RequestParam(required = false) Long makerId) {
        return makerService.getMakerDetailById(null, makerId);
    }

    @GetMapping("/query-relevance-enterprise-list")
    @ApiOperation(value = "查询服务商关联的所有商户", notes = "查询服务商关联的所有商户")
    public R queryRelevanceEnterpriseList(String keyword, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return enterpriseServiceProviderService.getEnterpriseByServiceProvider(Condition.getPage(query.setDescs("create_time")), serviceProviderWorkerEntity.getServiceProviderId(), keyword);
    }

    @GetMapping("/query-relevance-maker-list")
    @ApiOperation(value = "根据商户查询所有关联创客", notes = "根据商户查询所有关联创客")
    public R queryRelevanceMakerList(@ApiParam(value = "商户编号", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, String keyword, Query query) {
        return makerEnterpriseService.getEnterpriseMakerList(Condition.getPage(query.setDescs("create_time")), enterpriseId, RelationshipType.RELEVANCE, null, keyword);
    }

    @GetMapping("/query-self-help-invoice-list")
    @ApiOperation(value = "查询当前服务商的自助开票", notes = "查询当前服务商的自助开票")
    public R querySelfHelpInvoiceList(String keyword, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return makerEnterpriseService.getSelfHelpInvoiceByServiceProviderId(Condition.getPage(query.setDescs("create_time")), keyword, serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/query-self-help-invoice-detail")
    @ApiOperation(value = "根据自助开票查询自助开票详情", notes = "根据自助开票查询自助开票详情")
    public R querySelfHelpInvoiceDetail(@ApiParam(value = "自助开票编号", required = true) @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpvoiceId, Query query) {
        return makerEnterpriseService.getSelfHelpInvoiceDetails(Condition.getPage(query.setDescs("create_time")), selfHelpvoiceId);
    }

}
