package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddMakerDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/maker/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "创客端---个独管理模块相关接口", tags = "创客端---个独管理模块相关接口")
public class IndividualEnterpriseMakerController {

    private IMakerService makerService;
    private IIndividualEnterpriseService individualEnterpriseService;

    @GetMapping("/query-individual-enterprise-list")
    @ApiOperation(value = "查询当前创客的所有个独", notes = "查询当前创客的所有个独")
    public R queryIndividualEnterpriselist(@ApiParam(value = "个独状态") @RequestParam(required = false) Ibstate ibstate, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualEnterpriseService.queryIndividualEnterpriseListMaker(makerEntity.getId(), null, ibstate, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-individual-enterprise-detail")
    @ApiOperation(value = "查询个独详情", notes = "查询个独详情")
    public R queryIndividualEnterpriseDetail(@ApiParam(value = "个独", required = true) @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.queryIndividualEnterpriseDetailMaker(individualEnterpriseId);
    }

    @GetMapping("/query-idcard-info")
    @ApiOperation(value = "查询当前创客身份证信息", notes = "查询当前创客身份证信息")
    public R queryIdcardInfo(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.queryIdcardOcr(makerEntity);
    }

    @PostMapping("/create-individual-enterprise")
    @ApiOperation(value = "创建个独", notes = "创建个独")
    public R createIndividualEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseAddMakerDTO individualBusinessEnterpriseAddMakerDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualEnterpriseService.createIndividualEnterpriseMaker(individualBusinessEnterpriseAddMakerDto, makerEntity);
    }

}
