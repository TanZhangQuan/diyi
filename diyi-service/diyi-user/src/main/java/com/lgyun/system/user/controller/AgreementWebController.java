package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.feign.IOrderClient;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author .
 * @date 2020/8/15.
 * @time 9:52.
 */
@Slf4j
@RestController
@RequestMapping("/agreement")
@Validated
@AllArgsConstructor
@Api(value = "商户平台合同的信息相关接口", tags = "商户平台合同的信息相关接口")
public class AgreementWebController {

    private IAgreementService agreementService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IOrderClient orderClient;
    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/web/selectEnterpriseId")
    @ApiOperation(value = "根据商户查询商户的加盟合同", notes = "根据商户查询商户的加盟合同")
    public R selectEnterpriseId(BladeUser bladeUser) {
        log.info("根据商户查询商户的加盟合同");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
           return R.data(agreementService.findByEnterpriseAndType(enterpriseWorkerEntity.getEnterpriseId(), AgreementType.ENTERPRISEJOINAGREEMENT));
        } catch (Exception e) {
            log.error("根据商户查询商户的加盟合同异常", e);
        }
        return R.fail("根据商户查询商户的加盟合同失败");
    }

    @GetMapping("/web/selectAuthorization")
    @ApiOperation(value = "根据商户查询商户的单方授权函", notes = "根据商户查询商户的单方授权函")
    public R selectAuthorization(BladeUser bladeUser,Query query) {
        log.info("根据商户查询商户的单方授权函");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return agreementService.selectAuthorization(enterpriseWorkerEntity.getEnterpriseId(),Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据商户查询商户的单方授权函异常", e);
        }
        return R.fail("根据商户查询商户的单方授权函失败");
    }

    @PostMapping("/saveAuthorization")
    @ApiOperation(value = "商户上传授权函", notes = "商户上传授权函")
    public R uploadMakerVideo(BladeUser bladeUser, String paperAgreementURL) {
        log.info("商户上传授权函");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return agreementService.saveAuthorization(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL);
        } catch (Exception e) {
            log.error("商户上传授权函异常", e);
        }
        return R.fail("商户上传授权函失败");
    }

    @GetMapping("/web/selectServiceAgreement")
    @ApiOperation(value = "查询商户关联服务商的加盟合同", notes = "查询商户关联服务商的加盟合同")
    public R selectServiceAgreement(Query query, BladeUser bladeUser, @RequestParam(required = false) String serviceProviderName, @RequestParam(required = false) String agreementNo) {
        log.info("查询商户关联服务商的加盟合同");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return R.data(agreementService.selectServiceAgreement(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, agreementNo, SignType.PLATFORMAGREEMENT, AgreementType.SERVICEPROVIDERJOINAGREEMENT));
        } catch (Exception e) {
            log.error("查询商户关联服务商的加盟合同异常", e);
        }
        return R.fail("查询商户关联服务商的加盟合同失败");
    }

    @GetMapping("/web/selectServiceSupplementaryAgreement")
    @ApiOperation(value = "查询商户关联服务商的补充协议", notes = "查询商户关联服务商的补充协议")
    public R selectServiceSupplementaryAgreement(Query query, BladeUser bladeUser, @RequestParam(required = false) String serviceProviderName, @RequestParam(required = false) String agreementNo) {
        log.info("查询商户关联服务商的补充协议");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
           return R.data(agreementService.selectServiceSupplementaryAgreement(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), serviceProviderName, agreementNo, AgreementType.SERENTSUPPLEMENTARYAGREEMENT));
        } catch (Exception e) {
            log.error("查询商户关联服务商的补充协议异常", e);
        }
        return R.fail("查询商户关联服务商的补充协议失败");
    }

    @PostMapping("/saveSupplementaryAgreement")
    @ApiOperation(value = "商户上传服务商的补充协议", notes = "商户上传服务商的补充协议")
    public R saveSupplementaryAgreement(BladeUser bladeUser, String paperAgreementURL, Long serviceProviderId) {
        log.info("商户上传服务商的补充协议");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return agreementService.saveSupplementaryAgreement(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL, serviceProviderId);
        } catch (Exception e) {
            log.error("商户上传服务商的补充协议异常", e);
        }
        return R.fail("商户上传服务商的补充协议失败");
    }

    @GetMapping("/web/selectMakerAgreement")
    @ApiOperation(value = "查询创客加盟合同", notes = "查询创客加盟合同")
    public R selectMakerAgreement(Query query, BladeUser bladeUser) {
        log.info("查询创客加盟合同");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return agreementService.selectMakerAgreement(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("查询创客加盟合同异常", e);
        }
        return R.fail("查询创客加盟合同失败");
    }

    @PostMapping("/saveEnterpriseMakerAgreement")
    @ApiOperation(value = "商户上传商户和创客的补充协议", notes = "商户上传商户和创客的补充协议")
    public R saveEnterpriseMakerAgreement(BladeUser bladeUser, String paperAgreementURL) {
        log.info("商户上传商户和创客的补充协议");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return agreementService.saveEnterpriseMakerAgreement(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL);
        } catch (Exception e) {
            log.error("商户上传商户和创客的补充协议异常", e);
        }
        return R.fail("商户上传商户和创客的补充协议失败");
    }

    @GetMapping("/web/selectEnterpriseMakerAgreement")
    @ApiOperation(value = "查询商户和创客的补充协议", notes = "查询商户和创客的补充协议")
    public R selectEnterpriseMakerAgreement(Query query, BladeUser bladeUser) {
        log.info("商户和创客的补充协议");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return agreementService.selectEnterpriseMakerAgreement(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("商户和创客的补充协议异常", e);
        }
        return R.fail("商户和创客的补充协议失败");
    }

    /**
     * 发布商户和创客的补充协议
     */
    @PostMapping("/saveEntMakAgreement")
    @ApiOperation(value = "发布商户和创客的补充协议", notes = "发布商户和创客的补充协议")
    public R saveEntMakAgreement(BladeUser bladeUser, String paperAgreementURL,Boolean boolAllMakers,@RequestParam(required = false) String makerIds,Integer templateCount) {
        log.info("发布商户和创客的补充协议");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return agreementService.saveOnlineAgreement(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL,boolAllMakers,makerIds,templateCount,AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT, makerEnterpriseService);
        } catch (Exception e) {
            log.error("发布商户和创客的补充协议异常", e);
        }
        return R.fail("发布商户和创客的补充协议失败");
    }

    /**
     * 根据商户id查询关联的创客
     */
    @GetMapping("/web/selectEnterpriseMaker")
    @ApiOperation(value = "根据商户id查询关联的创客", notes = "根据商户id查询关联的创客")
    public R selectEnterpriseMaker(Query query, BladeUser bladeUser) {
        log.info("根据商户id查询关联的创客");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return makerEnterpriseService.selectEnterpriseMaker(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("根据商户id查询关联的创客异常", e);
        }
        return R.fail("根据商户id查询关联的创客失败");
    }

    /**
     * 发布在线签署的协议
     */
    @PostMapping("/saveOnlineAgreement")
    @ApiOperation(value = "发布在线签署的协议", notes = "发布在线签署的协议")
    public R saveOnlineAgreement(BladeUser bladeUser, String paperAgreementURL,Boolean boolAllMakers,@RequestParam(required = false) String makerIds,Integer templateCount) {
        log.info("发布在线签署的协议");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return agreementService.saveOnlineAgreement(enterpriseWorkerEntity.getEnterpriseId(), paperAgreementURL,boolAllMakers,makerIds,templateCount,AgreementType.OTHERAGREEMENT, makerEnterpriseService);
        } catch (Exception e) {
            log.error("发布在线签署的协议异常", e);
        }
        return R.fail("发布在线签署的协议失败");
    }

    /**
     * 根据商户id查询众包的合同
     */
    @GetMapping("/web/selectEntMakSourc")
    @ApiOperation(value = "根据商户id查询众包的合同", notes = "根据商户id查询众包的合同")
    public R selectEntMakSourc(Query query, BladeUser bladeUser) {
        log.info("根据商户id查询众包的合同");
        try {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return orderClient.selectEntMakSourc(query.getCurrent(),query.getSize(),enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("根据商户id查询众包的合同异常", e);
        }
        return R.fail("根据商户id查询众包的合同失败");
    }

    /**
     * 根据自助开票id查询众包的详情
     */
    @GetMapping("/web/selectEntMakSourcDetail")
    @ApiOperation(value = "根据自助开票id查询众包的详情", notes = "根据自助开票id查询众包的详情")
    public R selectEntMakSourcDetail(Long selfHelpInvoiceId) {
        log.info("根据自助开票id查询众包的详情合同");
        try {
            return orderClient.findDetailCrowdSourcing(selfHelpInvoiceId);
        } catch (Exception e) {
            log.error("根据自助开票id查询众包的详情异常", e);
        }
        return R.fail("根据自助开票id查询众包的详情失败");
    }
}
