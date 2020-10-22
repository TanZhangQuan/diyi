package com.lgyun.system.user.controller.enterprise;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.MakerAddDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.excel.MakerImportListener;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@RestController
@RequestMapping("/enterprise/natural-person-maker")
@Validated
@AllArgsConstructor
@Api(value = "商户端---自然人创客管理模块相关接口", tags = "商户端---自然人创客管理模块相关接口")
public class NaturalPersonMakerEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private IMakerService makerService;
    private IMakerEnterpriseService makerEnterpriseService;

    @GetMapping("/query-relevance-maker-list")
    @ApiOperation(value = "查询当前商户的所有关联或关注创客", notes = "查询当前商户的所有关联或关注创客")
    public R queryRelevanceMakerList(String keyword, @ApiParam(value = "创客商户关系") @NotNull(message = "请选择创客商户关系") @RequestParam(required = false) RelationshipType relationshipType, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerEnterpriseService.getEnterpriseMakerList(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), relationshipType, null, keyword);
    }

    @PostMapping("/create-maker")
    @ApiOperation(value = "新增单个创客", notes = "新增单个创客")
    public R createMaker(@Valid @RequestBody MakerAddDTO makerAddDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerService.makerAdd(makerAddDto, enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/import-makers")
    @ApiOperation(value = "导入创客", notes = "导入创客")
    public R importMakers(@ApiParam(value = "文件") @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file, BladeUser bladeUser) throws IOException {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        //判断文件内容是否为空
        if (file.isEmpty()) {
            return R.fail("Excel文件不能为空");
        }

        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename();
        if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
            return R.fail("请选择Excel文件");
        }

        MakerImportListener makerImportListener = new MakerImportListener(makerService, enterpriseWorkerEntity.getEnterpriseId());
        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        ExcelReaderBuilder builder = EasyExcel.read(inputStream, MakerExcel.class, makerImportListener);
        builder.doReadAll();

        return R.success("操作成功");
    }

    @GetMapping("/query-maker-detail")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情")
    public R queryMakerDetail(@ApiParam(value = "创客") @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerService.getMakerDetailById(enterpriseWorkerEntity.getEnterpriseId(), makerId);
    }

    @PostMapping("/cancel-relevance-maker-list")
    @ApiOperation(value = "批量取消创客关联或关注", notes = "批量取消创客关联或关注")
    public R cancelRelevanceMakerList(@ApiParam(value = "创客") @NotEmpty(message = "请选择要取消关联的创客") @RequestParam(required = false) Set<Long> makerIds,
                                   @ApiParam(value = "创客商户关系") @NotNull(message = "请选择创客商户关系") @RequestParam(required = false) RelationshipType relationshipType,
                                   BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerEnterpriseService.cancelRelMakers(makerIds, relationshipType, enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/relevance-maker-list")
    @ApiOperation(value = "批量关联创客", notes = "批量关联创客")
    public R relevanceMakerList(@ApiParam(value = "创客") @NotEmpty(message = "请选择要关联的创客") @RequestParam(required = false) Set<Long> makerIds, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerEnterpriseService.relMakers(makerIds, enterpriseWorkerEntity.getEnterpriseId());
    }

}
