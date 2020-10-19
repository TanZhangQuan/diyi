package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创客端---首页管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
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

    @GetMapping("/query-enterprise-num-and-income")
    @ApiOperation(value = "查询当前创客关联商户数和收入情况", notes = "查询当前创客关联商户数和收入情况")
    public R queryEnterpriseNumAndIncome(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.getEnterpriseNumIncome(makerEntity.getId());
    }

}
