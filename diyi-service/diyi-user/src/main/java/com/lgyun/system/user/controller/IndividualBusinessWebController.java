package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.service.IIndividualBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/web/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "个体户相关接口(管理端)", tags = "个体户相关接口(管理端)")
public class IndividualBusinessWebController {

    private IIndividualBusinessService individualBusinessService;

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个体户年审信息", notes = "查询个体户年审信息")
    public R queryEnterpriseReportList(Query query, @ApiParam(value = "个体户ID") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {
        return individualBusinessService.queryEnterpriseReports(query, individualBusinessId);
    }

    @PostMapping("/remove-individual-business")
    @ApiOperation(value = "个体户逻辑删除", notes = "个体户逻辑删除")
    public R remove(@ApiParam(value = "个体户ID集合") @NotBlank(message = "请选择要删除的个体户") @RequestParam(required = false) String ids) {
        return R.status(individualBusinessService.removeByIds(Func.toLongList(ids)));
    }

    @PostMapping("/update-individual-business")
    @ApiOperation(value = "修改个体户", notes = "修改个体户")
    public R updateIndividualBusiness(@Valid @RequestBody IndividualBusinessEntity individualBusiness) {
        return R.status(individualBusinessService.updateById(individualBusiness));
    }

}