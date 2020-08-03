package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import com.lgyun.system.order.service.IPayEnterpriseReceiptService;
import com.lgyun.system.order.wrapper.PayEnterpriseReceiptWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/enterprisepayreceipt")
@Validated
@AllArgsConstructor
@Api(value = "商户支付回单相关接口", tags = "商户支付回单相关接口")
public class PayEnterpriseReceiptController {

    private IPayEnterpriseReceiptService enterprisePayReceiptService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody PayEnterpriseReceiptEntity enterprisePayReceipt) {
        return R.status(enterprisePayReceiptService.save(enterprisePayReceipt));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody PayEnterpriseReceiptEntity enterprisePayReceipt) {
        return R.status(enterprisePayReceiptService.updateById(enterprisePayReceipt));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R detail(PayEnterpriseReceiptEntity enterprisePayReceipt) {
        PayEnterpriseReceiptEntity detail = enterprisePayReceiptService.getOne(Condition.getQueryWrapper(enterprisePayReceipt));
        return R.data(PayEnterpriseReceiptWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(PayEnterpriseReceiptEntity enterprisePayReceipt, Query query) {
        IPage<PayEnterpriseReceiptEntity> pages = enterprisePayReceiptService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(enterprisePayReceipt));
        return R.data(PayEnterpriseReceiptWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(enterprisePayReceiptService.removeByIds(Func.toLongList(ids)));
    }

}
