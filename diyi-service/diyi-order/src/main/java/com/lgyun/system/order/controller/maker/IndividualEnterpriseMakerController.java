package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
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
@RequestMapping("/maker/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "创客端---个独管理模块相关接口", tags = "创客端---个独管理模块相关接口")
public class IndividualEnterpriseMakerController {

    private IUserClient userClient;
    private IPayMakerService payMakerService;

    @GetMapping("/query-month-and-year-invoice-money")
    @ApiOperation(value = "查询个独月度开票金额和年度开票金额", notes = "查询个独月度开票金额和年度开票金额")
    public R queryMonthAndYearInvoiceMoney(@ApiParam(value = "个独", required = true) @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payMakerService.yearMonthMoney(individualEnterpriseId, MakerType.INDIVIDUALENTERPRISE);
    }

}
