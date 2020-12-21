package com.lgyun.system.user.controller.partner;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddOrUpdateDTO;
import com.lgyun.system.user.dto.MakerListIndividualDTO;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.service.IIndividualBusinessService;
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
@RequestMapping("/partner/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "合伙人端---个体户管理模块相关接口", tags = "合伙人端---个体户管理模块相关接口")
public class IndividualBusinessPartnerController {

    private IMakerService makerService;
    private IPartnerService partnerService;
    private IIndividualBusinessService individualBusinessService;

    @GetMapping("/query-individual-business-list")
    @ApiOperation(value = "查询当前合伙人的所有个体户", notes = "查询当前合伙人的所有个体户")
    public R queryIndividualBusinessList(@ApiParam(value = "个体户状态") @RequestParam(required = false) Ibstate ibstate, Query query, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        PartnerEntity partnerEntity = result.getData();

        return individualBusinessService.queryIndividualBusinessListMaker(partnerEntity.getId(), ibstate, null, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-individual-business-detail")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R queryIndividualBusinessDetail(@ApiParam(value = "个体户", required = true) @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.queryIndividualBusinessDetailMaker(individualBusinessId);
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

    @PostMapping("/add-or-update-individual-business")
    @ApiOperation(value = "添加/编辑个体户", notes = "添加/编辑个体户")
    public R addOrUpdateIndividualBusiness(@Valid @RequestBody IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.addOrUpdateIndividualBusiness(individualBusinessEnterpriseAddOrUpdateDto, null);
    }

    @GetMapping("/query-update-individual-business-detail")
    @ApiOperation(value = "查询编辑个体户详情", notes = "查询编辑个体户详情")
    public R queryUpdateIndividualBusinessDetail(@ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.queryUpdateIndividualBusinessDetail(individualBusinessId);
    }

}
