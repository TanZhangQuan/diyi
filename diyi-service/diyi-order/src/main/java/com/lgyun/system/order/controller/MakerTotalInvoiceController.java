package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.MakerTotalInvoiceEntity;
import com.lgyun.system.order.service.IMakerTotalInvoiceService;
import com.lgyun.system.order.wrapper.MakerTotalInvoiceWrapper;
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
 * @since 2020-07-18 20:49:12
 */
@Slf4j
@RestController
@RequestMapping("/order/makertotalinvoice")
@Validated
@AllArgsConstructor
@Api(value = "创客汇总代开发票相关接口", tags = "创客汇总代开发票相关接口")
public class MakerTotalInvoiceController {

    private IMakerTotalInvoiceService makerTotalInvoiceService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody MakerTotalInvoiceEntity makerTotalInvoice) {
        return R.status(makerTotalInvoiceService.save(makerTotalInvoice));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody MakerTotalInvoiceEntity makerTotalInvoice) {
        return R.status(makerTotalInvoiceService.updateById(makerTotalInvoice));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R detail(MakerTotalInvoiceEntity makerTotalInvoice) {
        MakerTotalInvoiceEntity detail = makerTotalInvoiceService.getOne(Condition.getQueryWrapper(makerTotalInvoice));
        return R.data(MakerTotalInvoiceWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(MakerTotalInvoiceEntity makerTotalInvoice, Query query) {
        IPage<MakerTotalInvoiceEntity> pages = makerTotalInvoiceService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(makerTotalInvoice));
        return R.data(MakerTotalInvoiceWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(makerTotalInvoiceService.removeByIds(Func.toLongList(ids)));
    }

}
