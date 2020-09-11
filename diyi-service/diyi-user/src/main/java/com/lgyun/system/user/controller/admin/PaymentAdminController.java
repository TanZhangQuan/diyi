package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListPaymentDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListPaymentDTO;
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
@RequestMapping("/admin/payment")
@Validated
@AllArgsConstructor
@Api(value = "平台端---支付管理模块相关接口", tags = "平台端---支付管理模块相关接口")
public class PaymentAdminController {

    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-list-payment")
    @ApiOperation(value = "查询所有商户", notes = "查询所有商户")
    public R queryEnterpriseListPayment(QueryEnterpriseListPaymentDTO queryEnterpriseListPaymentDTO, Query query) {

        log.info("查询所有商户");
        try {
            return enterpriseService.queryEnterpriseList(queryEnterpriseListPaymentDTO, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询所有商户异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-service-provider-list-payment")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderListPayment(QueryServiceProviderListPaymentDTO queryServiceProviderListPaymentDTO, Query query) {

        log.info("查询所有服务商");
        try {
            return enterpriseService.queryServiceProviderList(queryServiceProviderListPaymentDTO, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询所有商户异常", e);
        }
        return R.fail("查询失败");
    }

}
