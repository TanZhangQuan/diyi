package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.PayEnterpriseTotalListDto;
import com.lgyun.system.order.dto.PayEnterpriseUploadDto;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.wrapper.PayEnterpriseWrapper;
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
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Slf4j
@RestController
@RequestMapping("/pay_enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户支付清单相关接口", tags = "商户支付清单相关接口")
public class PayEnterpriseController {

    private IPayEnterpriseService enterprisePayService;
    private IUserClient iUserClient;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody PayEnterpriseEntity enterprisePay) {
        return R.status(enterprisePayService.save(enterprisePay));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody PayEnterpriseEntity enterprisePay) {
        return R.status(enterprisePayService.updateById(enterprisePay));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R detail(PayEnterpriseEntity enterprisePay) {
        PayEnterpriseEntity detail = enterprisePayService.getOne(Condition.getQueryWrapper(enterprisePay));
        return R.data(PayEnterpriseWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(PayEnterpriseEntity enterprisePay, Query query) {
        IPage<PayEnterpriseEntity> pages = enterprisePayService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(enterprisePay));
        return R.data(PayEnterpriseWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(enterprisePayService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/statistical")
    @ApiOperation(value = "获取交付清单统计数据", notes = "获取交付清单统计数据")
    public R statistical(BladeUser bladeUser) {

        log.info("获取交付清单统计数据");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return enterprisePayService.statistical(enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("获取交付清单统计数据异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传支付清单", notes = "上传支付清单")
    public R upload(@Valid @RequestBody PayEnterpriseUploadDto payEnterpriseUploadDto, BladeUser bladeUser) {

        log.info("上传支付清单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return enterprisePayService.upload(payEnterpriseUploadDto, enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("上传支付清单异常", e);
        }
        return R.fail("上传失败");
    }

    @GetMapping("/get_by_dto_enterprise")
    @ApiOperation(value = "查询当前商户所有总包支付清单", notes = "查询当前商户所有总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceProviderName", value = "服务商名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getByDtoEnterprise(PayEnterpriseTotalListDto payEnterpriseTotalListDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户所有总包支付清单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return enterprisePayService.getByDtoEnterprise(enterpriseWorkerEntity.getEnterpriseId(), payEnterpriseTotalListDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户所有总包支付清单异常", e);
        }
        return R.fail("查询失败");
    }

}
