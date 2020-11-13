package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/agreement")
@Validated
@AllArgsConstructor
@Api(value = "平台端---合同管理模块相关接口", tags = "平台端---合同管理模块相关接口")
public class AgreementAdminController {

    private IAdminService adminService;
    private IAgreementService agreementService;
    private IMakerService makerService;

    @GetMapping("/query-maker-agreement-states")
    @ApiOperation(value = "平台查询创客合同的签署状态", notes = "平台查询创客合同的签署状态")
    public R queryMakerAgreementStates(BladeUser bladeUser, Query query, String makerName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryMakerAgreementState(makerName, Condition.getPage(query.setDescs("m.create_time")));
    }

    @GetMapping("/query-maker-join-or-power-agreement")
    @ApiOperation(value = "平台通过创客查询加盟合同或者授权协议", notes = "平台通过创客查询加盟合同或者授权协议")
    public R queryMakerJoinOrPowerAgreement(Long makerId, AgreementType agreementType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.findAdminMakerId(makerId, agreementType);
    }

    @GetMapping("/query-maker-apply-short-video")
    @ApiOperation(value = "平台通过创客查询授权视频", notes = "平台通过创客查询授权视频")
    public R findMakerApplyShortVideo(Long makerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.data(makerService.getById(makerId));
    }

    @PostMapping("/create-maker-video")
    @ApiOperation(value = "平台端添加创客授权视频", notes = "平台端添加创客授权视频")
    public R createMakerVideo(@ApiParam(value = "创客") @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId,
                              @ApiParam(value = "创客视频") @NotBlank(message = "请上传创客视频") @RequestParam(required = false) String videoUrl, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.saveAdminMakerVideo(makerId, videoUrl);
    }

    @GetMapping("/query-maker-to-enterprise-supplement-list")
    @ApiOperation(value = "平台通过创客查询创客和商户的补充协议", notes = "平台通过创客查询创客和商户的补充协议")
    public R queryMakerToEnterpriseSupplementList(Long makerId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryMakerIdSupplement(makerId, Condition.getPage(query.setDescs("a.create_time")));
    }

    @GetMapping("/query-enterprise-agreement-states")
    @ApiOperation(value = "平台查询商户合同的签署状态", notes = "平台查询商户合同的签署状态")
    public R queryEnterpriseAgreementStates(BladeUser bladeUser, Query query, @RequestParam(required = false) String enterpriseName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryEnterpriseAgreementState(enterpriseName, Condition.getPage(query.setDescs("e.create_time")));
    }

    @GetMapping("/query-enterprise-agreement")
    @ApiOperation(value = "平台根据商户查询商户加盟合同、授权协议或价格协议", notes = "平台根据商户查询商户加盟合同、授权协议或价格协议")
    public R queryEnterpriseAgreement(Long enterpriseId, AgreementType agreementType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryAdminEnterpriseId(enterpriseId, agreementType);
    }

    @GetMapping("/query-enterprise-to-maker-supplement-list")
    @ApiOperation(value = "平台根据商户查询商户和创客的补充协议", notes = "平台根据商户查询商户和创客的补充协议")
    public R queryEnterpriseToMakerSupplementList(Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryEnterpriseIdSupplement(enterpriseId, Condition.getPage(query.setDescs("a.create_time")));
    }


    @GetMapping("/query-enterprise-to-service-provider-supplement-list")
    @ApiOperation(value = "平台根据商户查询合作商户和服务商补充协议", notes = "平台根据商户查询合作商户和服务商补充协议")
    public R queryEnterpriseToServiceProviderSupplementList(Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryEnterIdServiceSupplement(enterpriseId, Condition.getPage(query.setDescs("a.create_time")));
    }


    @GetMapping("/query-enterprise-promise-list")
    @ApiOperation(value = "平台根据商户id查询商户承诺函", notes = "平台根据商户id查询商户承诺函")
    public R queryEnterprisePromiseList(Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryEnterIdPromise(enterpriseId, Condition.getPage(query.setDescs("a.create_time")));
    }

    @GetMapping("/query-service-agreement-state")
    @ApiOperation(value = "平台查询服务商合同的签署状态", notes = "平台查询服务商合同的签署状态")
    public R queryServiceAgreementState(BladeUser bladeUser, Query query, String serviceProviderName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryServiceAgreementState(serviceProviderName, Condition.getPage(query.setDescs("s.create_time")));
    }


    @GetMapping("/query-service-provider-join-agreement")
    @ApiOperation(value = "平台根据服务商查询服务商加盟合同", notes = "平台根据服务商查询服务商加盟合同")
    public R queryServiceProviderJoinAgreement(Long serviceProviderId, AgreementType agreementType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.findAdminSerIdAgreement(serviceProviderId, agreementType);
    }

    @GetMapping("/query-service-provider-to-enterprise-supplement-list")
    @ApiOperation(value = "平台根据服务商id查询合作服务商和商户补充协议", notes = "平台根据服务商id查询合作服务商和商户补充协议")
    public R queryServiceProviderToEnterpriseSupplementList(Long serviceProviderId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryServiceIdEnterSupplement(serviceProviderId, Condition.getPage(query.setDescs("a.create_time")));
    }

    @PostMapping("/update-paper-agreement-url")
    @ApiOperation(value = "根据合同修改合同路径", notes = "根据合同修改合同路径")
    public R updatePaperAgreementUrl(Long agreementId, String agreementUrl, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.saveAdminAgreementId(agreementId, agreementUrl);
    }

    @PostMapping("/create-agreement")
    @ApiOperation(value = "平台端添加合同", notes = "平台端添加合同")
    public R createAgreement(@RequestParam(required = false) Long makerId,
                             @RequestParam(required = false) Long enterpriseId,
                             @RequestParam(required = false) Long serviceProviderId,
                             Long objectId, ObjectType objectType, AgreementType agreementType, String paperAgreementUrl, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.saveAdminAgreement(makerId, enterpriseId, serviceProviderId, objectId, objectType, agreementType, paperAgreementUrl);
    }

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "平台查所有创客", notes = "平台查所有创客")
    public R queryMakerList(Query query, BladeUser bladeUser, @RequestParam(required = false) Long makerId, @RequestParam(required = false) String makerName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return agreementService.queryAdminMakerAll(makerId, makerName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "平台查所有商户", notes = "平台查所有商户")
    public R queryEnterpriseList(Query query, BladeUser bladeUser, @RequestParam(required = false) Long enterpriseId, @RequestParam(required = false) String enterpriseName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return agreementService.queryAdminEnterpriseAll(enterpriseId, enterpriseName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "平台查所有服务商", notes = "平台查所有服务商")
    public R queryServiceProviderList(Query query, BladeUser bladeUser, @RequestParam(required = false) Long serviceProviderId, @RequestParam(required = false) String serviceProviderName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return agreementService.queryAdminServiceAll(serviceProviderId, serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }
}
