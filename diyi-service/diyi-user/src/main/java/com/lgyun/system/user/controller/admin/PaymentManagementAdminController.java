package com.lgyun.system.user.controller.admin;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台端支付管理controller
 *
 * @author tzq
 * @since 2020-09-9 20:01:13
 */
@Slf4j
@RestController
@RequestMapping("/payment-management/admin")
@Validated
@AllArgsConstructor
@Api(value = "平台端---支付管理相关接口", tags = "平台端---支付管理相关接口")
public class PaymentManagementAdminController {


}
