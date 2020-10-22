package com.lgyun.system.order.controller.admin;


import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/partner")
@Validated
@AllArgsConstructor
@Api(value = "平台端---合伙人管理模块相关接口", tags = "平台端---合伙人管理模块相关接口")
public class PartnerAdminController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient userClient;

    @GetMapping("/all-transaction")
    @ApiOperation(value = "查询合伙人交易数据", notes = "查询合伙人交易数据")
    public R allTransaction(BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.allTransaction();
    }

    @GetMapping("/query-partner-all-service-provider")
    @ApiOperation(value = "合伙人可以看所有的服务商", notes = "合伙人可以看所有的服务商")
    public R queryAgentMainServiceProvider(Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.getPartnerAllServiceProvider(Condition.getPage(query.setDescs("create_time")));
    }
}
