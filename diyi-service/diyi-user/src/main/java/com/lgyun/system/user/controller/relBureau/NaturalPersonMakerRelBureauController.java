package com.lgyun.system.user.controller.relBureau;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.service.IRelBureauService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/rel-bureau/natural-person-maker")
@Validated
@AllArgsConstructor
@Api(value = "相关局端---自然人创客管理模块相关接口", tags = "相关局端---自然人创客管理模块相关接口")
public class NaturalPersonMakerRelBureauController {

    private IMakerService makerService;
    private IRelBureauService relBureauService;

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询所有创客", notes = "查询所有创客")
    public R queryMakerList(@ApiParam(value = "创客姓名/手机号/身份证号") @RequestParam(required = false) String keyword, Query query, BladeUser bladeUser) {
        ///查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return makerService.queryMakerList(null, null, relBureauEntity.getId(), null, null, keyword, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-maker-detail")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情")
    public R queryMakerDetail(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) {
        ///查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.queryMakerDetail(makerId);
    }

}
