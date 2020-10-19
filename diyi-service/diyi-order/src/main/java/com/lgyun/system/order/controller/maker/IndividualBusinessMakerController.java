package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
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

/**
 * 创客端---个体户管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/maker/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "创客端---个体户管理模块相关接口", tags = "创客端---个体户管理模块相关接口")
public class IndividualBusinessMakerController {

    private IUserClient userClient;
    private ISelfHelpInvoiceService iSelfHelpInvoiceService;

    @GetMapping("/query-individual-business-month-and-year-invoice-money")
    @ApiOperation(value = "查询个体户月度开票金额和年度开票金额", notes = "查询个体户月度开票金额和年度开票金额")
    public R queryIndividualBusinessMonthAndYearInvoiceMoney(@ApiParam(value = "个体户", required = true) @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return iSelfHelpInvoiceService.yearMonthMoney(individualBusinessId, InvoicePeopleType.INDIVIDUALBUSINESS);
    }

}
