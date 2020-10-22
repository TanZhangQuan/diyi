package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
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
@RequestMapping("/web/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "个独相关接口(管理端)", tags = "个独相关接口(管理端)")
public class IndividualEnterpriseWebController {

    private IIndividualEnterpriseService individualEnterpriseService;

    @GetMapping("/query-enterprise-reports")
    @ApiOperation(value = "查询个独年审信息", notes = "查询个独年审信息")
    public R queryEnterpriseReports(Query query, @ApiParam(value = "个独ID", required = true) @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.queryEnterpriseReports(query, individualEnterpriseId);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "个独逻辑删除", notes = "个独逻辑删除")
    public R remove(@ApiParam(value = "个独ID集合", required = true) @NotBlank(message = "请选择要删除的个独") @RequestParam(required = false) String ids) {
        return R.status(individualEnterpriseService.removeByIds(Func.toLongList(ids)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改个独信息", notes = "修改个独信息")
    public R update(@Valid @RequestBody IndividualEnterpriseEntity individualEnterprise) {
        return R.status(individualEnterpriseService.updateById(individualEnterprise));
    }

}
