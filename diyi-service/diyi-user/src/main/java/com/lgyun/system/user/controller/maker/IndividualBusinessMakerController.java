package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
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
 * 控制器
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@RestController
@RequestMapping("/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "个体户相关接口", tags = "个体户相关接口")
public class IndividualBusinessMakerController {

    private IIndividualBusinessService individualBusinessService;
    private IMakerService iMakerService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody IndividualBusinessEnterpriseAddDto individualBusinessEnterpriseAddDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualBusinessService.save(individualBusinessEnterpriseAddDto, makerEntity);
    }

    @GetMapping("/list-by-maker")
    @ApiOperation(value = "查询当前创客的所有个体户", notes = "查询当前创客的所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ibstate", value = "工商个体户状态", paramType = "query", dataType = "string"),
    })
    public R listByMaker(Ibstate ibstate, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return individualBusinessService.listByMaker(query, makerEntity.getId(), ibstate);
    }

    @GetMapping("/find-by-id")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R findById(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {
        return individualBusinessService.findById(individualBusinessId);
    }

    @GetMapping("/year-month-money")
    @ApiOperation(value = "查询个体户月度开票金额和年度开票金额", notes = "查询个体户月度开票金额和年度开票金额")
    public R yearMonthMoney(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {
        return individualBusinessService.yearMonthMoney(individualBusinessId, InvoicePeopleType.INDIVIDUALBUSINESS);
    }

}
