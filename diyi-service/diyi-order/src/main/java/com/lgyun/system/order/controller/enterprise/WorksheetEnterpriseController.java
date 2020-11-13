package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.ReleaseWorksheetDTO;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@RestController
@RequestMapping("/enterprise/worksheet")
@Validated
@AllArgsConstructor
@Api(value = "商户端---工单管理模块相关接口", tags = "商户端---工单管理模块相关接口")
public class WorksheetEnterpriseController {

    private IUserClient userClient;
    private IWorksheetService worksheetService;
    private IWorksheetMakerService worksheetMakerService;

    @PostMapping("/create-worksheet")
    @ApiOperation(value = "发布工单", notes = "发布工单")
    public R createWorksheet(@Valid @RequestBody ReleaseWorksheetDTO releaseWorksheetDTO, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        releaseWorksheetDTO.setEnterpriseId(enterpriseWorkerEntity.getEnterpriseId());

        return worksheetService.releaseWorksheet(releaseWorksheetDTO);
    }

    @GetMapping("query-worksheet-list")
    @ApiOperation(value = "查询工单", notes = "查询工单")
    public R queryWorksheetList(@ApiParam(value = "工单状态") @NotNull(message = "请选择工单状态") @RequestParam(required = false) WorksheetState worksheetState,
                                @RequestParam(required = false) String worksheetNo, @RequestParam(required = false) String worksheetName,
                                @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return worksheetService.getEnterpriseWorksheet(Condition.getPage(query.setDescs("ws.create_time")), enterpriseWorkerEntity.getEnterpriseId(), worksheetState, worksheetNo, worksheetName, startTime, endTime);
    }

    @PostMapping("delete-worksheet")
    @ApiOperation(value = "删除工单", notes = "删除工单")
    public R deleteWorksheet(@ApiParam(value = "工单") @NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        worksheetService.removeById(worksheetId);
        return R.success("删除成功");
    }

    @GetMapping("query-worksheet-detail")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R queryWorksheetDetail(@NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getWorksheetWebDetails(Condition.getPage(query.setDescs("wm.create_time")), worksheetId);
    }

    @PostMapping("/kick-out-maker")
    @ApiOperation(value = "工单踢出创客", notes = "工单踢出创客")
    public R kickOutMaker(@NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetMakerId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.kickOut(worksheetMakerId);
    }

    @PostMapping("/close-or-open-worksheet")
    @ApiOperation(value = "开单或关单", notes = "开单或关单")
    public R closeOrOpenWorksheet(@ApiParam(value = "工单", required = true) @NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId,
                                  @ApiParam(value = "1代表关闭，2开启", required = true) @NotNull(message = "请输入1代表关闭，2开启") @RequestParam(required = false) Integer variable, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.closeOrOpen(worksheetId, variable);
    }

    @PostMapping("/check-achievement")
    @ApiOperation(value = "验收工作成果", notes = "验收工作成果")
    public R checkAchievement(@NotNull(message = "请输入id") @RequestParam(required = false) Long worksheetMakerId, @NotNull(message = "请输入验证金额") @RequestParam(required = false) BigDecimal checkMoney,
                              @NotNull(message = "请输入验收的结果") @RequestParam(required = false) Boolean bool, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return worksheetMakerService.checkAchievement(worksheetMakerId, checkMoney, enterpriseWorkerEntity.getEnterpriseId(), bool);
    }

    @PostMapping("/close-or-open-worksheet-list")
    @ApiOperation(value = "批量开启或关闭工单", notes = "批量开启或关闭工单")
    public R closeOrOpenWorksheetList(@NotNull(message = "请选择工单") @RequestParam(required = false) String worksheetIds,
                                      @ApiParam(value = "1代表关闭，2开启", required = true) @NotNull(message = "请输入1代表关闭，2开启") @RequestParam(required = false) Integer variable, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.closeOrOpenList(worksheetIds, variable);
    }

    @PostMapping("/delete-worksheet-list")
    @ApiOperation(value = "批量删除工单", notes = "批量删除工单")
    public R deleteWorksheetList(@NotNull(message = "请选择工单") @RequestParam(required = false) String worksheetIds, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.deleteWorksheetList(worksheetIds);
    }

    @PostMapping("/check-whole-worksheet")
    @ApiOperation(value = "整体验收工单", notes = "整体验收工单")
    public R checkWholeWorksheet(@NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.wholeWorksheetCheck(worksheetId);
    }

    @GetMapping("/query-worksheet-list-by-maker-id")
    @ApiOperation(value = "根据创客查询工单", notes = "根据创客查询工单")
    public R queryWorksheetListByMakerId(@ApiParam(value = "创客") @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return worksheetService.getWorksheetDetailsByMaker(enterpriseWorkerEntity.getEnterpriseId(), makerId, Condition.getPage(query.setDescs("t1.create_time")));
    }
}
