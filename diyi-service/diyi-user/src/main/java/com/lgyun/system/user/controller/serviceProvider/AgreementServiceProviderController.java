package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/service-provider/agreement")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---合同管理模块相关接口", tags = "服务商端---合同管理模块相关接口")
public class AgreementServiceProviderController {

    private IAgreementService agreementService;
    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IEnterpriseServiceProviderService enterpriseServiceProviderService;

    @GetMapping("/query-serive-agreement")
    @ApiOperation(value = "查询服务商加盟平台合同", notes = "查询服务商加盟平台合同")
    public R querySeriveAgreement(@RequestParam(required = false) String agreementNo, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return agreementService.findSeriveAgreement(serviceProviderWorkerEntity.getServiceProviderId(), agreementNo);
    }

    @GetMapping("/query-enterprise-agreement")
    @ApiOperation(value = "服务商查询商户加盟平台合同", notes = "服务商查询商户加盟平台合同")
    public R queryEnterpriseAgreement(@RequestParam(required = false) String agreementNo, String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return agreementService.findEnterpriseAgreement(serviceProviderWorkerEntity.getServiceProviderId(), agreementNo, enterpriseName, Condition.getPage(query.setDescs("a.create_time")));
    }

    @GetMapping("/query-enterprise-promise")
    @ApiOperation(value = "服务商查询商户业务真实性承诺函", notes = "服务商查询商户业务真实性承诺函")
    public R queryEnterprisePromise(BladeUser bladeUser, Query query, @RequestParam(required = false) String agreementNo, String enterpriseName) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return agreementService.findEnterprisePromise(serviceProviderWorkerEntity.getServiceProviderId(), agreementNo, enterpriseName, Condition.getPage(query.setDescs("a.create_time")));
    }

    @GetMapping("/query-enterprise-supplement")
    @ApiOperation(value = "服务商查询服务商和商户的补充协议", notes = "服务商查询服务商和商户的补充协议")
    public R queryEnterpriseSupplement(@RequestParam(required = false) String agreementNo, String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return agreementService.findEnterpriseSupplement(serviceProviderWorkerEntity.getServiceProviderId(), agreementNo, enterpriseName, Condition.getPage(query.setDescs("a.create_time")));
    }

    @GetMapping("/query-maker-join-contract")
    @ApiOperation(value = "服务商查询创客加盟平台合同", notes = "服务商查询创客加盟平台合同")
    public R queryMakerJoinContract(@RequestParam(required = false) String agreementNo, String makerName, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return agreementService.findMakerAgreement(serviceProviderWorkerEntity.getServiceProviderId(), agreementNo, makerName, Condition.getPage(query.setDescs("a.create_time")));
    }

    @PostMapping("/upload-supplement")
    @ApiOperation(value = "上传服务商和商户的补充协议", notes = "上传服务商和商户的补充协议")
    public R uploadSupplement(@ApiParam(value = "服务商和商户的补充协议") @NotBlank(message = "请上传服务商和商户的补充协议") @RequestParam(required = false) String contractUrl, Long enterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return agreementService.uploadSupplement(contractUrl, serviceProviderWorkerEntity.getServiceProviderId(), enterpriseId);
    }

    @GetMapping("/query-relevance-enterprise-list")
    @ApiOperation(value = "根据服务商查询有关联的商户", notes = "根据服务商查询有关联的商户")
    public R queryRelevanceEnterpriseList(Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return enterpriseServiceProviderService.queryRelevanceEnterpriseList(serviceProviderWorkerEntity.getServiceProviderId(), null, Condition.getPage(query.setDescs("t3.create_time")));
    }

}
