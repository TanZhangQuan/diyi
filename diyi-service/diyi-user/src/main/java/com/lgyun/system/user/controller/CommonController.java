package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.system.user.dto.MakerWechatLoginDto;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.ICommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/common")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "公用接口", tags = "公用接口")
public class CommonController {
    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    private final ICommonService iCommonService;
    private final AliyunOssService ossService;

    @PostMapping("/oss_image_upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public R ossImageUpload(@ApiParam(value = "图片") @NotNull(message = "请选择上传图片") @RequestParam(required = false) MultipartFile file) {

        logger.info("上传文件");
        try {
            if (file.isEmpty()) {
                return R.fail("上传文件不能为空");
            }
            // 获取上传文件的后缀
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            // 上传文件中
            String url = ossService.uploadSuffix(file.getBytes(), suffix);
            //保存图片url信息
            return R.data(url);

        } catch (Exception e) {
            logger.error("上传文件异常", e);
        }

        return R.fail("上传文件失败");
    }

    @GetMapping("/wechat_authorization")
    @ApiOperation(value = "微信授权", notes = "微信授权")
    public R wechatAuthorization(@ApiParam(value = "微信授权code") @NotNull(message = "请填写微信授权code") @RequestParam(required = false) String code) {

        logger.info("微信授权");
        try {
            return iCommonService.wechatAuthorization(code);
        } catch (Exception e) {
            logger.error("微信授权异常", e);
        }
        return R.fail("微信授权失败");
    }

    @PostMapping("/maker_wechat_login")
    @ApiOperation(value = "创客微信登陆", notes = "创客微信登陆")
    public R makerWechatLogin(@Valid @RequestBody MakerWechatLoginDto makerWechatLoginDto) {

        logger.info("创客微信登陆");
        try {
            return iCommonService.makerWechatLogin(makerWechatLoginDto);
        } catch (Exception e) {
            logger.error("创客登陆异常", e);
        }
        return R.fail("创客登陆失败");
    }

}
