package com.lgyun.system.user.controller.relBureau;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddOrUpdateRelBureauFileDTO;
import com.lgyun.system.user.dto.RelBureauNoticeFileListDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.service.IRelBureauFileReadService;
import com.lgyun.system.user.service.IRelBureauFileService;
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
@RequestMapping("/rel-bureau/file")
@Validated
@AllArgsConstructor
@Api(value = "相关局端---监督文件管理模块相关接口", tags = "相关局端---监督文件管理模块相关接口")
public class FileRelBureauController {

    private IRelBureauService relBureauService;
    private IRelBureauFileService relBureauFileService;
    private IRelBureauFileReadService relBureauFileReadService;

    @PostMapping("/add-or-update-rel-bureau-file")
    @ApiOperation(value = "添加或编辑相关局监督文件", notes = "添加或编辑相关局监督文件")
    public R addOrUpdateRelBureauFile(@Valid @RequestBody AddOrUpdateRelBureauFileDTO addOrUpdateRelBureauFileDTO, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return relBureauFileService.addOrUpdateRelBureauFile(relBureauEntity.getId(), addOrUpdateRelBureauFileDTO);
    }

    @GetMapping("/query-rel-bureau-file-update-detail")
    @ApiOperation(value = "查询相关局监督文件编辑详情", notes = "查询相关局监督文件编辑详情")
    public R queryRelBureauFileUpdateDetail(@ApiParam("相关局监督文件") @NotNull(message = "请选择相关局监督文件") @RequestParam(required = false) Long relBureauFileId, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauFileService.queryRelBureauFileUpdateDetail(relBureauFileId);
    }

    @GetMapping("/query-rel-bureau-file-list")
    @ApiOperation(value = "查询相关局监督文件列表", notes = "查询相关局监督文件列表")
    public R queryRelBureauFileList(RelBureauNoticeFileListDTO relBureauNoticeFileListDTO, Query query, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return relBureauFileService.queryRelBureauFileList(relBureauEntity.getId(), true, relBureauNoticeFileListDTO, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-rel-bureau-file-detail")
    @ApiOperation(value = "查询相关局监督文件详情", notes = "查询相关局监督文件详情")
    public R queryRelBureauFileDetail(@ApiParam("相关局监督文件") @NotNull(message = "请选择相关局监督文件") @RequestParam(required = false) Long relBureauFileId, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauFileService.queryRelBureauFileDetail(relBureauFileId, false, null, null);
    }

    @GetMapping("/query-read-service-provider-list")
    @ApiOperation(value = "查询相关局监督文件已读服务商", notes = "查询相关局监督文件已读服务商")
    public R queryReadServiceProviderList(@ApiParam("相关局监督文件") @NotNull(message = "请选择相关局监督文件") @RequestParam(required = false) Long relBureauFileId, Query query, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauFileReadService.queryReadServiceProviderList(relBureauFileId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/delete-rel-bureau-file")
    @ApiOperation(value = "删除相关局监督文件", notes = "删除相关局监督文件")
    public R deleteRelBureauFile(@ApiParam("相关局监督文件") @NotNull(message = "请选择相关局监督文件") @RequestParam(required = false) Long relBureauFileId, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return relBureauFileService.deleteRelBureauFile(relBureauEntity.getId(), relBureauFileId);
    }

    @PostMapping("/update-rel-bureau-file-state")
    @ApiOperation(value = "修改相关局监督文件状态", notes = "修改相关局监督文件状态")
    public R updateRelBureauFileState(@ApiParam("相关局监督文件") @NotNull(message = "请选择相关局监督文件") @RequestParam(required = false) Long relBureauFileId,
                                      @ApiParam("相关局监督文件状态") @NotNull(message = "请选择相关局监督文件状态") @RequestParam(required = false) RelBureauNoticeFileState relBureauNoticeFileState,
                                      BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return relBureauFileService.updateRelBureauFileState(relBureauEntity.getId(), relBureauFileId, relBureauNoticeFileState);
    }

}
