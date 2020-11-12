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

@RestController
@RequestMapping("/common")
@Validated
@AllArgsConstructor
@Api(value = "公用接口", tags = "公用接口")
public class CommonController {

    private ICommonService commonService;

    @PostMapping("/oss-file-upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public R ossFileUpload(@ApiParam(value = "文件", required = true) @NotNull(message = "请选择上传文件") @RequestParam(required = false) MultipartFile file,
                           @ApiParam(value = "文件类型") @RequestParam(required = false) String suffix) throws Exception {
        return commonService.ossImageUpload(file, suffix);
    }

}
