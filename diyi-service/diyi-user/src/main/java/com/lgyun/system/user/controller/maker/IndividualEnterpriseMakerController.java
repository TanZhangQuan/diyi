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

        return individualEnterpriseService.listByMaker(query, makerEntity.getId(), ibstate);
    }

    @GetMapping("/query-individual-enterprise-month-and-year-invoice-money")
    @ApiOperation(value = "查询个独月度开票金额和年度开票金额", notes = "查询个独月度开票金额和年度开票金额")
    public R queryIndividualEnterpriseMonthAndYearInvoiceMoney(@ApiParam(value = "个独", required = true) @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.yearMonthMoney(individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
    }

    @GetMapping("/query-individual-enterprise-detail")
    @ApiOperation(value = "查询个独详情", notes = "查询个独详情")
    public R queryIndividualEnterpriseDetail(@ApiParam(value = "个独", required = true) @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.findById(individualEnterpriseId);
    }

    @PostMapping("/save-individual-enterprise")
    @ApiOperation(value = "新增个独", notes = "新增个独")
    public R saveIndividualEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseAddDTO individualBusinessEnterpriseAddDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualEnterpriseService.save(individualBusinessEnterpriseAddDto, makerEntity);
    }

}
