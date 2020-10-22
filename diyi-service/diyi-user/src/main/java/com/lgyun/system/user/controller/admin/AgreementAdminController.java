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
    private IAgreementService iAgreementService;
    private IMakerService makerService;


    @GetMapping("/query-maker-agreement-state")
    @ApiOperation(value = "平台查询创客合同的签署状态", notes = "平台查询创客合同的签署状态")
    public R queryMakerAgreementState(BladeUser bladeUser,Query query,String makerName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.queryMakerAgreementState(makerName,Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-maker-join-or-power-agreement")
    @ApiOperation(value = "平台通过创客ID查询加盟合同或者授权协议", notes = "平台通过创客id查询加盟合同或者授权协议")
    public R queryMakerJoinOrPowerAgreement(Long makerId, AgreementType agreementType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.findAdminMakerId(makerId, agreementType);
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

    @PostMapping("/save-admin-maker-video")
    @ApiOperation(value = "平台端添加创客授权视频", notes = "平台端添加创客授权视频")
    public R saveAdminMakerVideo(@ApiParam(value = "创客id") @RequestParam(required = false) Long makerId, @ApiParam(value = "创客视频Url") @RequestParam(required = false) String videoUrl, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.saveAdminMakerVideo(makerId, videoUrl);
    }

    @GetMapping("/query-maker-id-supplement")
    @ApiOperation(value = "平台通过创客id查询创客和商户的补充协议", notes = "平台通过创客id查询创客和商户的补充协议")
    public R queryMakerIdSupplement(Long makerId,Query query,BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.data(iAgreementService.queryMakerIdSupplement(makerId,Condition.getPage(query.setDescs("create_time"))));
    }

    @GetMapping("/query-enterprise-agreement-state")
    @ApiOperation(value = "平台查询商户合同的签署状态", notes = "平台查询商户合同的签署状态")
    public R queryEnterpriseAgreementState(BladeUser bladeUser,Query query,String enterpriseName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.queryEnterpriseAgreementState(enterpriseName,Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-enterprise-id-agreement")
    @ApiOperation(value = "平台根据商户id查询商户加盟合同、授权协议或价格协议", notes = "平台根据商户id查询商户加盟合同、授权协议或价格协议")
    public R queryAdminEnterpriseId(Long enterpriseId, AgreementType agreementType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.queryAdminEnterpriseId(enterpriseId, agreementType);
    }

    @GetMapping("/query-enterprise-id-supplement")
    @ApiOperation(value = "平台根据商户id查询商户和创客的补充协议", notes = "平台根据商户id查询商户和创客的补充协议")
    public R queryEnterpriseIdSupplement(Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.queryEnterpriseIdSupplement(enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/query-enter-id-service-supplement")
    @ApiOperation(value = "平台根据商户id查询合作商户和服务商补充协议", notes = "平台根据商户id查询合作商户和服务商补充协议")
    public R queryEnterIdServiceSupplement(Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.queryEnterIdServiceSupplement(enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/query-enter-id-promise")
    @ApiOperation(value = "平台根据商户id查询商户承诺函", notes = "平台根据商户id查询商户承诺函")
    public R queryEnterIdPromise(Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return iAgreementService.queryEnterIdPromise(enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-admin-ser-id-agreement")
    @ApiOperation(value = "平台根据服务商id查询服务商加盟合同", notes = "平台根据服务商id查询服务商加盟合同")
    public R findAdminSerIdAgreement(Long serviceProviderId, AgreementType agreementType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.findAdminSerIdAgreement(serviceProviderId, agreementType);
    }

    @GetMapping("/query-service-id-enter-supplement")
    @ApiOperation(value = "平台根据服务商id查询合作服务商和商户补充协议", notes = "平台根据服务商id查询合作服务商和商户补充协议")
    public R queryServiceIdEnterSupplement(Long serviceProviderId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return iAgreementService.queryServiceIdEnterSupplement(serviceProviderId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/save-admin-agreement-id")
    @ApiOperation(value = "根据合同id修改合同路径", notes = "根据合同id修改合同路径")
    public R saveAdminAgreementId(Long agreementId,String agreementUrl,BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return iAgreementService.saveAdminAgreementId(agreementId,agreementUrl);
    }


    @PostMapping("/save-admin-agreement")
    @ApiOperation(value = "平台端添加合同", notes = "平台端添加合同")
    public R saveAdminAgreement(@ApiParam(value = "创客名字")@RequestParam(required = false) Long makerId,
                                @ApiParam(value = "商户名字")@RequestParam(required = false) Long enterpriseId,
                                @ApiParam(value = "服务商名字")@RequestParam(required = false) Long serviceProviderId,
                                @ApiParam(value = "对象id") Long objectId,
                                @ApiParam(value = "对象类型") ObjectType objectType,
                                @ApiParam(value = "1：代表合同，2授权") Integer contractType,
                                @ApiParam(value = "合同类别") AgreementType agreementType,
                                @ApiParam(value = "合同类别") String paperAgreementUrl,
                                BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iAgreementService.saveAdminAgreement(makerId, enterpriseId,serviceProviderId, objectId, objectType, contractType, agreementType, paperAgreementUrl);
    }


    @GetMapping("/query-admin-maker-all")
    @ApiOperation(value = "平台查所有创客", notes = "平台查所有创客")
    public R queryAdminMakerAll(Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return iAgreementService.queryAdminMakerAll(Condition.getPage(query.setDescs("create_time")));
    }


    @GetMapping("/query-admin-enterprise-all")
    @ApiOperation(value = "平台查所有商户", notes = "平台查所有商户")
    public R queryAdminEnterpriseAll(Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return iAgreementService.queryAdminEnterpriseAll(Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-admin-service-all")
    @ApiOperation(value = "平台查所有服务商", notes = "平台查所有服务商")
    public R queryAdminServiceAll(Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return iAgreementService.queryAdminServiceAll(Condition.getPage(query.setDescs("create_time")));
    }
}
