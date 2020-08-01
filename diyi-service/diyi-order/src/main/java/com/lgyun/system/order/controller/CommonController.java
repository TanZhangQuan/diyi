package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.system.order.service.ICommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/common")
@Validated
@AllArgsConstructor
@Api(value = "公用接口", tags = "公用接口")
public class CommonController {

    private ICommonService iCommonService;

    @PostMapping("/oss_image_upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public R ossFileUpload(@ApiParam(value = "文件") @NotNull(message = "请选择上传文件") @RequestParam(required = false) MultipartFile file) {

        log.info("上传文件");
        try {
            return iCommonService.ossImageUpload(file);
        } catch (Exception e) {
            log.error("上传文件异常", e);
        }

        return R.fail("上传文件失败");
    }

    @PostMapping("/oss_excel_upload")
    @ApiOperation(value = "上传Excel文件", notes = "上传Excel文件")
    public R ossExcelUpload(@ApiParam(value = "文件") @NotNull(message = "请选择上传Excel文件") @RequestParam(required = false) MultipartFile file) {

        log.info("上传Excel文件");
        try {
            return iCommonService.ossExcelUpload(file);
        } catch (Exception e) {
            log.error("上传Excel文件异常", e);
        }

        return R.fail("上传失败");
    }

}
