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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/maker/accept-paysheet")
@Validated
@AllArgsConstructor
@Api(value = "创客端---交付支付验收单管理模块相关接口", tags = "创客端---交付支付验收单管理模块相关接口")
public class AcceptPaysheetMakerController {

    private IUserClient userClient;
    private IAcceptPaysheetService acceptPaysheetService;

    @GetMapping("/query-maker-to-enterprise-accept-paysheet-list")
    @ApiOperation(value = "查询创客对应某商户的所有总包交付支付验收单", notes = "查询创客对应某商户的所有总包交付支付验收单")
    public R queryMakerToEnterpriseAcceptPaysheetList(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return acceptPaysheetService.getAcceptPaysheetsByEnterprise(Condition.getPage(query.setDescs("create_time")), enterpriseId, makerEntity.getId());
    }

    @GetMapping("/query-accept-paysheet-detail")
    @ApiOperation(value = "根据ID查询总包交付支付验收单", notes = "根据ID查询总包交付支付验收单")
    public R queryAcceptPaysheetDetail(@ApiParam(value = "总包交付支付验收单ID", required = true) @NotNull(message = "请输入总包交付支付验收单编号") @RequestParam(required = false) Long acceptPaysheetId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return acceptPaysheetService.getAcceptPaysheetWorksheet(makerEntity.getId(), acceptPaysheetId);
    }

}
