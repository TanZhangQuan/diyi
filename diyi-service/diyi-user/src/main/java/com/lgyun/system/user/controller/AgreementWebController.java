package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
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
    private IOnlineSignPicService onlineSignPicService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IMakerService iMakerService;

    @GetMapping("/web/selectEnterpriseId")
    @ApiOperation(value = "根据商户查询商户的加盟合同", notes = "根据商户查询商户的加盟合同")
    public R selectEnterpriseId(Long enterpriseId) {
        log.info("根据商户查询商户的加盟合同");
        try {
            R.data(agreementService.findByEnterpriseAndType(enterpriseId, 2));
        } catch (Exception e) {
            log.error("根据商户查询商户的加盟合同异常", e);
        }
        return R.fail("根据商户查询商户的加盟合同失败");
    }

    @GetMapping("/web/selectAuthorization")
    @ApiOperation(value = "根据商户查询商户的单方授权函", notes = "根据商户查询商户的单方授权函")
    public R selectAuthorization(Long enterpriseId) {
        log.info("根据商户查询商户的单方授权函");
        try {
            R.data(agreementService.selectAuthorization(enterpriseId));
        } catch (Exception e) {
            log.error("根据商户查询商户的单方授权函异常", e);
        }
        return R.fail("根据商户查询商户的单方授权函失败");
    }

    @PostMapping("/saveAuthorization")
    @ApiOperation(value = "商户上传授权函", notes = "商户上传授权函")
    public R uploadMakerVideo(Long enterpriseId, String paperAgreementURL) {
        log.info("商户上传授权函");
        try {
            return agreementService.saveAuthorization(enterpriseId, paperAgreementURL);
        } catch (Exception e) {
            log.error("商户上传授权函异常", e);
        }
        return R.fail("商户上传授权函失败");
    }

    @GetMapping("/web/selectServiceAgreement")
    @ApiOperation(value = "查询商户关联服务商的加盟合同", notes = "查询商户关联服务商的加盟合同")
    public R selectServiceAgreement(Query query, Long enterpriseId, @RequestParam(required = false) String serviceProviderName, @RequestParam(required = false) String agreementNo) {
        log.info("查询商户关联服务商的加盟合同");
        try {
            R.data(agreementService.selectServiceAgreement(Condition.getPage(query.setDescs("create_time")), enterpriseId, serviceProviderName, agreementNo, SignType.PLATFORMAGREEMENT, 3));
        } catch (Exception e) {
            log.error("查询商户关联服务商的加盟合同异常", e);
        }
        return R.fail("查询商户关联服务商的加盟合同失败");
    }

    @GetMapping("/web/selectServiceSupplementaryAgreement")
    @ApiOperation(value = "查询商户关联服务商的补充协议", notes = "查询商户关联服务商的补充协议")
    public R selectServiceSupplementaryAgreement(Query query, Long enterpriseId, @RequestParam(required = false) String serviceProviderName, @RequestParam(required = false) String agreementNo) {
        log.info("查询商户关联服务商的加盟合同");
        try {
            R.data(agreementService.selectServiceSupplementaryAgreement(Condition.getPage(query.setDescs("create_time")), enterpriseId, serviceProviderName, agreementNo, 11));
        } catch (Exception e) {
            log.error("查询商户关联服务商的补充协议异常", e);
        }
        return R.fail("查询商户关联服务商的补充协议失败");
    }

    @PostMapping("/saveSupplementaryAgreement")
    @ApiOperation(value = "商户上传服务商的补充协议", notes = "商户上传服务商的补充协议")
    public R saveSupplementaryAgreement(Long enterpriseId, String paperAgreementURL, Long serviceProviderId) {
        log.info("商户上传服务商的补充协议");
        try {
            return agreementService.saveSupplementaryAgreement(enterpriseId, paperAgreementURL, serviceProviderId);
        } catch (Exception e) {
            log.error("商户上传服务商的补充协议异常", e);
        }
        return R.fail("商户上传服务商的补充协议失败");
    }

    @GetMapping("/web/selectMakerAgreement")
    @ApiOperation(value = "查询创客加盟合同", notes = "查询创客加盟合同")
    public R selectMakerAgreement(Query query, Long enterpriseId) {
        log.info("查询创客加盟合同");
        try {
            return agreementService.selectMakerAgreement(Condition.getPage(query.setDescs("create_time")), enterpriseId);
        } catch (Exception e) {
            log.error("查询创客加盟合同异常", e);
        }
        return R.fail("查询创客加盟合同失败");
    }
}
