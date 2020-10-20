package com.lgyun.system.user.controller.maker;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 创客端---创客和外包企业的关联相关接口
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
@RestController
@RequestMapping("/maker/makerenterprise")
@Validated
@AllArgsConstructor
@Api(value = "创客端---创客和外包企业的关联相关接口", tags = "创客端---创客和外包企业的关联相关接口")
public class MakerEnterpriseController {

    private IMakerEnterpriseService makerEnterpriseService;
    private IEnterpriseService iEnterpriseService;
    private IMakerService iMakerService;

    @GetMapping("/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterpriseId", value = "商户id", paramType = "query", dataType = "long"),
    })
    @ApiOperation(value = "查询商户详情", notes = "查询商户详情")
    public R detail(Long enterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return iEnterpriseService.getEnterpriseId(enterpriseId, makerEntity.getId());

    }

    @GetMapping("/getMakerDetailed")
    @ApiOperation(value = "查询关联商户和创客的明细", notes = "查询关联商户和创客的明细")
    public R getMakerDetailed(BladeUser bladeUser, Long enterpriseId, WorkSheetType workSheetType, Query query) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();
        return  makerEnterpriseService.getMakerDetailed(Condition.getPage(query.setDescs("create_time")),makerEntity.getId(),enterpriseId, workSheetType);
    }


    @GetMapping("/selectMakerEnterprisePage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "relationshipType", value = "创客商户关系", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "查询关联商户和关注商户", notes = "查询关联商户和关注商户")
    public R selectMakerEnterprisePage(BladeUser bladeUser, RelationshipType relationshipType, Query query) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        IPage<MakerEnterpriseRelationVO> pages = makerEnterpriseService.selectMakerEnterprisePage(Condition.getPage(query.setDescs("create_time")), makerEntity.getId(), relationshipType);
        return R.data(pages);
    }

    @GetMapping("/getEnterpriseName")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterpriseName", value = "商户名字", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "通过商户名字查询", notes = "通过商户名字查询")
    public R getEnterpriseName(@ApiParam(value = "商户名字") @RequestParam(required = false) String enterpriseName) {
        return iEnterpriseService.getEnterpriseName(enterpriseName);
    }

    @PostMapping("/addOrCancelfollow")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterpriseId", value = "商户id", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "attribute", value = "1取消，2添加", paramType = "query", dataType = "int")
    })
    @ApiOperation(value = "添加关注或取消关注", notes = "添加关注或取消关注")
    public R addOrCancelfollow(Long enterpriseId, BladeUser bladeUser, Integer attribute) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerEnterpriseService.addOrCancelfollow(enterpriseId, makerEntity.getId(), attribute);
    }

}
