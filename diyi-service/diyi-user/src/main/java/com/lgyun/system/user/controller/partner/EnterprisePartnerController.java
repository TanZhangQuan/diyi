package com.lgyun.system.user.controller.partner;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.CreateEnterpriseDTO;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
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
@RequestMapping("/partner/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "合伙人端---商户管理模块相关接口", tags = "合伙人端---商户管理模块相关接口")
public class EnterprisePartnerController {

    private IPartnerService partnerService;
    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-id-and-name-list")
    @ApiOperation(value = "查询合伙人所有商户编号姓名", notes = "查询合伙人所有商户编号姓名")
    public R queryEnterpriseIdAndNameList(@ApiParam(value = "商户名称") @RequestParam(required = false) String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        PartnerEntity partnerEntity = result.getData();

        return enterpriseService.queryEnterpriseIdAndNameList(null, partnerEntity.getId(), enterpriseName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-enterprise-detail")
    @ApiOperation(value = "查询商户详情", notes = "查询商户详情")
    public R queryEnterpriseDetail(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseDetailPartner(enterpriseId);
    }

    @PostMapping("/create-enterprise")
    @ApiOperation(value = "添加商户", notes = "添加商户")
    public R createEnterprise(@Valid @RequestBody CreateEnterpriseDTO createEnterpriseDTO, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        PartnerEntity partnerEntity = result.getData();

        return enterpriseService.createEnterprise(createEnterpriseDTO, null, partnerEntity.getId());
    }

}
