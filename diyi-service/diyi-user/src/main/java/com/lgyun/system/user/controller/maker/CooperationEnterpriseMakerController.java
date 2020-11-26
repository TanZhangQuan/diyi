package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.enumeration.WorksheetType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maker/cooperation-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "创客端---关联商户管理模块相关接口", tags = "创客端---关联商户管理模块相关接口")
public class CooperationEnterpriseMakerController {

    private IMakerService makerService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterpriseId", value = "商户", paramType = "query", dataType = "long"),
    })
    @ApiOperation(value = "查询商户详情", notes = "查询商户详情")
    public R queryEnterpriseDetail(Long enterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return enterpriseService.getEnterpriseId(enterpriseId, makerEntity.getId());
    }

    @GetMapping("/query-maker-to-enterprise-transaction")
    @ApiOperation(value = "查询关联商户和创客的明细", notes = "查询关联商户和创客的明细")
    public R queryMakerToEnterpriseTransaction(Long enterpriseId, WorksheetType worksheetType, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerEnterpriseService.getMakerDetailed(Condition.getPage(query.setDescs("pm.create_time")), makerEntity.getId(), enterpriseId, worksheetType);
    }

    @GetMapping("/query-relevance-or-attention-enterprise-list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "relationshipType", value = "创客商户关系", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "查询关联商户和关注商户", notes = "查询关联商户和关注商户")
    public R queryRelevanceOrAttentionEnterpriseList(BladeUser bladeUser, RelationshipType relationshipType, Query query) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerEnterpriseService.selectMakerEnterprisePage(makerEntity.getId(), relationshipType, Condition.getPage(query.setDescs("me.create_time")));
    }

    @GetMapping("/query-enterprise")
    @ApiOperation(value = "通过商户名字查询商户", notes = "通过商户名字查询商户")
    public R queryEnterprise(@ApiParam(value = "商户名字") @RequestParam(required = false) String enterpriseName, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.getEnterpriseName(enterpriseName);
    }

    @PostMapping("/add-or-cancel-follow")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterpriseId", value = "商户id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "attribute", value = "1取消，2添加", paramType = "query", dataType = "int")
    })
    @ApiOperation(value = "添加关注或取消关注", notes = "添加关注或取消关注")
    public R addOrCancelFollow(Long enterpriseId, BladeUser bladeUser, Integer attribute) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerEnterpriseService.addOrCancelfollow(enterpriseId, makerEntity.getId(), attribute);
    }

}
