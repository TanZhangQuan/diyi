package com.lgyun.system.order.controller.maker;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.TimeType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
@RequestMapping("/maker/income")
@Validated
@AllArgsConstructor
@Api(value = "创客端---收入管理模块相关接口", tags = "创客端---收入管理模块相关接口")
public class IncomeMakerController {

    private IUserClient userClient;
    private IPayMakerService payMakerService;
    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;

    @GetMapping("/query-total-sub-num-and-all-income")
    @ApiOperation(value = "根据创客类型，年份，月份（可选）查询当前创客总包+分包的笔数和总收入金额", notes = "根据创客类型，年份，月份（可选）查询当前创客总包+分包的笔数和总收入金额")
    public R queryTotalSubNumAndAllIncome(@ApiParam(value = "创客类型", required = true) @NotNull(message = "请选择创客类型") @RequestParam(required = false) MakerType makerType,
                                          @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                          @ApiParam(value = "月份") @RequestParam(required = false) Long month, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payMakerService.queryTotalSubNumAndAllIncome(makerType, makerEntity.getId(), year, month);
    }

    @GetMapping("/query-crowd-num-and-all-income")
    @ApiOperation(value = "根据开票人身份类别，年份，月份（可选）查询当前创客众包的笔数和总收入金额", notes = "根据开票人身份类别，年份，月份（可选）查询当前创客众包的笔数和总收入金额")
    public R queryCrowdNumAndAllIncome(@ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请选择开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                       @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                       @ApiParam(value = "月份") @RequestParam(required = false) Long month, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return selfHelpInvoiceDetailService.queryCrowdNumAndAllIncome(makerType, makerEntity.getId(), year, month);
    }

    @GetMapping("/query-total-sub-maker-income")
    @ApiOperation(value = "查询创客总包+分包收入", notes = "查询创客总包+分包收入")
    public R queryTotalSubMakerIncome(@ApiParam(value = "创客类型", required = true) @NotNull(message = "请选择创客类型") @RequestParam(required = false) MakerType makerType,
                                      @ApiParam(value = "时间类型", required = true) @NotNull(message = "请选择时间类型") @RequestParam(required = false) TimeType timeType,
                                      @ApiParam(value = "年份", required = true) @JsonFormat(pattern = "yyyy")
                                      @DateTimeFormat(pattern = "yyyy") @RequestParam(required = false) Date year,
                                      @ApiParam(value = "开始时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                                      @ApiParam(value = "结束时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payMakerService.queryTotalSubMakerIncome(makerType, makerEntity.getId(), timeType, year, beginDate, endDate);
    }

    @GetMapping("/query-crowd-maker-income")
    @ApiOperation(value = "查询创客众包/众采收入", notes = "查询创客众包/众采收入")
    public R queryCrowdMakerIncome(@ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请选择开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                       @ApiParam(value = "时间类型", required = true) @NotNull(message = "请选择时间类型") @RequestParam(required = false) TimeType timeType,
                                       @ApiParam(value = "年份", required = true) @JsonFormat(pattern = "yyyy")
                                       @DateTimeFormat(pattern = "yyyy") @RequestParam(required = false) Date year,
                                       @ApiParam(value = "开始时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                                       @ApiParam(value = "结束时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return selfHelpInvoiceDetailService.queryCrowdMakerIncome(makerType, makerEntity.getId(), timeType, year, beginDate, endDate);
    }

    @GetMapping("/query-maker-to-enterprise-total-sub-income")
    @ApiOperation(value = "根据创客类型，年份，月份（可选）查询创客对应商户总包+分包的收入金额", notes = "根据创客类型，年份，月份（可选）查询创客对应商户总包+分包的收入金额")
    public R queryMakerToEnterpriseTotalSubIncome(@ApiParam(value = "创客类型", required = true) @NotNull(message = "请选择创客类型") @RequestParam(required = false) MakerType makerType,
                                                  @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                                  @ApiParam(value = "月份") @RequestParam(required = false) Long month, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payMakerService.queryMakerToEnterpriseTotalSubIncome(makerType, makerEntity.getId(), year, month, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-maker-to-enterprise-crowd-income")
    @ApiOperation(value = "根据开票人身份类别，年份，月份（可选）查询创客对应商户众包的收入金额", notes = "根据开票人身份类别，年份，月份（可选）查询创客对应商户众包的收入金额")
    public R queryMakerToEnterpriseCrowdIncome(@ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请选择开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                               @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                               @ApiParam(value = "月份") @RequestParam(required = false) Long month, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return selfHelpInvoiceDetailService.queryMakerToEnterpriseCrowdIncome(makerType, makerEntity.getId(), year, month, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-total-sub-income-detail")
    @ApiOperation(value = "根据创客类型，年份，月份，商户（可选）查询总包+分包收入明细", notes = "根据创客类型，年份，月份，商户（可选）查询总包+分包收入明细")
    public R queryTotalSubIncomeDetail(@ApiParam(value = "创客类型", required = true) @NotNull(message = "请选择创客类型") @RequestParam(required = false) MakerType makerType,
                                       @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                       @ApiParam(value = "月份", required = true) @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                                       @ApiParam(value = "商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payMakerService.queryTotalSubIncomeDetail(makerType, makerEntity.getId(), year, month, enterpriseId, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-crowd-income-detail")
    @ApiOperation(value = "根据开票人身份类别，年份，月份，商户（可选）查询众包收入明细", notes = "根据开票人身份类别，年份，月份，商户（可选）查询众包收入明细")
    public R queryCrowdIncomeDetail(@ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请选择开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                    @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                    @ApiParam(value = "月份", required = true) @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                                    @ApiParam(value = "商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return selfHelpInvoiceDetailService.queryCrowdIncomeDetail(makerType, makerEntity.getId(), year, month, enterpriseId, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-total-sub-detail-all-income")
    @ApiOperation(value = "根据创客类型，年份，月份，商户（可选）查询总包+分包明细的总收入", notes = "根据创客类型，年份，月份，商户（可选）查询总包+分包明细的总收入")
    public R queryTotalSubDetailAllIncome(@ApiParam(value = "创客类型", required = true) @NotNull(message = "请选择创客类型") @RequestParam(required = false) MakerType makerType,
                                          @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                          @ApiParam(value = "月份", required = true) @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                                          @ApiParam(value = "商户") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payMakerService.queryTotalSubDetailAllIncome(makerType, makerEntity.getId(), year, month, enterpriseId);
    }

    @GetMapping("/query-crowd-detail-all-income")
    @ApiOperation(value = "根据开票人身份类别，年份，月份，商户（可选）查询众包明细的总收入", notes = "根据开票人身份类别，年份，月份，商户（可选）查询众包明细的总收入")
    public R queryCrowdDetailAllIncome(@ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请选择开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                       @ApiParam(value = "年份", required = true) @NotNull(message = "请选择年份") @RequestParam(required = false) Long year,
                                       @ApiParam(value = "月份", required = true) @NotNull(message = "请选择月份") @RequestParam(required = false) Long month,
                                       @ApiParam(value = "商户") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return selfHelpInvoiceDetailService.queryCrowdDetailAllIncome(makerType, makerEntity.getId(), year, month, enterpriseId);
    }

}
