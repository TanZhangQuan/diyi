package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.MakerTaxRecordEntity;
import com.lgyun.system.order.service.IMakerTaxRecordService;
import com.lgyun.system.order.vo.MakerTaxRecordVO;
import com.lgyun.system.order.wrapper.MakerTaxRecordWrapper;
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
@RequestMapping("/order/makertaxrecord")
@Validated
@AllArgsConstructor
@Api(value = "创客单张完税证明信息相关接口", tags = "创客单张完税证明信息相关接口")
public class MakerTaxRecordController {

    private IMakerTaxRecordService makerTaxRecordService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody MakerTaxRecordEntity makerTaxRecord) {
        return R.status(makerTaxRecordService.save(makerTaxRecord));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody MakerTaxRecordEntity makerTaxRecord) {
        return R.status(makerTaxRecordService.updateById(makerTaxRecord));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R<MakerTaxRecordVO> detail(MakerTaxRecordEntity makerTaxRecord) {
        MakerTaxRecordEntity detail = makerTaxRecordService.getOne(Condition.getQueryWrapper(makerTaxRecord));
        return R.data(MakerTaxRecordWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R<IPage<MakerTaxRecordVO>> list(MakerTaxRecordEntity makerTaxRecord, Query query) {
        IPage<MakerTaxRecordEntity> pages = makerTaxRecordService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(makerTaxRecord));
        return R.data(MakerTaxRecordWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(makerTaxRecordService.removeByIds(Func.toLongList(ids)));
    }

}
