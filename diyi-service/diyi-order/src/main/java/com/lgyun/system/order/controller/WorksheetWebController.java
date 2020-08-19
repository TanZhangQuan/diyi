package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.ReleaseWorksheetDto;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
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
import java.math.BigDecimal;

/**
 * 工单
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Slf4j
@RestController
@RequestMapping("/order/worksheet")
@Validated
@AllArgsConstructor
@Api(value = "工单相关接口(管理端)", tags = "工单相关接口(管理端)")
public class WorksheetWebController {

    private IWorksheetService worksheetService;
    private IWorksheetMakerService worksheetMakerService;
    private IUserClient iUserClient;


    @PostMapping("/releaseWorksheet")
    @ApiOperation(value = "发布工单", notes = "发布工单")
    public R releaseWorksheet(@Valid @RequestBody ReleaseWorksheetDto releaseWorksheetDTO,BladeUser bladeUser) {
        log.info("发布工单");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            releaseWorksheetDTO.setEnterpriseId(enterpriseWorkerEntity.getEnterpriseId());
            return worksheetService.releaseWorksheet(releaseWorksheetDTO);
        } catch (Exception e) {
            log.error("发布订单失败", e);
        }
        return R.fail("发布订单失败");
    }

    @GetMapping("getMakerName")
    @ApiOperation(value = "通过创客名字查询", notes = "通过创客名字查询")
    public R getMakerName(Query query, @RequestParam(required = false) String makerName) {

        log.info("通过创客名字查询");
        try {
            return iUserClient.getMakerName(query.getCurrent(), query.getSize(), makerName);
        } catch (Exception e) {
            log.error("通过创客名字查询失败", e);
        }

        return R.fail("通过创客名字查询失败");
    }

    @GetMapping("getEnterpriseWorksheet")
    @ApiOperation(value = "根据工单状态和商户id查询", notes = "根据工单状态和商户id查询")
    public R getEnterpriseWorksheet(Query query, BladeUser bladeUser,
                                    @NotNull(message = "请输入工单的状态") @RequestParam(required = false) WorksheetState worksheetState,
                                    @RequestParam(required = false) String worksheetNo,
                                    @RequestParam(required = false) String worksheetName,
                                    @RequestParam(required = false) String startTime,
                                    @RequestParam(required = false) String endTime) {
        log.info("根据工单状态和商户id查询");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return worksheetService.getEnterpriseWorksheet(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), worksheetState, worksheetNo, worksheetName, startTime, endTime);
        } catch (Exception e) {
            log.error("根据工单状态和商户id查询失败", e);
        }
        return R.fail("根据工单状态和商户id查询失败");
    }

    @PostMapping("deleteWorksheet")
    @ApiOperation(value = "删除", notes = "删除")
    public R deleteWorksheet(@NotNull(message = "请输入工单的id") @RequestParam(required = false) Long worksheetId) {
        log.info("删除");
        try {
            worksheetService.removeById(worksheetId);
            return R.success("删除成功");
        } catch (Exception e) {
            log.error("删除失败", e);
        }
        return R.fail("删除失败");
    }

    @GetMapping("getWorksheetWebDetails")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R getWorksheetWebDetails(Query query, @NotNull(message = "请输入工单的id") @RequestParam(required = false) Long worksheetId) {
        log.info("查询工单详情");
        try {
            return worksheetService.getWorksheetWebDetails(Condition.getPage(query.setDescs("create_time")), worksheetId);
        } catch (Exception e) {
            log.error("查询工单详情失败", e);
        }
        return R.fail("查询工单详情失败");
    }

    @PostMapping("/kickOut")
    @ApiOperation(value = "工单踢出创客", notes = "工单踢出创客")
    public R kickOut(@NotNull(message = "请输入工单的id") @RequestParam(required = false) Long worksheetId,
                     @NotNull(message = "请输入创客id") @RequestParam(required = false) Long makerId) {
        log.info("工单踢出创客");
        try {
            return worksheetService.kickOut(worksheetId, makerId);
        } catch (Exception e) {
            log.error("工单踢出创客失败", e);
        }
        return R.fail("工单踢出创客失败");
    }

//    @PostMapping("/checkAccept")
//    @ApiOperation(value = "验收工单", notes = "验收工单")
//    public R checkAccept(@NotNull(message = "请输入工单创客的id") @RequestParam(required = false) Long worksheetMakerId,
//                         @NotNull(message = "请输入创客id") @RequestParam(required = false) BigDecimal checkMoney) {
//        log.info("验收工单");
//        try {
//            return worksheetService.checkAccept(worksheetMakerId, checkMoney);
//        } catch (Exception e) {
//            log.error("验收工单失败", e);
//        }
//        return R.fail("验收工单失败");
//    }

    @PostMapping("/closeOrOpen")
    @ApiOperation(value = "开单或关单", notes = "开单或关单")
    public R closeOrOpen(@NotNull(message = "请输入工单的id") @RequestParam(required = false) Long worksheetId,
                         @ApiParam(value = "1代表关闭，2开启") @NotNull(message = "请输入1代表关闭，2开启") @RequestParam(required = false) Integer variable) {
        log.info("开单或关单");
        try {
            return worksheetService.closeOrOpen(worksheetId, variable);
        } catch (Exception e) {
            log.error("开单或关单失败", e);
        }
        return R.fail("开单或关单失败");
    }

    @PostMapping("/checkAchievement")
    @ApiOperation(value = "验收工作成果", notes = "验收工作成果")
    public R checkAchievement(@NotNull(message = "请输入id") @RequestParam(required = false) Long worksheetMakerId,
                              @NotNull(message = "请输入验证金额") @RequestParam(required = false) BigDecimal checkMoney,
                              @NotNull(message = "请输入验收的结果") @RequestParam(required = false) Boolean bool,
                              BladeUser bladeUser) {
        log.info("验收工作成果");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return worksheetMakerService.checkAchievement(worksheetMakerId, checkMoney, enterpriseWorkerEntity.getEnterpriseId(), bool);
        } catch (Exception e) {
            log.info("验收工作成果失败");
        }
        return R.fail("验收工作成果失败");
    }

    /**
     * 批量开启或关闭工单
     */
    @PostMapping("/closeOrOpenList")
    @ApiOperation(value = "批量开启或关闭工单", notes = "批量开启或关闭工单")
    public R closeOrOpenAll(@NotNull(message = "请输入工单的id") @RequestParam(required = false) String worksheetIds,
                              @ApiParam(value = "1代表关闭，2开启") @NotNull(message = "请输入1代表关闭，2开启") @RequestParam(required = false) Integer variable) {
        log.info("批量开启工单");
        try {
            return worksheetService.closeOrOpenList(worksheetIds,variable);
        } catch (Exception e) {
            log.info("批量开启或关闭工单失败");
        }
        return R.fail("批量开启或关闭工单失败");
    }

    /**
     * 批量删除工单
     */
    @PostMapping("/deleteWorksheetList")
    @ApiOperation(value = "批量删除工单", notes = "批量删除工单")
    public R deleteWorksheetList(@NotNull(message = "请输入工单的id") @RequestParam(required = false) String worksheetIds) {
        log.info("批量开启工单");
        try {
            return worksheetService.deleteWorksheetList(worksheetIds);
        } catch (Exception e) {
            log.info("批量删除工单失败");
        }
        return R.fail("批量删除工单失败");
    }

    /**
     * 整体验收工单
     */
    @PostMapping("/wholeWorksheetCheck")
    @ApiOperation(value = "整体验收工单", notes = "整体验收工单")
    public R wholeWorksheetCheck(@NotNull(message = "请输入工单的id") @RequestParam(required = false) Long worksheetId) {
        log.info("整体验收工单");
        try {
            return worksheetService.wholeWorksheetCheck(worksheetId);
        } catch (Exception e) {
            log.info("整体验收工单失败");
        }
        return R.fail("整体验收工单失败");
    }
}
