package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * (平台)发票和完税证明接口
 *
 * @author jun.
 * @date 2020/7/22.
 * @time 16:24.
 */
@Slf4j
@RestController
@RequestMapping("/invoice/admin")
@Validated
@AllArgsConstructor
@Api(value = "(平台)发票和完税证明接口", tags = "(平台)发票和完税证明接口")
public class InvoiceAdminController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient iUserClient;

    @GetMapping("/getLumpSumInvoice")
    @ApiOperation(value = "服务商查询总包发票", notes = "服务商查询总包发票")
    public R getLumpSumInvoice(Query query, BladeUser bladeUser,
                               @RequestParam(required = false) String enterpriseName,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime,
                               @RequestParam InvoiceState companyInvoiceState) {
        log.info("服务商查询总包发票");
        try {
            return payEnterpriseService.getServiceLumpSumInvoice(1292662449439494141L,enterpriseName,startTime,endTime,companyInvoiceState, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("服务商查询总包发票失败",e);
        }
        return R.fail("服务商查询总包发票失败");
    }
}
