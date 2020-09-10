package com.lgyun.system.user.controller.admin;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台端---个独管理管理controller
 *
 * @author tzq
 * @date 2020-09-9
 */
@Slf4j
@RestController
@RequestMapping("/admin/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "平台端---个独管理管理模块相关接口", tags = "平台端---个独管理管理模块相关接口")
public class IndividualEnterpriseController {

}
