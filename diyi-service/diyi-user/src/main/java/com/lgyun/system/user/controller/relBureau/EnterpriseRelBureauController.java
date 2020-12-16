package com.lgyun.system.user.controller.relBureau;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.EnterpriseListDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.service.IEnterpriseService;
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
@RequestMapping("/rel-bureau/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "相关局端---商户管理模块相关接口", tags = "相关局端---商户管理模块相关接口")
public class EnterpriseRelBureauController {

    private IRelBureauService relBureauService;
    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询渠道商的所有商户", notes = "查询渠道商的所有商户")
    public R queryEnterpriseList(EnterpriseListDTO enterpriseListDTO, Query query, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return enterpriseService.queryEnterpriseList(null, relBureauEntity.getId(), enterpriseListDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-enterprise-detail")
    @ApiOperation(value = "查询商户详情", notes = "查询商户详情")
    public R queryEnterpriseDetail(@ApiParam(value = "商户") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseDetailAgentMain(enterpriseId);
    }

}
