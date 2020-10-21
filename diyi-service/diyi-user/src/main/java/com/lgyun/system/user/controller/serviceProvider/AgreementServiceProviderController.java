package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.user.service.IAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author tzq
 * @date 2020/9/2.
 * @time 10:05.
 */
@RestController
@RequestMapping("/serviceProvider/agreement")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---合同相关相关接口", tags = "服务商端---合同相关相关接口")
public class AgreementServiceProviderController {

    private IAgreementService iAgreementService;
    private IUserClient iUserClient;

    @GetMapping("/findSeriveAgreement")
    @ApiOperation(value = "查询服务商加盟平台合同", notes = "查询服务商加盟平台合同")
    public R findSeriveAgreement(BladeUser bladeUser, @RequestParam(required = false) String agreementNo) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return iAgreementService.findSeriveAgreement(agreementNo, serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/findEnterpriseAgreement")
    @ApiOperation(value = "服务商查询商户加盟平台合同", notes = "服务商查询商户加盟平台合同")
    public R findEnterpriseAgreement(BladeUser bladeUser, Query query, @RequestParam(required = false) String agreementNo, String enterpriseName) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return iAgreementService.findEnterpriseAgreement(agreementNo, serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/findEnterprisePromise")
    @ApiOperation(value = "服务商查询商户承诺函", notes = "服务商查询商户承诺函")
    public R findEnterprisePromise(BladeUser bladeUser, Query query, @RequestParam(required = false) String agreementNo, String enterpriseName) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return iAgreementService.findEnterprisePromise(agreementNo, serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/findEnterpriseSupplement")
    @ApiOperation(value = "服务商查询服务商和商户的补充协议", notes = "服务商查询服务商和商户的补充协议")
    public R findEnterpriseSupplement(BladeUser bladeUser, Query query, @RequestParam(required = false) String agreementNo, String enterpriseName) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return iAgreementService.findEnterpriseSupplement(agreementNo, serviceProviderWorkerEntity.getServiceProviderId(), enterpriseName, Condition.getPage(query.setDescs("create_time")));
    }



    @GetMapping("/findMakerAgreement")
    @ApiOperation(value = "服务商查询创客加盟平台合同", notes = "服务商查询创客加盟平台合同")
    public R findMakerAgreement(BladeUser bladeUser, Query query, @RequestParam(required = false) String agreementNo, String makerName) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return iAgreementService.findMakerAgreement(agreementNo, serviceProviderWorkerEntity.getServiceProviderId(), makerName, Condition.getPage(query.setDescs("create_time")));
    }


    @PostMapping("/uploadSupplement")
    @ApiOperation(value = "上传服务商和商户的补充协议", notes = "上传服务商和商户的补充协议")
    public R uploadContractAndLetter(BladeUser bladeUser, String contractUrl,Long enterpriseId) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return iAgreementService.uploadSupplement(contractUrl, serviceProviderWorkerEntity.getServiceProviderId(),enterpriseId);
    }
}
