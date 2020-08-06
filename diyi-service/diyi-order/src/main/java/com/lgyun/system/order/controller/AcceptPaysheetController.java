package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPayListDto;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.wrapper.AcceptPaysheetWrapper;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.MakerEntity;
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
@RequestMapping("/acceptpaysheet")
@Validated
@AllArgsConstructor
@Api(value = "总包交付支付验收单相关接口", tags = "总包交付支付验收单相关接口")
public class AcceptPaysheetController {

    private IAcceptPaysheetService acceptPaysheetService;
    private IUserClient iUserClient;

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

    @GetMapping("/get_by_dto_enterprise")
    @ApiOperation(value = "查询当前商户所有总包总包交付支付验收单", notes = "查询当前商户所有总包总包交付支付验收单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "makerName", value = "创客名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getByDtoEnterprise(AcceptPayListDto acceptPayListDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户所有总包总包交付支付验收单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return acceptPaysheetService.getByDtoEnterprise(enterpriseWorkerEntity.getEnterpriseId(), acceptPayListDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户所有总包总包交付支付验收单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_maker_list")
    @ApiOperation(value = "根据总包总包交付支付验收单ID查询关联创客", notes = "根据总包总包交付支付验收单ID查询关联创客")
    public R getMakerList(@ApiParam(value = "总包交付支付验收单ID") @NotNull(message = "请输入总包交付支付验收单编号") @RequestParam(required = false) Long acceptPaysheetId, Query query) {

        log.info("根据总包总包交付支付验收单ID查询关联创客");
        try {
            return acceptPaysheetService.getMakerList(acceptPaysheetId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("根据总包总包交付支付验收单ID查询关联创客异常", e);
        }
        return R.fail("查询失败");
    }

}
