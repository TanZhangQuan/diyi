package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.system.user.service.ICommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 公用文件上传接口
 *
 * @author tzq
 * @since 2020/8/18 23:04
 */
@RestController
@RequestMapping("/common")
@Validated
@AllArgsConstructor
@Api(value = "公用接口", tags = "公用接口")
public class CommonController {

    private ICommonService iCommonService;

    @PostMapping("/oss-image-upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public R ossImageUpload(@ApiParam(value = "文件") @NotNull(message = "请选择上传文件") @RequestParam(required = false) MultipartFile file) throws Exception {
        return iCommonService.ossImageUpload(file);
    }

    @PostMapping("/oss-excel-upload")
    @ApiOperation(value = "上传Excel文件", notes = "上传Excel文件")
    public R ossExcelUpload(@ApiParam(value = "文件") @NotNull(message = "请选择上传Excel文件") @RequestParam(required = false) MultipartFile file) throws Exception {
        return iCommonService.ossExcelUpload(file);
    }

}
