package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 创客端---个体户管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/maker/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "创客端---个体户管理模块相关接口", tags = "创客端---个体户管理模块相关接口")
public class IndividualBusinessMakerController {

    private IIndividualBusinessService individualBusinessService;
    private IMakerService iMakerService;

    @GetMapping("/query-individual-business-list")
    @ApiOperation(value = "查询当前创客的所有个体户", notes = "查询当前创客的所有个体户")
    public R queryIndividualBusinessList(@ApiParam(value = "个体户状态") @RequestParam(required = false) Ibstate ibstate, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualBusinessService.listByMaker(query, makerEntity.getId(), ibstate);
    }

    @GetMapping("/query-individual-business-month-and-year-invoice-money")
    @ApiOperation(value = "查询个体户月度开票金额和年度开票金额", notes = "查询个体户月度开票金额和年度开票金额")
    public R queryIndividualBusinessMonthAndYearInvoiceMoney(@ApiParam(value = "个体户", required = true) @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.yearMonthMoney(individualBusinessId, InvoicePeopleType.INDIVIDUALBUSINESS);
    }

    @GetMapping("/query-individual-business-detail")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R findById(@ApiParam(value = "个体户", required = true) @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.findById(individualBusinessId);
    }

    @PostMapping("/save-individual-business")
    @ApiOperation(value = "新增个体户", notes = "新增个体户")
    public R saveIndividualBusiness(@Valid @RequestBody IndividualBusinessEnterpriseAddDTO individualBusinessEnterpriseAddDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualBusinessService.save(individualBusinessEnterpriseAddDto, makerEntity);
    }

}
