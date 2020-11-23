package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/enterprise/agreement")
@Validated
@AllArgsConstructor
@Api(value = "商户端---合同管理模块相关接口", tags = "商户端---合同管理模块相关接口")
public class AgreementEnterpriseController {

    private IAgreementService agreementService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IEnterpriseServiceProviderService enterpriseServiceProviderService;

    @GetMapping("/query-enterprise-join-contract")
    @ApiOperation(value = "根据商户查询商户的加盟合同", notes = "根据商户查询商户的加盟合同")
    public R selectEnterpriseId(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return R.data(agreementService.findByEnterpriseAndType(enterpriseWorkerEntity.getEnterpriseId(), AgreementType.ENTERPRISEJOINAGREEMENT, SignType.PAPERAGREEMENT));
    }

    @GetMapping("/query-price-agreement")
    @ApiOperation(value = "根据商户查询商户的加盟价格协议合同", notes = "根据商户查询商户的加盟价格协议合同")
    public R queryPriceAgreement(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return R.data(agreementService.findByEnterpriseAndType(enterpriseWorkerEntity.getEnterpriseId(), AgreementType.ENTERPRISEPRICEAGREEMENT, SignType.PAPERAGREEMENT));
    }

    @GetMapping("/query-commitment-letter")
    @ApiOperation(value = "根据商户查询商户承诺函", notes = "根据商户查询商户承诺函")
    public R queryCommitmentLetter(Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return agreementService.selectAuthorization(enterpriseWorkerEntity.getEnterpriseId(), Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/upload-commitment-letter")
    @ApiOperation(value = "上传承诺函", notes = "上传承诺函")
    public R uploadCommitmentLetter(String paperAgreementURL, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return agreementService.saveAuthorization(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL);
    }

    @GetMapping("/query-service-provider-join-contract")
    @ApiOperation(value = "查询商户关联服务商的加盟合同", notes = "查询商户关联服务商的加盟合同")
    public R selectServiceAgreement(Query query, BladeUser bladeUser, @RequestParam(required = false) String serviceProviderName, @RequestParam(required = false) String agreementNo) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return R.data(agreementService.selectServiceAgreement(Condition.getPage(query.setDescs("a.create_time")), enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, agreementNo, AgreementType.SERVICEPROVIDERJOINAGREEMENT));
    }

    @GetMapping("/query-service-provider-supplementary-agreement")
    @ApiOperation(value = "查询商户关联服务商的补充协议", notes = "查询商户关联服务商的补充协议")
    public R selectServiceSupplementaryAgreement(Query query, BladeUser bladeUser, @RequestParam(required = false) String serviceProviderName, @RequestParam(required = false) String agreementNo) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return R.data(agreementService.selectServiceSupplementaryAgreement(Condition.getPage(query.setDescs("a.create_time")), enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, agreementNo, AgreementType.SERENTSUPPLEMENTARYAGREEMENT));
    }

    @PostMapping("/upload-supplementary-agreement")
    @ApiOperation(value = "商户上传服务商的补充协议", notes = "商户上传服务商的补充协议")
    public R saveSupplementaryAgreement(BladeUser bladeUser, String paperAgreementURL, Long serviceProviderId) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return agreementService.saveSupplementaryAgreement(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL, serviceProviderId);
    }

    @GetMapping("/query-maker-join-contract")
    @ApiOperation(value = "查询创客加盟合同", notes = "查询创客加盟合同")
    public R selectMakerAgreement(Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return agreementService.selectMakerAgreement(Condition.getPage(query.setDescs("a.create_time")), enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/upload-enterprise-to-maker-supplementary-agreement")
    @ApiOperation(value = "商户上传商户和创客的补充协议", notes = "商户上传商户和创客的补充协议")
    public R saveEnterpriseToMakerAgreement(BladeUser bladeUser, @NotBlank(message = "请选择商户和创客的补充协议") @RequestParam(required = false) String paperAgreementURL, @NotBlank(message = "选择的创客") @RequestParam(required = false) String makerIds) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return agreementService.saveEnterpriseMakerAgreement(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL, makerIds);
    }

    @GetMapping("/query-enterprise-maker-supplementary-agreement")
    @ApiOperation(value = "查询商户和创客的补充协议", notes = "查询商户和创客的补充协议")
    public R selectEnterpriseMakerAgreement(Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return agreementService.selectEnterpriseMakerAgreement(Condition.getPage(query.setDescs("a.create_time")), enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/save-ent-mak-supplementary-agreement")
    @ApiOperation(value = "发布商户和创客的补充协议", notes = "发布商户和创客的补充协议")
    public R saveEntMakAgreement(BladeUser bladeUser, String paperAgreementURL, @RequestParam(required = false) String makerIds) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return agreementService.saveEntMakAgreement(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL, makerIds, AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT);
    }

    @GetMapping("/query-enterprise-maker")
    @ApiOperation(value = "根据商户查询关联的创客", notes = "根据商户查询关联的创客")
    public R selectEnterpriseMaker(Query query, BladeUser bladeUser, @RequestParam(required = false) String makerName) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerEnterpriseService.selectEnterpriseMaker(Condition.getPage(query.setDescs("m.create_time")), enterpriseWorkerEntity.getEnterpriseId(), makerName);
    }

//    @PostMapping("/create-online-agreement")
//    @ApiOperation(value = "发布在线签署的协议", notes = "发布在线签署的协议")
//    public R createOnlineAgreement(BladeUser bladeUser, String paperAgreementURL, Boolean boolAllMakers, @RequestParam(required = false) String makerIds, Integer templateCount) throws Exception {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//
//        return agreementService.saveOnlineAgreement(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL, boolAllMakers, makerIds, templateCount, AgreementType.OTHERAGREEMENT, makerEnterpriseService);
//    }

    @GetMapping("/query-relevance-service-provider-list")
    @ApiOperation(value = "根据商户查询有关联的服务商", notes = "根据商户查询有关联的服务商")
    public R queryRelevanceServiceProviderList(String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseServiceProviderService.queryCooperationServiceProviderList(enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, Condition.getPage(query.setDescs("t1.create_time")));
    }
}
