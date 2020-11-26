package com.lgyun.system.user.controller.partner;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddOrUpdateDTO;
import com.lgyun.system.user.dto.MakerListIndividualDTO;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.service.IPartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/partner/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "合伙人端---个独管理模块相关接口", tags = "合伙人端---个独管理模块相关接口")
public class IndividualEnterprisePartnerController {

    private IMakerService makerService;
    private IPartnerService partnerService;
    private IIndividualEnterpriseService individualEnterpriseService;

    @GetMapping("/query-individual-enterprise-list")
    @ApiOperation(value = "查询当前合伙人的所有个独", notes = "查询当前合伙人的所有个独")
    public R queryIndividualEnterpriseList(@ApiParam(value = "个独状态") @RequestParam(required = false) Ibstate ibstate, Query query, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        PartnerEntity partnerEntity = result.getData();

        return individualEnterpriseService.queryIndividualEnterpriseListMaker(null, partnerEntity.getId(), ibstate, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-individual-enterprise-detail")
    @ApiOperation(value = "查询个独详情", notes = "查询个独详情")
    public R queryIndividualEnterpriseDetail(@ApiParam(value = "个独", required = true) @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.queryIndividualEnterpriseDetailMaker(individualEnterpriseId);
    }

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询当前合伙人关联的创客", notes = "查询当前合伙人关联的创客")
    public R queryMakerList(MakerListIndividualDTO makerListIndividualDTO, Query query, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        PartnerEntity partnerEntity = result.getData();

        return makerService.queryMakerListIndividual(null, partnerEntity.getId(), makerListIndividualDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/add-or-update-individual-enterprise")
    @ApiOperation(value = "添加/编辑个独", notes = "添加/编辑个独")
    public R addOrUpdateIndividualEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.addOrUpdateIndividualEnterprise(individualBusinessEnterpriseAddOrUpdateDto, null);
    }

    @GetMapping("/query-update-individual-enterprise-detail")
    @ApiOperation(value = "查询编辑个独详情", notes = "查询编辑个独详情")
    public R queryUpdateIndividualEnterpriseDetail(@ApiParam(value = "个独") @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.queryUpdateIndividualEnterpriseDetail(individualEnterpriseId);
    }

}
