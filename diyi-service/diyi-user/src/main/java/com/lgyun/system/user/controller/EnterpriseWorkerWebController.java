package com.lgyun.system.user.controller;

import com.lgyun.system.user.service.IEnterpriseWorkerService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@RestController
@RequestMapping("/web/enterprise_worker")
@Validated
@AllArgsConstructor
@Api(value = "商户员工相关接口(管理端)", tags = "商户员工相关接口(管理端)")
public class EnterpriseWorkerWebController {

    private IEnterpriseWorkerService enterpriseWorkerService;


}
