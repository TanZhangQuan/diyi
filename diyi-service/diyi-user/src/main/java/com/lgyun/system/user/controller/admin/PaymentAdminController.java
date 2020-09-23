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
 * 平台端---支付管理模块相关接口
 *
 * @author tzq
 * @date 2020-09-9
 */
@RestController
@RequestMapping("/admin/payment")
@Validated
@AllArgsConstructor
@Api(value = "平台端---支付管理模块相关接口", tags = "平台端---支付管理模块相关接口")
public class PaymentAdminController {

    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询所有商户", notes = "查询所有商户")
    public R queryEnterpriseList(QueryEnterpriseListPaymentDTO queryEnterpriseListPaymentDTO, Query query) {
        return enterpriseService.queryEnterpriseListPayment(queryEnterpriseListPaymentDTO, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderList(QueryServiceProviderListPaymentDTO queryServiceProviderListPaymentDTO, Query query) {
        return enterpriseService.queryServiceProviderListPayment(queryServiceProviderListPaymentDTO, Condition.getPage(query.setDescs("create_time")));
    }

}
