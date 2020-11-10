package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maker/home-page")
@Validated
@AllArgsConstructor
@Api(value = "创客端---首页管理模块相关接口", tags = "创客端---首页管理模块相关接口")
public class HomePageMakerController {

    private IUserClient userClient;
    private IPayMakerService payMakerService;

    @GetMapping("/query-enterprise-num-and-income")
    @ApiOperation(value = "查询当前创客关联商户数和收入情况", notes = "查询当前创客关联商户数和收入情况")
    public R queryEnterpriseNumAndIncome(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payMakerService.getEnterpriseNumIncome(makerEntity.getId());
    }

}
