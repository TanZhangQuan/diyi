package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IIndividualBusinessService;
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
@RequestMapping("/maker/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "创客端---个体户管理模块相关接口", tags = "创客端---个体户管理模块相关接口")
public class IndividualBusinessMakerController {

    private IMakerService makerService;
    private IIndividualBusinessService individualBusinessService;

    @GetMapping("/query-individual-business-list")
    @ApiOperation(value = "查询当前创客的所有个体户", notes = "查询当前创客的所有个体户")
    public R queryIndividualBusinessList(@ApiParam(value = "个体户状态") @RequestParam(required = false) Ibstate ibstate, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualBusinessService.listByMaker(Condition.getPage(query.setDescs("create_time")), makerEntity.getId(), ibstate);
    }

    @GetMapping("/query-individual-business-detail")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R queryIndividualBusinessDetail(@ApiParam(value = "个体户", required = true) @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.findById(individualBusinessId);
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

    @PostMapping("/create-individual-business")
    @ApiOperation(value = "新增个体户", notes = "新增个体户")
    public R createIndividualBusiness(@Valid @RequestBody IndividualBusinessEnterpriseAddDTO individualBusinessEnterpriseAddDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualBusinessService.save(individualBusinessEnterpriseAddDto, makerEntity);
    }

}
