package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author .
 * @date 2020/11/5.
 * @time 19:00.
 */
@RestController
@RequestMapping("/admin/invoice-tax")
@Validated
@AllArgsConstructor
@Api(value = "平台端---发票/完税证明管理模块相关接口", tags = "平台端---发票/完税证明管理模块相关接口")
public class InvoiceTaxAdminController {

    private IAdminService adminService;
    private IAgreementService agreementService;

    @GetMapping("/query-service-agreement-state")
    @ApiOperation(value = "平台查询服务商合同的签署状态", notes = "平台查询服务商合同的签署状态")
    public R queryServiceAgreementState(BladeUser bladeUser, Query query, String serviceProviderName) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryServiceAgreementState(serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }
}
