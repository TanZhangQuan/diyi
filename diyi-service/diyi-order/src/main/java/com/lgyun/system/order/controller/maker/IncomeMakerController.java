package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 创客端---收入管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/maker/income")
@Validated
@AllArgsConstructor
@Api(value = "创客端---收入管理模块相关接口", tags = "创客端---收入管理模块相关接口")
public class IncomeMakerController {

    private IUserClient iUserClient;
    private IWorksheetMakerService worksheetMakerService;

    @GetMapping("/query-worksheet-num-and-all-income")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份（可选）查询工单笔数和总收入金额", notes = "根据工单类型，创客类型，年份，月份（可选）查询工单笔数和总收入金额")
    public R queryWorksheetNumAndAllIncome(@ApiParam(value = "工单类型", required = true) @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                           @ApiParam(value = "工单创客类型", required = true) @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                           @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                           @ApiParam(value = "月份") @RequestParam(required = false) Long month, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryAllMoneyByYearMonth(worksheetType, makerType, makerEntity.getId(), year, month);
    }

    @GetMapping("/query-every-year-income")
    @ApiOperation(value = "根据工单类型，创客类型查询每年收入", notes = "根据工单类型，创客类型查询每年收入")
    public R queryEveryYearIncome(@ApiParam(value = "工单类型", required = true) @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                  @ApiParam(value = "工单创客类型", required = true) @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryMoneyByYear(worksheetType, makerType, makerEntity.getId());
    }

    @GetMapping("/query-every-month-income")
    @ApiOperation(value = "根据工单类型，创客类型，年份查询每月收入", notes = "根据工单类型，创客类型，年份查询每月收入")
    public R queryEveryMonthIncome(@ApiParam(value = "工单类型", required = true) @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                   @ApiParam(value = "工单创客类型", required = true) @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                   @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryMoneyByMonth(worksheetType, makerType, makerEntity.getId(), year);
    }

    @GetMapping("/query-maker-to-enterprise-all-income")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份（可选）查询创客对应商户的总收入金额", notes = "根据工单类型，创客类型，年份，月份（可选）查询创客对应商户的总收入金额")
    public R queryMakerToEnterpriseAllIncome(@ApiParam(value = "工单类型", required = true) @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                             @ApiParam(value = "工单创客类型", required = true) @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                             @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                             @ApiParam(value = "月份") @RequestParam(required = false) Long month, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryAllMoneyByYearMonthEnterprise(Condition.getPage(query.setDescs("create_time")), worksheetType, makerType, makerEntity.getId(), year, month);
    }

    @GetMapping("/query-detail-all-income")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份，商户编号（可选）查询明细总收入", notes = "根据工单类型，创客类型，年份，月份，商户编号（可选）查询明细总收入")
    public R queryDetailAllIncome(@ApiParam(value = "工单类型", required = true) @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                  @ApiParam(value = "工单创客类型", required = true) @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                  @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                  @ApiParam(value = "月份", required = true) @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                                  @ApiParam(value = "商户编号") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryAllMoneyDetailByYearMonth(worksheetType, makerType, makerEntity.getId(), year, month, enterpriseId);
    }

    @GetMapping("/query-income-detail")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份，商户编号（可选）查询收入明细", notes = "根据工单类型，创客类型，年份，月份，商户编号（可选）查询收入明细")
    public R queryIncomeDetail(@ApiParam(value = "工单类型", required = true) @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                               @ApiParam(value = "工单创客类型", required = true) @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                               @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                               @ApiParam(value = "月份", required = true) @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                               @ApiParam(value = "商户编号") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryMoneyDetailByYearMonth(Condition.getPage(query.setDescs("create_time")), worksheetType, makerType, makerEntity.getId(), year, month, enterpriseId);
    }

}
