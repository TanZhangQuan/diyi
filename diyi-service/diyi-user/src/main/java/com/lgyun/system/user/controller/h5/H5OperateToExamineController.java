package com.lgyun.system.user.controller.h5;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AuditState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.service.IOnlineSignPicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author jun.
 * @date 2021/3/22.
 * @time 13:54.
 */
@Slf4j
@RestController
@RequestMapping("/admin/h5-operate-to-examine")
@Validated
@AllArgsConstructor
@Api(value = "h5---运营审核", tags = "h5---运营审核")
public class H5OperateToExamineController {

    private IAdminService adminService;
    private IMakerService makerService;
    private IOnlineSignPicService onlineSignPicService;

    @GetMapping("/query-maker-to-examine-list")
    @ApiOperation(value = "待审核列表", notes = "待审核列表")
    public R queryMakerToExamineList(BladeUser bladeUser, Query query,@ApiParam(value = "创客名字") @RequestParam(required = false) String makerName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return makerService.queryMakerToExamineList(makerName,Condition.getPage(query.setDescs("s1.create_time")));
    }

    @PostMapping("to-examine-authorization")
    @ApiOperation(value = "审核一键授权", notes = "审核一键授权")
    public R toExamineAuthorization(BladeUser bladeUser,
                                    @ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId,
                                    @ApiParam(value = "审核状态", required = true) @NotNull(message = "审核状态不能为空") @RequestParam(required = false) AuditState auditState,
                                    @ApiParam(value = "驳回内容")@RequestParam(required = false) String rejectedExplanation) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return onlineSignPicService.toExamineAuthorization(makerId, auditState,rejectedExplanation);
    }

}
