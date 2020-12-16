package com.lgyun.system.user.controller.relBureau;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddOrUpdateRelBureauNoticeDTO;
import com.lgyun.system.user.dto.RelBureauNoticeFileListDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.service.IRelBureauNoticeReadService;
import com.lgyun.system.user.service.IRelBureauNoticeService;
import com.lgyun.system.user.service.IRelBureauService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/rel-bureau/notice")
@Validated
@AllArgsConstructor
@Api(value = "相关局端---通知管理模块相关接口", tags = "相关局端---通知管理模块相关接口")
public class NoticeRelBureauController {

    private IRelBureauService relBureauService;
    private IRelBureauNoticeService relBureauNoticeService;
    private IRelBureauNoticeReadService relBureauNoticeReadService;

    @PostMapping("/add-or-update-rel-bureau-notice")
    @ApiOperation(value = "添加或编辑相关局通知", notes = "添加或编辑相关局通知")
    public R addOrUpdateRelBureauNotice(@Valid @RequestBody AddOrUpdateRelBureauNoticeDTO addOrUpdateRelBureauNoticeDTO, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return relBureauNoticeService.addOrUpdateRelBureauNotice(relBureauEntity.getId(), addOrUpdateRelBureauNoticeDTO);
    }

    @GetMapping("/query-rel-bureau-notice-update-detail")
    @ApiOperation(value = "查询相关局通知编辑详情", notes = "查询相关局通知编辑详情")
    public R queryRelBureauNoticeUpdateDetail(@ApiParam("相关局通知") @NotNull(message = "请选择相关局通知") @RequestParam(required = false) Long relBureauNoticeId, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauNoticeService.queryRelBureauNoticeUpdateDetail(relBureauNoticeId);
    }

    @GetMapping("/query-rel-bureau-notice-list")
    @ApiOperation(value = "查询相关局通知列表", notes = "查询相关局通知列表")
    public R queryRelBureauNoticeList(RelBureauNoticeFileListDTO relBureauNoticeFileListDTO, Query query, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return relBureauNoticeService.queryRelBureauNoticeList(relBureauEntity.getId(), true, relBureauNoticeFileListDTO, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-rel-bureau-notice-detail")
    @ApiOperation(value = "查询相关局通知详情", notes = "查询相关局通知详情")
    public R queryRelBureauNoticeDetail(@ApiParam("相关局通知") @NotNull(message = "请选择相关局通知") @RequestParam(required = false) Long relBureauNoticeId, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauNoticeService.queryRelBureauNoticeDetail(relBureauNoticeId, false, null, null);
    }

    @GetMapping("/query-read-service-provider-list")
    @ApiOperation(value = "查询相关局通知已读服务商", notes = "查询相关局通知已读服务商")
    public R queryReadServiceProviderList(@ApiParam("相关局通知") @NotNull(message = "请选择相关局通知") @RequestParam(required = false) Long relBureauNoticeId, Query query, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauNoticeReadService.queryReadServiceProviderList(relBureauNoticeId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/delete-rel-bureau-notice")
    @ApiOperation(value = "删除相关局通知", notes = "删除相关局通知")
    public R deleteRelBureauNotice(@ApiParam("相关局通知") @NotNull(message = "请选择相关局通知") @RequestParam(required = false) Long relBureauNoticeId, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return relBureauNoticeService.deleteRelBureauNotice(relBureauEntity.getId(), relBureauNoticeId);
    }

    @PostMapping("/update-rel-bureau-notice-state")
    @ApiOperation(value = "修改相关局通知状态", notes = "修改相关局通知状态")
    public R updateRelBureauNoticeState(@ApiParam("相关局通知") @NotNull(message = "请选择相关局通知") @RequestParam(required = false) Long relBureauNoticeId,
                                        @ApiParam("相关局通知状态") @NotNull(message = "请选择相关局通知状态") @RequestParam(required = false) RelBureauNoticeFileState relBureauNoticeFileState,
                                        BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return relBureauNoticeService.updateRelBureauNoticeState(relBureauEntity.getId(), relBureauNoticeId, relBureauNoticeFileState);
    }

}
