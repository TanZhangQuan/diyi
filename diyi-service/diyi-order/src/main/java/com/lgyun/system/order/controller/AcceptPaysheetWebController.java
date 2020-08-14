package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPayListDto;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.dto.PayEnterpriseMakerListDto;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
@Slf4j
@RestController
@RequestMapping("/web/accept_pay_sheet")
@Validated
@AllArgsConstructor
@Api(value = "总包交付支付验收单相关接口(管理端)", tags = "总包交付支付验收单相关接口(管理端)")
public class AcceptPaysheetWebController {

    private IAcceptPaysheetService acceptPaysheetService;
    private IUserClient iUserClient;
    private IPayEnterpriseService payEnterpriseService;

    @PostMapping("/upload")
    @ApiOperation(value = "上传总包交付支付验收单", notes = "上传总包交付支付验收单")
    public R save(@Valid @RequestBody AcceptPaysheetSaveDto acceptPaysheet, BladeUser bladeUser) {

        log.info("上传总包交付支付验收单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return acceptPaysheetService.upload(acceptPaysheet, enterpriseWorkerEntity);
        } catch (Exception e) {
            log.error("上传总包交付支付验收单异常", e);
        }

        return R.fail("上传失败");
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除总包交付支付验收单", notes = "删除总包交付支付验收单")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {

        log.info("删除总包交付支付验收单");
        try {
            return R.status(acceptPaysheetService.removeByIds(Func.toLongList(ids)));
        } catch (Exception e) {
            log.error("删除总包交付支付验收单异常", e);
        }

        return R.fail("删除失败");
    }

    @GetMapping("/get_accept_pay_sheet_by_enterprise")
    @ApiOperation(value = "查询当前商户所有总包交付支付验收单", notes = "查询当前商户所有总包交付支付验收单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acceptPayId", value = "交付支付验收单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "makerName", value = "创客名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getAcceptPaySheetsByEnterprise(AcceptPayListDto acceptPayListDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户所有总包交付支付验收单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return acceptPaysheetService.getAcceptPaySheetsByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), acceptPayListDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户所有总包交付支付验收单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_maker_list")
    @ApiOperation(value = "根据总包交付支付验收单ID查询关联创客", notes = "根据总包交付支付验收单ID查询关联创客")
    public R getMakerList(@ApiParam(value = "总包交付支付验收单ID") @NotNull(message = "请输入总包交付支付验收单编号") @RequestParam(required = false) Long acceptPaysheetId, Query query) {

        log.info("根据总包交付支付验收单ID查询关联创客");
        try {
            return acceptPaysheetService.getMakerList(acceptPaysheetId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据总包交付支付验收单ID查询关联创客异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_pay_enterprises_by_enterprise")
    @ApiOperation(value = "查询当前商户所有总包支付清单", notes = "查询当前商户所有总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceProviderName", value = "服务商名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByEnterprise(PayEnterpriseMakerListDto payEnterpriseMakerListDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户所有总包支付清单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return payEnterpriseService.getPayEnterprisesByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), payEnterpriseMakerListDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户所有总包支付清单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_makers")
    @ApiOperation(value = "根据支付清单ID查询支付清单关联工单的创客", notes = "根据支付清单ID查询支付清单关联工单的创客")
    public R getMakers(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query) {

        log.info("根据支付清单ID查询支付清单关联工单的创客");
        try {
            return payEnterpriseService.getMakers(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据支付清单ID查询支付清单关联工单的创客异常", e);
        }
        return R.fail("查询失败");
    }

}
