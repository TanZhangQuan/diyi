package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.user.service.IAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author jun.
 * @date 2020/9/2.
 * @time 10:05.
 */
@Slf4j
@RestController
@RequestMapping("/service/agreement")
@Validated
@AllArgsConstructor
@Api(value = "(服务商)合同相关相关接口", tags = "(服务商)合同相关相关接口")
public class AgreementServiceController {

    private IAgreementService iAgreementService;

    private IUserClient iUserClient;

    /**
     * 查询服务商加盟平台合同和承诺函
     */
    @GetMapping("/findSeriveAgreement")
    @ApiOperation(value = "查询服务商加盟平台合同和承诺函", notes = "查询服务商加盟平台合同和承诺函")
    public R findSeriveAgreement(BladeUser bladeUser, Query query, @RequestParam(required = false) String agreementNo, ObjectType objectType){
        log.info("查询服务商加盟平台合同和承诺函");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return iAgreementService.findSeriveAgreement(agreementNo,serviceProviderWorkerEntity.getServiceProviderId(), Condition.getPage(query.setDescs("create_time")));
        }catch (Exception e){
            log.error("查询服务商加盟平台合同和承诺函异常", e);
        }
        return R.fail("查询服务商加盟平台合同和承诺函失败");
    }

    /**
     * 上传加盟合同和承诺函
     */
    @PostMapping("/uploadContractAndLetter")
    @ApiOperation(value = "上传加盟合同和承诺函", notes = "上传加盟合同和承诺函")
    public R uploadContractAndLetter(BladeUser bladeUser,String contractUrl,String letterUrl){
        log.info("上传加盟合同和承诺函");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return iAgreementService.uploadContractAndLetter(contractUrl,letterUrl,serviceProviderWorkerEntity.getServiceProviderId());
        }catch (Exception e){
            log.error("上传加盟合同和承诺函异常", e);
        }
        return R.fail("上传加盟合同和承诺函失败");
    }

    /**
     * 查询创客加盟平台合同和承诺函
     */
    @GetMapping("/findMakerAgreement")
    @ApiOperation(value = "查询创客加盟平台合同和承诺函", notes = "查询创客加盟平台合同和承诺函")
    public R findMakerAgreement(BladeUser bladeUser, Query query, @RequestParam(required = false) String agreementNo, String makerName){
        log.info("查询创客加盟平台合同和承诺函");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return iAgreementService.findMakerAgreement(agreementNo,serviceProviderWorkerEntity.getServiceProviderId(),makerName, Condition.getPage(query.setDescs("create_time")));
        }catch (Exception e){
            log.error("查询创客加盟平台合同和承诺函异常", e);
        }
        return R.fail("查询创客加盟平台合同和承诺函失败");
    }


    /**
     * 查询商户加盟平台合同和承诺函
     */
    @GetMapping("/findEnterpriseAgreement")
    @ApiOperation(value = "查询商户加盟平台合同和承诺函", notes = "查询商户加盟平台合同和承诺函")
    public R findEnterpriseAgreement(BladeUser bladeUser, Query query, @RequestParam(required = false) String agreementNo, String enterpriseName){
        log.info("查询商户加盟平台合同和承诺函");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
            return iAgreementService.findEnterpriseAgreement(agreementNo,serviceProviderWorkerEntity.getServiceProviderId(),enterpriseName, Condition.getPage(query.setDescs("create_time")));
        }catch (Exception e){
            log.error("查询商户加盟平台合同和承诺函异常", e);
        }
        return R.fail("查询商户加盟平台合同和承诺函失败");
    }
}
