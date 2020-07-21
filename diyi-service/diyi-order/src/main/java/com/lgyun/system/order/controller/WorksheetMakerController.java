package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.wrapper.WorksheetMakerWrapper;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-18 19:44:52
 */
@Slf4j
@RestController
@RequestMapping("/user/worksheetmaker")
@Validated
@AllArgsConstructor
@Api(value = "工单创客关联相关接口", tags = "工单创客关联相关接口")
public class WorksheetMakerController {

    private IWorksheetMakerService worksheetMakerService;
    private IUserClient iUserClient;

    //	@PostMapping("/save")
//	@ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody WorksheetMakerEntity worksheetMaker) {
        return R.status(worksheetMakerService.save(worksheetMaker));
    }

    //	@PostMapping("/update")
//	@ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody WorksheetMakerEntity worksheetMaker) {
        return R.status(worksheetMakerService.updateById(worksheetMaker));
    }

    //	@GetMapping("/detail")
//	@ApiOperation(value = "详情", notes = "详情")
    public R<WorksheetMakerVO> detail(WorksheetMakerEntity worksheetMaker) {
        WorksheetMakerEntity detail = worksheetMakerService.getOne(Condition.getQueryWrapper(worksheetMaker));
        return R.data(WorksheetMakerWrapper.build().entityVO(detail));
    }

    //	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "分页")
    public R<IPage<WorksheetMakerVO>> list(WorksheetMakerEntity worksheetMaker, Query query) {
        IPage<WorksheetMakerEntity> pages = worksheetMakerService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(worksheetMaker));
        return R.data(WorksheetMakerWrapper.build().pageVO(pages));
    }

    //	@PostMapping("/remove")
//	@ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(worksheetMakerService.removeByIds(Func.toLongList(ids)));
    }

    /**
     * 根据工单类型，创客类型，年份或月份查询总收入
     */
    @GetMapping("/query-all-money-by-year-month")
    @ApiOperation(value = "根据工单类型，创客类型，年份或月份查询收入", notes = "根据工单类型，创客类型，年份或月份查询收入")
    public R<AllIncomeYearMonthVO> queryAllMoneyByYearMonth(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                                            @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                                            @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                                            @ApiParam(value = "月份") @RequestParam(required = false) Long month,
                                                            BladeUser bladeUser) {
        try{
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return worksheetMakerService.queryAllMoneyByYearMonth(worksheetType, makerType, makerEntity.getId(), year, month);
        }catch (Exception e){
            log.error("根据工单类型，创客类型，年份或月份查询收入异常", e);
        }
        return R.fail("查询失败");
    }

    /**
     * 根据工单类型，创客类型查询每年收入
     */
    @GetMapping("/query-money-by-year")
    @ApiOperation(value = "根据工单类型，创客类型查询年收入", notes = "根据工单类型，创客类型查询年收入")
    public R<IncomeYearVO> queryMoneyByYear(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                            @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                            BladeUser bladeUser) {
        try{
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return worksheetMakerService.queryMoneyByYear(worksheetType, makerType, makerEntity.getId());
        }catch (Exception e){
            log.error("根据工单类型，创客类型查询年收入异常", e);
        }
        return R.fail("查询失败");
    }

    /**
     * 根据工单类型，创客类型，年份查询每月收入
     */
    @GetMapping("/query-money-by-month")
    @ApiOperation(value = "根据工单类型，创客类型，年份查询每月收入", notes = "根据工单类型，创客类型，年份查询每月收入")
    public R<IncomeMonthVO> queryMoneyByMonth(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                              @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                              @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                              BladeUser bladeUser) {
        try{
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return worksheetMakerService.queryMoneyByMonth(worksheetType, makerType, makerEntity.getId(), year);
        }catch (Exception e){
            log.error("根据工单类型，创客类型，年份查询每月收入异常", e);
        }
        return R.fail("查询失败");
    }

    /**
     * 根据工单类型，创客类型，年份或月份查询创客对应商户的收入
     */
    @GetMapping("/query-all-money-by-year-month-enterprise")
    @ApiOperation(value = "根据工单类型，创客类型，年份或月份查询创客对应商户的收入", notes = "根据工单类型，创客类型，年份或月份查询创客对应商户的收入")
    public R<IPage<AllIncomeYearMonthEnterpriseVO>> queryAllMoneyByYearMonthEnterprise(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                                                                @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                                                                @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                                                                @ApiParam(value = "月份") @RequestParam(required = false) Long month,
                                                                                BladeUser bladeUser, Query query) {
        try{
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return worksheetMakerService.queryAllMoneyByYearMonthEnterprise(Condition.getPage(query), worksheetType, makerType, makerEntity.getId(), year, month);
        }catch (Exception e){
            log.error("根据工单类型，创客类型，年份或月份查询创客对应商户的收入异常", e);
        }
        return R.fail("查询失败");
    }

    /**
     * 根据工单类型，创客类型，年份，月份，商户编号（可选）查询总收入
     */
    @GetMapping("/query-all-money-detail-by-year-month")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份查询收入明细", notes = "根据工单类型，创客类型，年份，月份查询收入明细")
    public R<BigDecimal> queryAllMoneyDetailByYearMonth(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                                        @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                                        @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                                        @ApiParam(value = "月份") @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                                                        @ApiParam(value = "商户编号") @RequestParam(required = false) Long enterpriseId,
                                                        BladeUser bladeUser) {
        try{
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return worksheetMakerService.queryAllMoneyDetailByYearMonth(worksheetType, makerType, makerEntity.getId(), year, month, enterpriseId);
        }catch (Exception e){
            log.error("根据工单类型，创客类型，年份，月份查询收入明细异常", e);
        }
        return R.fail("查询失败");
    }

    /**
     * 根据工单类型，创客类型，年份，月份，商户编号（可选）查询收入明细
     */
    @GetMapping("/query-money-detail-by-year-month")
    @ApiOperation(value = "根据工单类型，创客类型，年份，月份查询收入明细", notes = "根据工单类型，创客类型，年份，月份查询收入明细")
    public R<IPage<IncomeDetailYearMonthVO>> queryMoneyDetailByYearMonth(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
                                                            @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
                                                            @ApiParam(value = "年份") @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                                            @ApiParam(value = "月份") @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                                                            @ApiParam(value = "商户编号") @RequestParam(required = false) Long enterpriseId,
                                                            BladeUser bladeUser, Query query) {
        try{
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return worksheetMakerService.queryMoneyDetailByYearMonth(Condition.getPage(query.setDescs("create_time")), worksheetType, makerType, makerEntity.getId(), year, month, enterpriseId);
        }catch (Exception e){
            log.error("根据工单类型，创客类型，年份，月份查询收入明细异常", e);
        }
        return R.fail("查询失败");
    }

}
