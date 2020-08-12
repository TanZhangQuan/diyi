package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.wrapper.EnterpriseReportWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 年度申报管理表控制器
 *
 * @author liangfeihu
 * @since 2020-08-12 14:47:56
 */
@Slf4j
@RestController
@RequestMapping("/enterprise_report")
@Validated
@AllArgsConstructor
@Api(value = "年度申报管理表相关接口", tags = "年度申报管理表相关接口")
public class EnterpriseReportController {

    private IEnterpriseReportService enterpriseReportService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody EnterpriseReportEntity enterpriseReport) {
        return R.status(enterpriseReportService.save(enterpriseReport));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody EnterpriseReportEntity enterpriseReport) {
        return R.status(enterpriseReportService.updateById(enterpriseReport));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R detail(EnterpriseReportEntity enterpriseReport) {
        EnterpriseReportEntity detail = enterpriseReportService.getOne(Condition.getQueryWrapper(enterpriseReport));
        return R.data(EnterpriseReportWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(EnterpriseReportEntity enterpriseReport, Query query) {
        IPage<EnterpriseReportEntity> pages = enterpriseReportService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(enterpriseReport));
        return R.data(EnterpriseReportWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(enterpriseReportService.removeByIds(Func.toLongList(ids)));
    }

}
