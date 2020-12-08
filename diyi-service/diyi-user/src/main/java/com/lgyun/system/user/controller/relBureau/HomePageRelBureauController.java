package com.lgyun.system.user.controller.relBureau;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.service.IRelBureauService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rel-bureau/home-page")
@Validated
@AllArgsConstructor
@Api(value = "相关局端---首页管理模块相关接口", tags = "相关局端---首页管理模块相关接口")
public class HomePageRelBureauController {

    private IRelBureauService relBureauService;

    @GetMapping("/query-rel-bureau-info")
    @ApiOperation(value = "查询相关局基础信息", notes = "查询相关局基础信息")
    public R queryRelBureauInfo(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return relBureauService.queryRelBureauInfo(relBureauEntity.getId());
    }

}
