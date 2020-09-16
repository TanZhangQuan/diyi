package com.lgyun.system.user.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.MakerAddDto;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.excel.MakerImportListener;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 平台端---自然人创客管理controller
 *
 * @author tzq
 * @date 2020-09-9
 */
@Slf4j
@RestController
@RequestMapping("/admin/natural-person-maker")
@Validated
@AllArgsConstructor
@Api(value = "平台端---自然人创客管理模块相关接口", tags = "平台端---自然人创客管理模块相关接口")
public class NaturalPersonMakerAdminController {

    private IMakerEnterpriseService makerEnterpriseService;
    private IMakerService makerService;
    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-id-and-name-list")
    @ApiOperation(value = "查询所有商户的编号名称", notes = "查询所有商户的编号名称")
    public R queryEnterpriseIdAndNameList(@ApiParam(value = "商户名称") @RequestParam(required = false) String enterpriseName, Query query) {

        log.info("查询所有商户的编号名称");
        try {
            return enterpriseService.queryEnterpriseListNaturalPersonMaker(enterpriseName, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询所有商户的编号名称异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/save-maker")
    @ApiOperation(value = "新增单个创客", notes = "新增单个创客")
    public R saveMaker(@ApiParam(value = "商户编号") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, @Valid @RequestBody MakerAddDto makerAddDto) {

        log.info("新增单个创客");
        try {
            return makerService.makerAdd(makerAddDto, enterpriseId);
        } catch (Exception e) {
            log.error("新增单个创客异常", e);
        }

        return R.fail("新增失败");
    }

    @PostMapping("import-maker")
    @ApiOperation(value = "导入创客", notes = "导入创客")
    public R importUser(@ApiParam(value = "商户编号") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                        @ApiParam(value = "Excel文件") @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file) {

        log.info("导入创客");
        try {
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
        } catch (IOException e) {
            log.error("导入创客异常", e);
        }

        return R.success("操作成功");
    }

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询所有创客", notes = "查询所有创客")
    public R queryMakerList(@ApiParam(value = "商户编号") @NotNull(message = "请选择商户") @RequestParam(required = false) CertificationState certificationState,
                            @ApiParam(value = "搜索创客关键字：请输入创客编号/姓名/手机号") @RequestParam(required = false) String keyword, Query query) {

        log.info("查询所有创客");
        try {
            return makerEnterpriseService.getEnterpriseMakerList(Condition.getPage(query.setDescs("create_time")), null, null, certificationState, keyword);
        } catch (Exception e) {
            log.error("查询所有创客异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-maker-detail-by-maker-id")
    @ApiOperation(value = "根据创客ID查询创客详情", notes = "根据创客ID查询创客详情")
    public R queryMakerDetailByMakerId(@ApiParam(value = "创客ID") @NotNull(message = "请输入创客编号") @RequestParam(required = false) Long makerId) {

        log.info("根据创客ID查询创客详情");
        try {
            return makerService.getMakerDetailById(null, makerId);
        } catch (Exception e) {
            log.error("根据创客ID查询创客详情异常", e);
        }
        return R.fail("查询失败");
    }

}
