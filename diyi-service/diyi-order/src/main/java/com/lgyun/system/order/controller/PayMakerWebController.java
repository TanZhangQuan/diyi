package com.lgyun.system.order.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创客支付明细表控制器
 *
 * @author liangfeihu
 * @since 2020-08-27 11:13:13
 */
@Slf4j
@RestController
@RequestMapping("/web/pay_maker")
@Validated
@AllArgsConstructor
@Api(value = "创客支付明细表相关接口(管理端)", tags = "创客支付明细表相关接口(管理端)")
public class PayMakerWebController {

}
