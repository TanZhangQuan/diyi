package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 工单
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Slf4j
@RestController
@RequestMapping("/worksheet")
@Validated
@AllArgsConstructor
@Api(value = "工单相关接口", tags = "工单相关接口")
public class WorksheetController {

    private IWorksheetService worksheetService;
    private IWorksheetMakerService worksheetMakerService;
    private IUserClient iUserClient;

    @PostMapping("/orderGrabbing")
    @ApiOperation(value = "抢单", notes = "抢单")
    public R orderGrabbing(@NotNull(message = "请输入工单的id") @RequestParam(required = false)Long worksheetId, BladeUser bladeUser) {
        log.info("抢单");
        try {
            //获取当前创客
            R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return worksheetService.orderGrabbing(worksheetId, makerEntity.getId());
        } catch (Exception e) {
            log.error("抢单失败", e);
        }
        return R.fail("抢单失败");
    }

    @GetMapping("/findXiaoPage")
    @ApiOperation(value = "小程查询工单", notes = "小程查询工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "worksheetState", value = "工单状态：1代表待抢单，2已接单，3已交付", paramType = "query", dataType = "string"),
    })
    public R findXiaoPage(Query query,@NotNull(message = "请输入工单的状态") @RequestParam(required = false) Integer worksheetState, BladeUser bladeUser) {
        try {
            //获取当前创客
            R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            if (null == worksheetState || (worksheetState != 1 && worksheetState != 2 && worksheetState != 3)) {
                return R.fail("参数错误");
            }
            return worksheetService.findXiaoPage(Condition.getPage(query.setDescs("create_time")), worksheetState, makerEntity.getId());
        }catch (Exception e){
            log.info("小程查询工单失败");
        }
        return R.fail("小程查询工单失败");
    }

    @PostMapping("/submitachievement")
    @ApiOperation(value = "提交工作成果", notes = "提交工作成果")
    public R submitachievement(@NotNull(message = "请输入工单的状态") @RequestParam(required = false) Long worksheetMakerId,
                               @NotNull(message = "请输入工单说明") @RequestParam(required = false) String achievementDesc,
                               @NotNull(message = "请输入工单url") @RequestParam(required = false)String achievementFiles) {

        try {
            WorksheetMakerEntity worksheetMakerEntity = worksheetMakerService.getById(worksheetMakerId);
            WorksheetEntity worksheetEntity = worksheetService.getById(worksheetMakerEntity.getWorksheetId());
            if (!(worksheetEntity.getWorksheetState().equals(WorksheetState.CLOSED) || worksheetEntity.getWorksheetState().equals(WorksheetState.CHECKACCEPT))) {
                return R.fail("工单暂时不能提交工作成果，稍后再试");
            }
            return worksheetMakerService.submitAchievement(worksheetMakerEntity, achievementDesc, achievementFiles);
        } catch (Exception e) {
            log.info("提交工作成果失败");
        }
        return R.fail("提交工作成果失败");
    }

    @GetMapping("/getWorksheetDetails")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R getWorksheetDetails(@NotNull(message = "请输入id") @RequestParam(required = false) Long worksheetMakerId) {
        log.info("查询工单详情");
        try {
            return worksheetService.getWorksheetDetails(worksheetMakerId);
        } catch (Exception e) {
            log.info("查询工单详情失败");
        }
        return R.fail("查询工单详情失败");
    }

    @GetMapping("/get_enterprise_worksheet_details")
    @ApiOperation(value = "根据创客ID查询工单", notes = "根据创客ID查询工单")
    public R getEnterpriseWorksheetDetails(@ApiParam(value = "创客ID") @NotNull(message = "请输入创客编号") @RequestParam(required = false) Long makerId, Query query, BladeUser bladeUser) {

        log.info("根据创客ID查询工单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return worksheetService.getWorksheetDetailsByMaker(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), makerId);
        } catch (Exception e) {
            log.error("根据创客ID查询工单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_by_worksheet_no")
    @ApiOperation(value = "根据工单编号获取工单", notes = "根据工单编号获取工单")
    public R getByWorksheetNo(String worksheetNo) {

        log.info("根据工单编号获取工单");
        try {
            return worksheetService.getByWorksheetNo(worksheetNo);
        } catch (Exception e) {
            log.error("根据工单编号获取工单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_by_worksheet_id")
    @ApiOperation(value = "根据工单ID获取工单", notes = "根据工单ID获取工单")
    public R getByWorksheetId(String worksheetId) {

        log.info("根据工单ID获取工单");
        try {
            return worksheetService.getByWorksheetId(worksheetId);
        } catch (Exception e) {
            log.error("根据工单ID获取工单异常", e);
        }
        return R.fail("查询失败");
    }

}
