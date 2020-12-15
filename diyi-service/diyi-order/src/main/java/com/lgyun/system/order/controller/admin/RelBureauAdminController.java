package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/rel-bureau")
@Validated
@AllArgsConstructor
@Api(value = "平台端---相关局管理模块相关接口", tags = "平台端---相关局管理模块相关接口")
public class RelBureauAdminController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/query-rel-bureau-transaction")
    @ApiOperation(value = "查询相关局交易情况数据", notes = "查询相关局交易情况数据")
    public R queryRelBureauTransaction(@ApiParam("相关局") @NotNull(message = "请选择相关局") @RequestParam(required = false) Long relBureauId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryRelBureauTransaction(relBureauId);
    }

}
