package com.lgyun.system.user.controller.partner;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partner/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "合伙人端---自助开票管理模块相关接口", tags = "合伙人端---自助开票管理模块相关接口")
public class SelfHelpInvoicePartnerController {

}
