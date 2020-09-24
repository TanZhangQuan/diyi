package com.lgyun.system.order.controller;

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
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-18 19:44:52
 */
@RestController
@RequestMapping("/worksheetmaker")
@Validated
@AllArgsConstructor
@Api(value = "工单创客关联相关接口", tags = "工单创客关联相关接口")
public class WorksheetMakerController {

    private IWorksheetMakerService worksheetMakerService;
    private IUserClient iUserClient;

    @GetMapping("/query-all-money-by-year-month")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份（可选）查询工单笔数和总收入金额", notes = "根据工单类型，创客类型，年份，月份（可选）查询工单笔数和总收入金额")
    public R queryAllMoneyByYearMonth(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                      @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                      @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                      @ApiParam(value = "月份") @RequestParam(required = false) Long month,
                                      BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryAllMoneyByYearMonth(worksheetType, makerType, makerEntity.getId(), year, month);
    }

    @GetMapping("/query-money-by-year")
    @ApiOperation(value = "根据工单类型，创客类型查询每年收入", notes = "根据工单类型，创客类型查询每年收入")
    public R queryMoneyByYear(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                              @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                              BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryMoneyByYear(worksheetType, makerType, makerEntity.getId());
    }

    @GetMapping("/query-money-by-month")
    @ApiOperation(value = "根据工单类型，创客类型，年份查询每月收入", notes = "根据工单类型，创客类型，年份查询每月收入")
    public R queryMoneyByMonth(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                               @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                               @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                               BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryMoneyByMonth(worksheetType, makerType, makerEntity.getId(), year);
    }

    @GetMapping("/query-all-money-by-year-month-enterprise")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份（可选）查询创客对应商户的总收入金额", notes = "根据工单类型，创客类型，年份，月份（可选）查询创客对应商户的总收入金额")
    public R queryAllMoneyByYearMonthEnterprise(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                                @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                                @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                                @ApiParam(value = "月份") @RequestParam(required = false) Long month,
                                                BladeUser bladeUser, Query query) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryAllMoneyByYearMonthEnterprise(Condition.getPage(query.setDescs("create_time")), worksheetType, makerType, makerEntity.getId(), year, month);
    }

    @GetMapping("/query-all-money-detail-by-year-month")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份，商户编号（可选）查询明细总收入", notes = "根据工单类型，创客类型，年份，月份，商户编号（可选）查询明细总收入")
    public R queryAllMoneyDetailByYearMonth(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                            @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                            @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                            @ApiParam(value = "月份") @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                                            @ApiParam(value = "商户编号") @RequestParam(required = false) Long enterpriseId,
                                            BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryAllMoneyDetailByYearMonth(worksheetType, makerType, makerEntity.getId(), year, month, enterpriseId);
    }

    @GetMapping("/query-money-detail-by-year-month")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份，商户编号（可选）查询收入明细", notes = "根据工单类型，创客类型，年份，月份，商户编号（可选）查询收入明细")
    public R queryMoneyDetailByYearMonth(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                         @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                         @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                         @ApiParam(value = "月份") @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                                         @ApiParam(value = "商户编号") @RequestParam(required = false) Long enterpriseId,
                                         BladeUser bladeUser, Query query) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetMakerService.queryMoneyDetailByYearMonth(Condition.getPage(query.setDescs("create_time")), worksheetType, makerType, makerEntity.getId(), year, month, enterpriseId);
    }

    @GetMapping("/get_by_pay_enterprise_id")
    @ApiOperation(value = "根据支付清单ID查询创客工单关联", notes = "根据支付清单ID查询创客工单关联")
    public R getByPayEnterpriseId(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query) {
        return worksheetMakerService.getByPayEnterpriseId(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

}
