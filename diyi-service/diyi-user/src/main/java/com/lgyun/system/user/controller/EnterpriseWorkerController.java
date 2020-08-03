package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.wrapper.EnterpriseWorkerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Slf4j
@RestController
@RequestMapping("/enterpriseworker")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class EnterpriseWorkerController {

    private IEnterpriseWorkerService enterpriseWorkerService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody EnterpriseWorkerEntity enterpriseWorker) {
        return R.status(enterpriseWorkerService.save(enterpriseWorker));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody EnterpriseWorkerEntity enterpriseWorker) {
        return R.status(enterpriseWorkerService.updateById(enterpriseWorker));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R detail(EnterpriseWorkerEntity enterpriseWorker) {
        EnterpriseWorkerEntity detail = enterpriseWorkerService.getOne(Condition.getQueryWrapper(enterpriseWorker));
        return R.data(EnterpriseWorkerWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(EnterpriseWorkerEntity enterpriseWorker, Query query) {
        IPage<EnterpriseWorkerEntity> pages = enterpriseWorkerService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(enterpriseWorker));
        return R.data(EnterpriseWorkerWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(enterpriseWorkerService.removeByIds(Func.toLongList(ids)));
    }

}
