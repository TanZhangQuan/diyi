package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.PayListDto;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.wrapper.PayMakerWrapper;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Slf4j
@RestController
@RequestMapping("/paymaker")
@Validated
@AllArgsConstructor
@Api(value = "创客支付明细相关接口", tags = "创客支付明细相关接口")
public class PayMakerController {

    private IPayMakerService payMakerService;
    private IUserClient iUserClient;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody PayMakerEntity payMaker) {
        return R.status(payMakerService.save(payMaker));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody PayMakerEntity payMaker) {
        return R.status(payMakerService.updateById(payMaker));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R detail(PayMakerEntity payMaker) {
        PayMakerEntity detail = payMakerService.getOne(Condition.getQueryWrapper(payMaker));
        return R.data(PayMakerWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(PayMakerEntity payMaker, Query query) {
        IPage<PayMakerEntity> pages = payMakerService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(payMaker));
        return R.data(PayMakerWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(payMakerService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/get_by_dto_enterprise")
    @ApiOperation(value = "查询当前商户所有分包支付清单", notes = "查询当前商户所有分包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceProviderName", value = "服务商名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getByDtoEnterprise(PayListDto payListDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户所有分包支付清单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return payMakerService.getByDtoEnterprise(enterpriseWorkerEntity.getEnterpriseId(), payListDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户所有分包支付清单异常", e);
        }
        return R.fail("查询失败");
    }

}
