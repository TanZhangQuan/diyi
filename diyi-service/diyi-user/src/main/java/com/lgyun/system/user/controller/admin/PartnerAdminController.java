package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.TemplateType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddPartnerDTO;
import com.lgyun.system.user.dto.PartnerListDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 平台端---合伙人管理模块相关接口
 *
 * @author xjw
 * @date 2020/10/21.
 * @time 21:17.
 */
@RestController
@RequestMapping("/admin/partner")
@Validated
@AllArgsConstructor
@Api(value = "平台端---合伙人管理模块相关接口", tags = "平台端---合伙人管理模块相关接口")
public class PartnerAdminController {

    private IAdminService adminService;
    private IPartnerService partnerService;
    private IEnterpriseService enterpriseService;
    private IOnlineSignPicService onlineSignPicService;
    private IPartnerEnterpriseService partnerEnterpriseService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;

    @GetMapping("/query-partner-list")
    @ApiOperation(value = "查询所有合伙人", notes = "查询所有合伙人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partnerName", value = "合伙人名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "创建开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "创建结束时间", paramType = "query", dataType = "date")
    })
    public R queryPartnerList(PartnerListDTO partnerListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return partnerService.queryPartnerList(partnerListDTO, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/update-partner-state")
    @ApiOperation(value = "修改合伙人状态", notes = "修改合伙人状态")
    public R updatePartnerState(@ApiParam(value = "合伙人") @NotNull(message = "请选择合伙人") @RequestParam(required = false) Long partnerId,
                                @ApiParam(value = "合伙人状态") @NotNull(message = "请选择合伙人状态") @RequestParam(required = false) AccountState partnerState, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return partnerService.updatePartnerState(partnerId, partnerState);
    }

    @PostMapping("/create-partner")
    @ApiOperation(value = "创建合伙人", notes = "创建合伙人")
    public R createPartner(@Valid @RequestBody AddPartnerDTO addPartnerDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return partnerService.createPartner(addPartnerDTO);
    }

    @GetMapping("/query-partner-info")
    @ApiOperation(value = "查询合伙人基本信息", notes = "查询合伙人基本信息")
    public R queryPartnerInfo(@ApiParam(value = "合伙人") @NotNull(message = "请选择合伙人") @RequestParam(required = false) Long partnerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return partnerService.queryPartnerInfo(partnerId);
    }

    @GetMapping("/query-enterprise-id-and-name-list")
    @ApiOperation(value = "查询所有商户编号姓名", notes = "查询所有商户编号姓名")
    public R queryEnterpriseIdAndNameList(@ApiParam(value = "商户名称") @RequestParam(required = false) String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseIdAndNameList(null, null, enterpriseName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/match-enterprise")
    @ApiOperation(value = "合伙人匹配商户", notes = "合伙人匹配商户")
    public R matchEnterprise(@ApiParam(value = "合伙人", required = true) @NotNull(message = "请选择合伙人") @RequestParam(required = false) Long partnerId,
                             @ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                             @ApiParam(value = "分配说明") @RequestParam(required = false) String matchDesc, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return partnerEnterpriseService.relevancePartnerEnterprise(partnerId, enterpriseId, matchDesc);
    }

    @GetMapping("/query-cooperation-enterprise-list")
    @ApiOperation(value = "查询合伙人合作商户", notes = "查询合伙人合作商户")
    public R queryCooperationEnterpriseList(@ApiParam(value = "合伙人", required = true) @NotNull(message = "请选择合伙人") @RequestParam(required = false) Long partnerId,
                                            @ApiParam(value = "商户名称", required = true) @RequestParam(required = false) String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return partnerEnterpriseService.queryCooperationEnterpriseList(partnerId, enterpriseName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/update-partner-enterprise-cooperation-status")
    @ApiOperation(value = "更改合伙人商户合作关系", notes = "更改合伙人商户合作关系")
    public R updatePartnerEnterpriseCooperationStatus(@ApiParam(value = "合伙人", required = true) @NotNull(message = "请选择合伙人") @RequestParam(required = false) Long partnerId,
                                                      @ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                                      @ApiParam(value = "合作状态", required = true) @NotNull(message = "请选择合作状态") @RequestParam(required = false) CooperateStatus cooperateStatus, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return partnerEnterpriseService.updateCooperationStatus(partnerId, enterpriseId, cooperateStatus);
    }

    @GetMapping("/query-cooperation-need-contract")
    @ApiOperation(value = "查询合伙人需要签署的合同", notes = "查询合伙人需要签署的合同")
    public R queryCooperationNeedContract(@ApiParam(value = "合伙人", required = true) @NotNull(message = "请选择合伙人") @RequestParam(required = false) Long partnerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return onlineAgreementNeedSignService.getOnlineAgreementNeedSign(ObjectType.PARTNERPEOPLE, partnerId, TemplateType.CONTRACT);
    }

    @PostMapping("/partner-confirm-sign")
    @ApiOperation(value = "合伙人确认签字", notes = "合伙人确认签字")
    public R partnerConfirmSign(@ApiParam(value = "合伙人", required = true) @NotNull(message = "请选择合伙人") @RequestParam(required = false) Long partnerId, BladeUser bladeUser,
                                @ApiParam(value = "签名图片", required = true) @NotBlank(message = "请上传签名图片") @URL(message = "请输入正确的链接") @RequestParam(required = false) String signPic,
                                @ApiParam(value = "模板", required = true) @NotNull(message = "请选择模板") @RequestParam(required = false) Long onlineAgreementTemplateId,
                                @ApiParam(value = "签署的id", required = true) @NotNull(message = "请选择需要签署的id") @RequestParam(required = false) Long onlineAgreementNeedSignId) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return onlineSignPicService.saveOnlineSignPic(partnerId, ObjectType.PARTNERPEOPLE, signPic, onlineAgreementTemplateId, onlineAgreementNeedSignId);
    }

}
