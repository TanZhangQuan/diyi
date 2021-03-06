package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/maker/worksheet")
@Validated
@AllArgsConstructor
@Api(value = "创客端---抢/派工单管理模块相关接口", tags = "创客端---抢/派工单管理模块相关接口")
public class WorksheetMakerController {

    private IUserClient userClient;
    private IWorksheetService worksheetService;
    private IWorksheetMakerService worksheetMakerService;

    @PostMapping("/grab-worksheet")
    @ApiOperation(value = "抢单", notes = "抢单")
    public R grabWorksheet(@ApiParam(value = "工单", required = true) @NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetService.orderGrabbing(worksheetId, makerEntity.getId());
    }

    @GetMapping("/query-maker-worksheet-list")
    @ApiOperation(value = "查询当前创客工单", notes = "查询当前创客工单")
    public R queryMakerWorksheetList(@ApiParam(value = "工单的状态") @NotNull(message = "请选择工单的状态") @RequestParam(required = false) Integer worksheetState, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetService.findXiaoPage(Condition.getPage(query.setDescs("ws.create_time")), worksheetState, makerEntity.getId());
    }

    @PostMapping("/submit-achievement")
    @ApiOperation(value = "提交工作成果", notes = "提交工作成果")
    public R submitAchievement(@NotNull(message = "请输入工单的状态") @RequestParam(required = false) Long worksheetMakerId,
                               @NotNull(message = "请输入工单说明") @RequestParam(required = false) String achievementDesc,
                               @NotNull(message = "请上传工作成果附件") @RequestParam(required = false) String achievementFiles, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetMakerService.submitAchievement(worksheetMakerId, achievementDesc, achievementFiles, worksheetService);
    }

    @GetMapping("/query-worksheet-detail")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R queryWorksheetDetail(@ApiParam(value = "工单") @NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetMakerId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getWorksheetDetails(worksheetMakerId);
    }

    @GetMapping("/query-worksheet-list-by-maker-id")
    @ApiOperation(value = "根据创客查询工单", notes = "根据创客查询工单")
    public R queryWorksheetListByMakerId(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getWorksheetDetailsByMaker(null, makerId, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-worksheet-list-by-worksheet-no")
    @ApiOperation(value = "根据工单编号查询工单", notes = "根据工单编号查询工单")
    public R queryWorksheetListByWorksheetNo(String worksheetNo, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getByWorksheetNo(worksheetNo);
    }

    @GetMapping("/query-worksheet-list-by-worksheet-id")
    @ApiOperation(value = "根据工单ID查询工单", notes = "根据工单ID查询工单")
    public R queryWorksheetListByWorksheetId(Long worksheetId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getByWorksheetId(worksheetId);
    }
}
