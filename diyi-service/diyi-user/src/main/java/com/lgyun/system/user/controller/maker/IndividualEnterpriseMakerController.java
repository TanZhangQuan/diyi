package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 创客端---个独管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
//@RequestMapping("/maker/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "创客端---个独管理模块相关接口", tags = "创客端---个独管理模块相关接口")
public class IndividualEnterpriseMakerController {

    private IMakerService makerService;
    private IIndividualEnterpriseService individualEnterpriseService;

    @GetMapping("/individual-enterprise/list-by-maker")
    @ApiOperation(value = "查询当前创客的所有个独", notes = "查询当前创客的所有个独")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ibstate", value = "工商个独状态", paramType = "query", dataType = "string"),
    })
    public R listByMaker(Ibstate ibstate, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualEnterpriseService.listByMaker(query, makerEntity.getId(), ibstate);
    }

    @GetMapping("/individual-enterprise/year-month-money")
    @ApiOperation(value = "查询个独月度开票金额和年度开票金额", notes = "查询个独月度开票金额和年度开票金额")
    public R yearMonthMoney(@ApiParam(value = "个独编号", required = true) @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.yearMonthMoney(individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
    }

    @GetMapping("/individual-enterprise/find-by-id")
    @ApiOperation(value = "查询个独详情", notes = "查询个独详情")
    public R findById(@ApiParam(value = "个独编号", required = true) @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.findById(individualEnterpriseId);
    }

    @PostMapping("/individual-enterprise/save")
    @ApiOperation(value = "新增个独", notes = "新增个独")
    public R save(@Valid @RequestBody IndividualBusinessEnterpriseAddDTO individualBusinessEnterpriseAddDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualEnterpriseService.save(individualBusinessEnterpriseAddDto, makerEntity);
    }

}
