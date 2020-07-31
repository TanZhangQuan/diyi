package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.ReleaseWorksheetDto;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
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
@Api(value = "工单相关接口", tags = "工单相关接口")
public class WorksheetController {

    private IWorksheetService worksheetService;
    private IWorksheetMakerService worksheetMakerService;
    private IUserClient iUserClient;

    @PostMapping("/releaseWorksheet")
    @ApiOperation(value = "发布工单", notes = "发布工单")
    public R releaseWorksheet(@Valid @RequestBody ReleaseWorksheetDto releaseWorksheetDTO) {
        log.info("发布工单");
        try {
            return worksheetService.releaseWorksheet(releaseWorksheetDTO);
        } catch (Exception e) {
            log.error("发布订单失败", e);
        }
        return R.fail("发布订单失败");
    }

    @GetMapping("getMakerName")
    @ApiOperation(value = "通过创客名字查询", notes = "通过创客名字查询")
    public R getMakerName(Query query,@RequestParam(required = false) String makerName){
        log.info("通过创客名字查询");
        try {
            return iUserClient.getMakerName(query.getCurrent(), query.getSize(),makerName);
        } catch (Exception e) {
            log.error("通过创客名字查询失败", e);
        }
        return R.fail("通过创客名字查询失败");
    }

    @GetMapping("getEnterpriseWorksheet")
    @ApiOperation(value = "根据工单状态和商户id查询", notes = "根据工单状态和商户id查询")
    public R getEnterpriseWorksheet(Query query, Long enterpriseId,
                                    @NotNull(message = "请输入工单的状态") @RequestParam(required = false) WorksheetState worksheetState,
                                    @RequestParam(required = false) String worksheetNo,
                                    @RequestParam(required = false) String worksheetName,
                                    @RequestParam(required = false) String startTime,
                                    @RequestParam(required = false) String endTime){
        log.info("根据工单状态和商户id查询");
        try {
            return worksheetService.getEnterpriseWorksheet(Condition.getPage(query),enterpriseId,worksheetState,worksheetNo,worksheetName,startTime,endTime);
        } catch (Exception e) {
            log.error("根据工单状态和商户id查询失败", e);
        }
        return R.fail("根据工单状态和商户id查询失败");
    }

    @PostMapping("deleteWorksheet")
    @ApiOperation(value = "删除", notes = "删除")
    public R deleteWorksheet(@NotNull(message = "请输入工单的id") @RequestParam(required = false) Long worksheetId){
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
    @ApiOperation(value = "查看", notes = "查看")
    public R getWorksheetWebDetails(Query query,@NotNull(message = "请输入工单的id") @RequestParam(required = false) Long worksheetId){
        log.info("查看");
        try {
            return worksheetService.getWorksheetWebDetails(Condition.getPage(query),worksheetId);
        } catch (Exception e) {
            log.error("查看失败", e);
        }
        return R.fail("查看失败");
    }

    @PostMapping("/kickOut")
    @ApiOperation(value = "工单踢出创客", notes = "工单踢出创客")
    public R kickOut(@NotNull(message = "请输入工单的id") @RequestParam(required = false) Long worksheetId,
                     @NotNull(message = "请输入创客id") @RequestParam(required = false)   Long makerId) {
        log.info("工单踢出创客");
        try {
            return worksheetService.kickOut(worksheetId, makerId);
        } catch (Exception e) {
            log.error("工单踢出创客失败", e);
        }
        return R.fail("工单踢出创客失败");
    }

    @PostMapping("/checkAccept")
    @ApiOperation(value = "验收工单", notes = "验收工单")
    public R checkAccept(@NotNull(message = "请输入工单创客的id") @RequestParam(required = false) Long worksheetMakerId,
                     @NotNull(message = "请输入创客id") @RequestParam(required = false) BigDecimal checkMoney) {
        log.info("验收工单");
        try {
            return worksheetService.checkAccept(worksheetMakerId, checkMoney);
        } catch (Exception e) {
            log.error("验收工单失败", e);
        }
        return R.fail("验收工单失败");
    }

    @PostMapping("/closeOrOpen")
    @ApiOperation(value = "开单或关单", notes = "开单或关单")
    public R closeOrOpen(@NotNull(message = "请输入工单的id") @RequestParam(required = false)Long worksheetId,
                         @ApiParam(value = "1代表关闭，2开启") @NotNull(message = "请输入1代表关闭，2开启") @RequestParam(required = false) Integer variable) {
        log.info("开单或关单");
        try {
            return worksheetService.closeOrOpen(worksheetId, variable);
        } catch (Exception e) {
            log.error("开单或关单失败", e);
        }
        return R.fail("开单或关单失败");
    }

    @PostMapping("/orderGrabbing")
    @ApiOperation(value = "抢单", notes = "抢单")
    public R orderGrabbing(@NotNull(message = "请输入工单的id") @RequestParam(required = false)Long worksheetId, BladeUser bladeUser) {
        log.info("抢单");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
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
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            if (null == worksheetState || (worksheetState != 1 && worksheetState != 2 && worksheetState != 3)) {
                return R.fail("参数错误");
            }
            return worksheetService.findXiaoPage(Condition.getPage(query), worksheetState, makerEntity.getId());
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

    @PostMapping("/checkAchievement")
    @ApiOperation(value = "验收工作成果", notes = "验收工作成果")
    public R checkAchievement(@NotNull(message = "请输入id") @RequestParam(required = false) Long worksheetMakerId,
                              @NotNull(message = "请输入验证金额") @RequestParam(required = false) BigDecimal checkMoney,
                              @NotNull(message = "请输入商户id") @RequestParam(required = false) Long enterpriseId,
                              @NotNull(message = "请输入验收的结果") @RequestParam(required = false) Boolean bool) {
        log.info("验收工作成果");
        try {
            return worksheetMakerService.checkAchievement(worksheetMakerId, checkMoney, enterpriseId, bool);
        } catch (Exception e) {
            log.info("验收工作成果失败");
        }
        return R.fail("验收工作成果失败");
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
            //获取当前商户
            EnterpriseEntity enterpriseEntity = iUserClient.currentEnterprise(bladeUser);
            return worksheetService.getWorksheetDetailsByMaker(Condition.getPage(query.setDescs("create_time")), enterpriseEntity.getId(), makerId);
        } catch (Exception e) {
            log.error("根据创客ID查询工单异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_worksheet_by_enterprise_id")
    @ApiOperation(value = "获取当前商户所有已完毕的工单", notes = "获取当前商户所有已完毕的工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "worksheetNo", value = "工单编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "worksheetName", value = "工单名称", paramType = "query", dataType = "string")
    })
    public R getWorksheetByEnterpriseId(String worksheetNo, String worksheetName, Query query, BladeUser bladeUser) {

        log.info("获取当前商户所有已完毕的工单");
        try {
            //获取当前商户
            EnterpriseEntity enterpriseEntity = iUserClient.currentEnterprise(bladeUser);
            return worksheetService.getWorksheetByEnterpriseId(enterpriseEntity.getId(), worksheetNo, worksheetName, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("获取当前商户所有已完毕的工单异常", e);
        }
        return R.fail("查询失败");
    }


}
