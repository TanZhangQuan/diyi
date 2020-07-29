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
            e.printStackTrace();
            return R.fail("发布订单失败");
        }
    }

    @PostMapping("/orderGrabbing")
    @ApiOperation(value = "抢单", notes = "抢单")
    public R orderGrabbing(Long worksheetId, BladeUser bladeUser) {
        log.info("抢单");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return worksheetService.orderGrabbing(worksheetId, makerEntity.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("抢单失败");
        }
    }

    @GetMapping("/findXiaoPage")
    @ApiOperation(value = "小程查询工单", notes = "小程查询工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "worksheetState", value = "工单状态：1代表待抢单，2已接单，3已交付", paramType = "query", dataType = "string"),
    })
    public R findXiaoPage(Query query, Integer worksheetState, BladeUser bladeUser) {
        MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
        if (null == worksheetState || (worksheetState != 1 && worksheetState != 2 && worksheetState != 3)) {
            return R.fail("参数错误");
        }
        return worksheetService.findXiaoPage(Condition.getPage(query), worksheetState, makerEntity.getId());
    }

    @PostMapping("/submitachievement")
    @ApiOperation(value = "提交工作成果", notes = "提交工作成果")
    public R submitachievement(Long worksheetMakerId, String achievementDesc, String achievementFiles) {
        log.info("提交工作成果");
        try {
            WorksheetMakerEntity worksheetMakerEntity = worksheetMakerService.getById(worksheetMakerId);
            WorksheetEntity worksheetEntity = worksheetService.getById(worksheetMakerEntity.getWorksheetId());
            if (!(worksheetEntity.getWorksheetState().equals(WorksheetState.CLOSED) || worksheetEntity.getWorksheetState().equals(WorksheetState.CHECKACCEPT))) {
                return R.fail("工单暂时不能提交工作成果，稍后再试");
            }
            return worksheetMakerService.submitAchievement(worksheetMakerEntity, achievementDesc, achievementFiles);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("提交工作成果失败");
        }

    }

    @PostMapping("/checkAchievement")
    @ApiOperation(value = "验收工作成果", notes = "验收工作成果")
    public R checkAchievement(Long worksheetMakerId, BigDecimal checkMoney, Long enterpriseId, Boolean bool) {
        log.info("验收工作成果");
        try {
            return worksheetMakerService.checkAchievement(worksheetMakerId, checkMoney, enterpriseId, bool);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("验收工作成果失败");
        }
    }

    @GetMapping("/getWorksheetDetails")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R getWorksheetDetails(Long worksheetMakerId) {
        log.info("查询工单详情");
        try {
            return worksheetService.getWorksheetDetails(worksheetMakerId);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("查询工单详情失败");
        }
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

}
