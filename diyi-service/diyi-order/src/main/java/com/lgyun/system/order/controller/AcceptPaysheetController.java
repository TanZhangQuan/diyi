package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.wrapper.AcceptPaysheetWrapper;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/user/acceptpaysheet")
@Validated
@AllArgsConstructor
@Api(value = "交付支付验收单相关接口", tags = "交付支付验收单相关接口")
public class AcceptPaysheetController {

    private IAcceptPaysheetService acceptPaysheetService;
    private IUserClient iUserClient;

    //	@PostMapping("/save")
//	@ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody AcceptPaysheetEntity acceptPaysheet) {
        return R.status(acceptPaysheetService.save(acceptPaysheet));
    }

    //	@PostMapping("/update")
//	@ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody AcceptPaysheetEntity acceptPaysheet) {
        return R.status(acceptPaysheetService.updateById(acceptPaysheet));
    }

    //	@GetMapping("/detail")
//	@ApiOperation(value = "详情", notes = "详情")
    public R detail(AcceptPaysheetEntity acceptPaysheet) {
        AcceptPaysheetEntity detail = acceptPaysheetService.getOne(Condition.getQueryWrapper(acceptPaysheet));
        return R.data(AcceptPaysheetWrapper.build().entityVO(detail));
    }

    //	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "分页")
    public R list(AcceptPaysheetEntity acceptPaysheet, Query query) {
        IPage<AcceptPaysheetEntity> pages = acceptPaysheetService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(acceptPaysheet));
        return R.data(AcceptPaysheetWrapper.build().pageVO(pages));
    }

    //	@PostMapping("/remove")
//	@ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(acceptPaysheetService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/get-enterprises-by-worksheet")
    @ApiOperation(value = "查询创客所有交付支付验收单的商户", notes = "查询创客所有交付支付验收单的商户")
    public R getEnterprisesByWorksheet(Query query, BladeUser bladeUser) {
        MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
        return acceptPaysheetService.getEnterprisesByWorksheet(Condition.getPage(query.setDescs("create_time")), makerEntity.getId());
    }

    @GetMapping("/get-accept-paysheets-by-enterprise")
    @ApiOperation(value = "查询创客对应某商户的所有交付支付验收单", notes = "查询创客对应某商户的所有交付支付验收单")
    public R getAcceptPaysheetsByEnterprise(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
        return acceptPaysheetService.getAcceptPaysheetsByEnterprise(Condition.getPage(query.setDescs("create_time")), enterpriseId, makerEntity.getId());
    }

    @GetMapping("/get-accept-paysheet-worksheet")
    @ApiOperation(value = "根据ID查询交付支付验收单", notes = "根据ID查询交付支付验收单")
    public R getAcceptPaysheetWorksheet(@ApiParam(value = "交付支付验收单ID") @NotNull(message = "请输入交付支付验收单编号") @RequestParam(required = false) Long acceptPaysheetId, BladeUser bladeUser) {
        MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
        return acceptPaysheetService.getAcceptPaysheetWorksheet(makerEntity.getId(), acceptPaysheetId);
    }

}
