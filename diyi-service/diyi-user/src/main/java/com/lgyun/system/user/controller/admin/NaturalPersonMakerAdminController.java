package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.ImportMakerListDTO;
import com.lgyun.system.user.dto.MakerAddDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/natural-person-maker")
@Validated
@AllArgsConstructor
@Api(value = "平台端---自然人创客管理模块相关接口", tags = "平台端---自然人创客管理模块相关接口")
public class NaturalPersonMakerAdminController {

    private IAdminService adminService;
    private IMakerService makerService;
    private IEnterpriseServiceProviderService enterpriseServiceProviderService;

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询所有创客", notes = "查询所有创客")
    public R queryMakerList(@ApiParam(value = "商户编号", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) CertificationState certificationState,
                            @ApiParam(value = "搜索创客关键字：请输入创客编号/姓名/手机号") @RequestParam(required = false) String keyword, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.queryMakerList(null, null, null, certificationState, keyword, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/create-maker")
    @ApiOperation(value = "新增单个创客", notes = "新增单个创客")
    public R createMaker(@Valid @RequestBody MakerAddDTO makerAddDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.makerAdd(makerAddDto, null);
    }

    @PostMapping("read-maker-list-excel")
    @ApiOperation(value = "读取Excel表获取导入的创客列表", notes = "读取Excel表获取导入的创客列表")
    public R readMakerListExcel(@ApiParam(value = "Excel文件", required = true) @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file, BladeUser bladeUser) throws IOException {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.readMakerListExcel(file);
    }

    @PostMapping("import-maker-list")
    @ApiOperation(value = "导入创客", notes = "导入创客")
    public R importMakerList(@Valid @RequestBody List<ImportMakerListDTO> importMakerListDTOList, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.importMaker(importMakerListDTOList, null);
    }

    @GetMapping("/query-maker-detail")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情")
    public R queryMakerDetail(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.queryMakerDetail(makerId);
    }

    @GetMapping("/query-enterprise-id-and-name-list")
    @ApiOperation(value = "查询所有商户的编号名称", notes = "查询所有商户的编号名称")
    public R queryEnterpriseIdAndNameList(@ApiParam(value = "商户名称") @RequestParam(required = false) String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseServiceProviderService.queryEnterpriseIdAndNameList(null, enterpriseName, Condition.getPage(query.setDescs("create_time")));
    }

}
