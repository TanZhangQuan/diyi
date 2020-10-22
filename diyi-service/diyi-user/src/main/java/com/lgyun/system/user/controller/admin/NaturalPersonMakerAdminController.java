package com.lgyun.system.user.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.MakerAddDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.excel.MakerImportListener;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/admin/natural-person-maker")
@Validated
@AllArgsConstructor
@Api(value = "平台端---自然人创客管理模块相关接口", tags = "平台端---自然人创客管理模块相关接口")
public class NaturalPersonMakerAdminController {

    private IAdminService adminService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IMakerService makerService;
    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-id-and-name-list")
    @ApiOperation(value = "查询所有商户的编号名称", notes = "查询所有商户的编号名称")
    public R queryEnterpriseIdAndNameList(@ApiParam(value = "商户名称") @RequestParam(required = false) String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseListNaturalPersonMaker(enterpriseName, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/create-maker")
    @ApiOperation(value = "新增单个创客", notes = "新增单个创客")
    public R createMaker(@ApiParam(value = "商户编号", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                       @Valid @RequestBody MakerAddDTO makerAddDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.makerAdd(makerAddDto, enterpriseId);
    }

    @PostMapping("import-maker-list")
    @ApiOperation(value = "导入创客", notes = "导入创客")
    public R importMakerList(@ApiParam(value = "商户编号", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                        @ApiParam(value = "Excel文件", required = true) @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file, BladeUser bladeUser) throws IOException {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        //判断文件内容是否为空
        if (file.isEmpty()) {
            return R.fail("Excel文件不能为空");
        }

        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename();
        if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
            return R.fail("请选择Excel文件");
        }

        MakerImportListener makerImportListener = new MakerImportListener(makerService, enterpriseId);
        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        ExcelReaderBuilder builder = EasyExcel.read(inputStream, MakerExcel.class, makerImportListener);
        builder.doReadAll();

        return R.success("操作成功");
    }

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询所有创客", notes = "查询所有创客")
    public R queryMakerList(@ApiParam(value = "商户编号", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) CertificationState certificationState,
                            @ApiParam(value = "搜索创客关键字：请输入创客编号/姓名/手机号") @RequestParam(required = false) String keyword, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerEnterpriseService.getEnterpriseMakerList(Condition.getPage(query.setDescs("create_time")), null, null, certificationState, keyword);
    }

    @GetMapping("/query-maker-detail")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情")
    public R queryMakerDetail(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.getMakerDetailById(null, makerId);
    }

}
