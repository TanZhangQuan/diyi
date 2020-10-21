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

/**
 * 平台端---合同管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/admin/agreement")
@Validated
@AllArgsConstructor
@Api(value = "平台端---合同管理模块相关接口", tags = "平台端---合同管理模块相关接口")
public class AgreementAdminController {

    private IAdminService adminService;
    private IAgreementService iAgreementService;
    private IMakerService makerService;

    @GetMapping("/query-maker-agreement")
    @ApiOperation(value = "查询平台自然人创客的合同", notes = "查询平台自然人创客的合同")
    public R findAdminMakerAgreement(@ApiParam(value = "创客名称") @RequestParam(required = false) String makerName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.getMakerName(query.getCurrent(), query.getSize(), makerName);
    }

    @GetMapping("/query-admin-maker-id-agreement")
    @ApiOperation(value = "平台通过创客ID查询加盟合同或者授权协议", notes = "平台通过创客id查询加盟合同或者授权协议")
    public R findAdminMakerIdAgreement(Long makerId, AgreementType agreementType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.findAdminMakerId(makerId, agreementType);
    }

    @GetMapping("/query-maker-id-enterprise-agreement")
    @ApiOperation(value = "平台通过创客id查询合作商户的合同", notes = "平台通过创客id查询合作商户的合同")
    public R findAdMaEnterAgreement(Long makerId, Query query, String enterpriseName, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.findAdMaEnterAgreement(makerId, enterpriseName, Condition.getPage(query.setDescs("create-time")));
    }

    @GetMapping("/query-maker-id-apply-short-video")
    @ApiOperation(value = "平台通过创客id查询授权视频", notes = "平台通过创客id查询授权视频")
    public R findMakerIdApplyShortVideo(Long makerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.data(makerService.getById(makerId));
    }

    @PostMapping("/save-admin-agreement")
    @ApiOperation(value = "平台端添加合同", notes = "平台端添加合同")
    public R saveAdminAgreement(@ApiParam(value = "合同id,编辑的时候才传") @RequestParam(required = false) Long agreementId, @ApiParam(value = "名字，商户就是商户名字，创客就是创客名字") String name,
                                @ApiParam(value = "对象id") Long objectId, @ApiParam(value = "对象类型") ObjectType objectType, @ApiParam(value = "1：代表合同，2授权") Integer contractType,
                                @ApiParam(value = "合同类别") AgreementType agreementType, @ApiParam(value = "合同类别") String paperAgreementUrl, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.saveAdminAgreement(agreementId, name, objectId, objectType, contractType, agreementType, paperAgreementUrl);
    }

    @PostMapping("/save-admin-maker-video")
    @ApiOperation(value = "平台端添加合同", notes = "平台端添加合同")
    public R saveAdminMakerVideo(@ApiParam(value = "创客id") @RequestParam(required = false) Long makerId, @ApiParam(value = "创客视频Url") @RequestParam(required = false) String videoUrl, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.saveAdminMakerVideo(makerId, videoUrl);
    }

    @GetMapping("/query-maker-id-agreement")
    @ApiOperation(value = "平台根据商户id查询商户加盟合同或授权协议", notes = "平台根据商户id查询商户加盟合同或授权协议")
    public R findAdminEnterpriseId(Long enterpriseId, AgreementType agreementType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.findAdminEnterpriseId(enterpriseId, agreementType);
    }

    @GetMapping("/query-enter-id-service-agreement")
    @ApiOperation(value = "平台根据商户id查询合作服务商的合同", notes = "平台根据商户id查询合作服务商的合同")
    public R findEnterIdServiceAgreement(Long enterpriseId, Query query, String serviceProviderName, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.findEnterIdServiceAgreement(enterpriseId, serviceProviderName, Condition.getPage(query.setDescs("create-time")));
    }

    @GetMapping("/query-admin-ser-id-agreement")
    @ApiOperation(value = "平台根据服务商id查询服务商加盟合同或授权协议", notes = "平台根据服务商id查询服务商加盟合同或授权协议")
    public R findAdminSerIdAgreement(Long serviceProviderId, AgreementType agreementType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.findAdminSerIdAgreement(serviceProviderId, agreementType);
    }
}
