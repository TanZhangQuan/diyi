package com.lgyun.system.order.controller;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
@Slf4j
@RestController
@RequestMapping("/acceptpaysheet")
@Validated
@AllArgsConstructor
@Api(value = "总包交付支付验收单相关接口", tags = "总包交付支付验收单相关接口")
public class AcceptPaysheetController {

    private IAcceptPaysheetService acceptPaysheetService;
    private IUserClient iUserClient;

    @GetMapping("/get-enterprises-by-worksheet")
    @ApiOperation(value = "查询创客所有总包交付支付验收单的商户", notes = "查询创客所有总包交付支付验收单的商户")
    public R getEnterprisesByWorksheet(Query query, BladeUser bladeUser) {

        log.info("查询创客所有总包交付支付验收单的商户");
        try {
            //获取当前创客
            R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return acceptPaysheetService.getEnterprisesByWorksheet(Condition.getPage(query.setDescs("create_time")), makerEntity.getId());
        } catch (Exception e) {
            log.error("查询创客所有总包交付支付验收单的商户异常", e);
        }

        return R.fail("查询失败");
    }

    @GetMapping("/get-accept-paysheets-by-enterprise")
    @ApiOperation(value = "查询创客对应某商户的所有总包交付支付验收单", notes = "查询创客对应某商户的所有总包交付支付验收单")
    public R getAcceptPaysheetsByEnterprise(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {

        log.info("查询创客对应某商户的所有总包交付支付验收单");
        try {
            //获取当前创客
            R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return acceptPaysheetService.getAcceptPaysheetsByEnterprise(Condition.getPage(query.setDescs("create_time")), enterpriseId, makerEntity.getId());
        } catch (Exception e) {
            log.error("查询创客对应某商户的所有总包交付支付验收单异常", e);
        }

        return R.fail("查询失败");
    }

    @GetMapping("/get-accept-paysheet-worksheet")
    @ApiOperation(value = "根据ID查询总包交付支付验收单", notes = "根据ID查询总包交付支付验收单")
    public R getAcceptPaysheetWorksheet(@ApiParam(value = "总包交付支付验收单ID") @NotNull(message = "请输入总包交付支付验收单编号") @RequestParam(required = false) Long acceptPaysheetId, BladeUser bladeUser) {

        log.info("根据ID查询总包交付支付验收单");
        try {
            //获取当前创客
            R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return acceptPaysheetService.getAcceptPaysheetWorksheet(makerEntity.getId(), acceptPaysheetId);
        } catch (Exception e) {
            log.error("根据ID查询总包交付支付验收单异常", e);
        }

        return R.fail("查询失败");
    }

}
