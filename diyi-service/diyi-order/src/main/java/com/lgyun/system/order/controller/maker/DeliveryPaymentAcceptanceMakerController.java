package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 创客端---交付支付验收单管理模块相关接口
 *
 * @author jun.
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
//@RequestMapping("/maker/delivery-payment-acceptance")
@Validated
@AllArgsConstructor
@Api(value = "创客端---交付支付验收单管理模块相关接口", tags = "创客端---交付支付验收单管理模块相关接口")
public class DeliveryPaymentAcceptanceMakerController {

    private IAcceptPaysheetService acceptPaysheetService;
    private IUserClient iUserClient;

    @GetMapping("/acceptpaysheet/get-accept-paysheets-by-enterprise")
    @ApiOperation(value = "查询创客对应某商户的所有总包交付支付验收单", notes = "查询创客对应某商户的所有总包交付支付验收单")
    public R getAcceptPaysheetsByEnterprise(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {

        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return acceptPaysheetService.getAcceptPaysheetsByEnterprise(Condition.getPage(query.setDescs("create_time")), enterpriseId, makerEntity.getId());
    }

    @GetMapping("/acceptpaysheet/get-accept-paysheet-worksheet")
    @ApiOperation(value = "根据ID查询总包交付支付验收单", notes = "根据ID查询总包交付支付验收单")
    public R getAcceptPaysheetWorksheet(@ApiParam(value = "总包交付支付验收单ID") @NotNull(message = "请输入总包交付支付验收单编号") @RequestParam(required = false) Long acceptPaysheetId, BladeUser bladeUser) {

        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return acceptPaysheetService.getAcceptPaysheetWorksheet(makerEntity.getId(), acceptPaysheetId);
    }

}
