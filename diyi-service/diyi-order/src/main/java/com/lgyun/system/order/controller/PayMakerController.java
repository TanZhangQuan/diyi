package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.wrapper.PayMakerWrapper;
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
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Slf4j
@RestController
@RequestMapping("/paymaker")
@Validated
@AllArgsConstructor
@Api(value = "创客支付明细相关接口", tags = "创客支付明细相关接口")
public class PayMakerController {

    private IPayMakerService payMakerService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody PayMakerEntity payMaker) {
        return R.status(payMakerService.save(payMaker));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody PayMakerEntity payMaker) {
        return R.status(payMakerService.updateById(payMaker));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R detail(PayMakerEntity payMaker) {
        PayMakerEntity detail = payMakerService.getOne(Condition.getQueryWrapper(payMaker));
        return R.data(PayMakerWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(PayMakerEntity payMaker, Query query) {
        IPage<PayMakerEntity> pages = payMakerService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(payMaker));
        return R.data(PayMakerWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(payMakerService.removeByIds(Func.toLongList(ids)));
    }

}
