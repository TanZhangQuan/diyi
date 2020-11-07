package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.UpdateMakerDeatilDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/maker/home-page")
@Validated
@AllArgsConstructor
@Api(value = "创客端---首页管理模块相关接口", tags = "创客端---首页管理模块相关接口")
public class HomePageMakerController {

    private IMakerService makerService;

    @GetMapping("/query-maker-info")
    @ApiOperation(value = "查询当前创客基本信息", notes = "查询当前创客基本信息")
    public R queryMakerInfo(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.queryMakerInfo(makerEntity.getId());
    }

    @GetMapping("/query-current-maker-detail")
    @ApiOperation(value = "查询当前创客详情", notes = "查询当前创客详情")
    public R queryCurrentMakerDetail(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.queryCurrentMakerDetail(makerEntity.getId());
    }

    @PostMapping("/update-maker-detail")
    @ApiOperation(value = "修改创客详情", notes = "修改创客详情")
    public R updateMakerDetail(@Valid @RequestBody UpdateMakerDeatilDTO updateMakerDeatilDTO, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.updateMakerDetail(updateMakerDeatilDTO, makerEntity);
    }

}
