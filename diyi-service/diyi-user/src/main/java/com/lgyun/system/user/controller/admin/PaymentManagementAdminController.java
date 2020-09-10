package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListDTO;
import com.lgyun.system.user.service.IEnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台端---支付管理controller
 *
 * @author tzq
 * @date 2020-09-9
 */
@Slf4j
@RestController
@RequestMapping("/admin/payment-management")
@Validated
@AllArgsConstructor
@Api(value = "平台端---支付管理模块相关接口", tags = "平台端---支付管理模块相关接口")
public class PaymentManagementAdminController {

    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询所有商户", notes = "查询所有商户")
    public R queryEnterpriseList(QueryEnterpriseListDTO queryEnterpriseListDTO, Query query) {

        log.info("查询所有商户");
        try {
            return enterpriseService.queryEnterpriseList(queryEnterpriseListDTO, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询所有商户异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderList(QueryServiceProviderListDTO queryServiceProviderListDTO, Query query) {

        log.info("查询所有服务商");
        try {
            return enterpriseService.queryServiceProviderList(queryServiceProviderListDTO, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询所有商户异常", e);
        }
        return R.fail("查询失败");
    }

}
