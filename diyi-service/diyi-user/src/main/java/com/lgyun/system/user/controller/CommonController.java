package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.system.user.service.ICommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "公用接口", tags = "公用接口")
public class CommonController {
    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    private final ICommonService iCommonService;

    @PostMapping("/oss_image_upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public R ossImageUpload(@ApiParam(value = "图片") @NotNull(message = "请选择上传图片") @RequestParam(required = false) MultipartFile file) {

        logger.info("上传文件");
        try {
            return iCommonService.ossImageUpload(file);
        } catch (Exception e) {
            logger.error("上传文件异常", e);
        }

        return R.fail("上传文件失败");
    }

}
