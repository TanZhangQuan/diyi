package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.WorksheetAttentionEntity;
import com.lgyun.system.order.service.IWorksheetAttentionService;
import com.lgyun.system.order.vo.WorksheetAttentionVO;
import com.lgyun.system.order.wrapper.WorksheetAttentionWrapper;
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
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@RestController
@RequestMapping("/worksheetattention")
@Validated
@AllArgsConstructor
@Api(value = "工单信息相关接口", tags = "工单信息相关接口")
public class WorksheetAttentionController {

    private IWorksheetAttentionService worksheetAttentionService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody WorksheetAttentionEntity worksheetAttention) {
        return R.status(worksheetAttentionService.save(worksheetAttention));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody WorksheetAttentionEntity worksheetAttention) {
        return R.status(worksheetAttentionService.updateById(worksheetAttention));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R<WorksheetAttentionVO> detail(WorksheetAttentionEntity worksheetAttention) {
        WorksheetAttentionEntity detail = worksheetAttentionService.getOne(Condition.getQueryWrapper(worksheetAttention));
        return R.data(WorksheetAttentionWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R<IPage<WorksheetAttentionVO>> list(WorksheetAttentionEntity worksheetAttention, Query query) {
        IPage<WorksheetAttentionEntity> pages = worksheetAttentionService.page(Condition.getPage(query), Condition.getQueryWrapper(worksheetAttention));
        return R.data(WorksheetAttentionWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(worksheetAttentionService.removeByIds(Func.toLongList(ids)));
    }

}
