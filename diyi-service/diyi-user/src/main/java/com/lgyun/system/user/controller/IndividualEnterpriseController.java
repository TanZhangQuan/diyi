package com.lgyun.system.user.controller;

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
 * 控制器
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@RestController
@RequestMapping("/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "个独相关接口", tags = "个独相关接口")
public class IndividualEnterpriseController {

    private IIndividualEnterpriseService individualEnterpriseService;
    private IMakerService iMakerService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody IndividualBusinessEnterpriseAddDTO individualBusinessEnterpriseAddDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualEnterpriseService.save(individualBusinessEnterpriseAddDto, makerEntity);
    }

    @GetMapping("/list-by-maker")
    @ApiOperation(value = "查询当前创客的所有个独", notes = "查询当前创客的所有个独")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ibstate", value = "工商个独状态", paramType = "query", dataType = "string"),
    })
    public R listByMaker(Ibstate ibstate, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualEnterpriseService.listByMaker(query, makerEntity.getId(), ibstate);
    }

    @GetMapping("/find-by-id")
    @ApiOperation(value = "查询个独详情", notes = "查询个独详情")
    public R findById(@ApiParam(value = "个独编号") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.findById(individualEnterpriseId);
    }

    @GetMapping("/year-month-money")
    @ApiOperation(value = "查询个独月度开票金额和年度开票金额", notes = "查询个独月度开票金额和年度开票金额")
    public R yearMonthMoney(@ApiParam(value = "个独编号") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.yearMonthMoney(individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
    }

}
