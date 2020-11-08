package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IAcceptPaysheetCsService;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/maker/accept-paysheet")
@Validated
@AllArgsConstructor
@Api(value = "创客端---交付支付验收单管理模块相关接口", tags = "创客端---交付支付验收单管理模块相关接口")
public class AcceptPaysheetMakerController {

    private IUserClient userClient;
    private IAcceptPaysheetService acceptPaysheetService;
    private IAcceptPaysheetCsService acceptPaysheetCsService;
    private IPayMakerService payMakerService;

    @GetMapping("/query-total-sub-accept-paysheet-list")
    @ApiOperation(value = "查询总包+分包交付支付验收单", notes = "查询总包+分包交付支付验收单")
    public R queryTotalSubAcceptPaysheetList(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return acceptPaysheetService.queryTotalSubAcceptPaysheetListMaker(enterpriseId, makerEntity.getId(), Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-total-sub-accept-paysheet-detail")
    @ApiOperation(value = "查询总包+分包交付支付验收单详情", notes = "查询总包+分包交付支付验收单详情")
    public R queryTotalSubAcceptPaysheetDetail(@ApiParam(value = "总包+分包交付支付验收单", required = true) @NotNull(message = "请选择总包+分包交付支付验收单") @RequestParam(required = false) Long acceptPaysheetId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return acceptPaysheetService.queryTotalSubAcceptPaysheetDetailMaker(makerEntity.getId(), acceptPaysheetId);
    }

    @PostMapping("/confirm-pay-maker")
    @ApiOperation(value = "确认收款", notes = "确认收款")
    public R confirmPayMaker(@ApiParam(value = "创客支付明细", required = true) @NotNull(message = "请选择创客支付明细") @RequestParam(required = false) Long payMakerId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payMakerService.confirmPayMaker(makerEntity.getId(), payMakerId);
    }

    @GetMapping("/query-crowd-accept-paysheet-list")
    @ApiOperation(value = "查询众包交付支付验收单", notes = "查询众包交付支付验收单")
    public R queryCrowdAcceptPaysheetList(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return acceptPaysheetCsService.queryCrowdAcceptPaysheetListMaker(enterpriseId, makerEntity.getId(), Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-crowd-accept-paysheet-detail")
    @ApiOperation(value = "查询众包交付支付验收单详情", notes = "查询众包交付支付验收单详情")
    public R queryCrowdAcceptPaysheetDetail(@ApiParam(value = "众包交付支付验收单", required = true) @NotNull(message = "请选择众包交付支付验收单") @RequestParam(required = false) Long acceptPaysheetId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return acceptPaysheetCsService.queryCrowdAcceptPaysheetDetailMaker(makerEntity.getId(), acceptPaysheetId);
    }

}
