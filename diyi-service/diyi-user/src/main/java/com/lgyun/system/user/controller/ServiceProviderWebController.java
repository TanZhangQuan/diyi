package com.lgyun.system.user.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
@Slf4j
@RestController
@RequestMapping("/web/service_provider")
@Validated
@AllArgsConstructor
@Api(value = "服务商信息相关接口(管理端)", tags = "服务商信息相关接口(管理端)")
public class ServiceProviderWebController {

}
