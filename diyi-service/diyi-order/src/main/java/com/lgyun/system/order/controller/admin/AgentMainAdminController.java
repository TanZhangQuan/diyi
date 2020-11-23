package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddOrUpdateAddressDTO;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/agent-main")
@Validated
@AllArgsConstructor
@Api(value = "平台端---渠道管理模块相关接口", tags = "平台端---渠道管理模块相关接口")
public class AgentMainAdminController {

    private IUserClient userClient;
    private IAddressService addressService;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询渠道商所有收货地址信息", notes = "查询渠道商所有收货地址信息")
    public R queryAddressList(@ApiParam(value = "渠道商") @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.queryAddressList(ObjectType.AGENTMAINPEOPLE, agentMainId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-address-update-detail")
    @ApiOperation(value = "查询编辑收货地址详情", notes = "查询编辑收货地址详情")
    public R queryAddressUpdateDetail(@ApiParam(value = "收货地址") @NotNull(message = "请选择收货地址") @RequestParam(required = false) Long addressId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.queryAddressUpdateDetail(addressId);
    }

    @PostMapping("/add-or-update-address")
    @ApiOperation(value = "添加/编辑收货地址", notes = "添加/编辑收货地址")
    public R addOrUpdateAddress(@ApiParam(value = "渠道商编号") @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId,
                                @Valid @RequestBody AddOrUpdateAddressDTO addOrUpdateAddressDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.addOrUpdateAddress(addOrUpdateAddressDto, agentMainId, ObjectType.AGENTMAINPEOPLE);
    }

    @PostMapping("/delete-address")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public R deleteAddress(@ApiParam(value = "收货地址", required = true) @NotNull(message = "请选择要删除的收货地址") @RequestParam(required = false) Long addressId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.deleteAddress(addressId);
    }

    @GetMapping("/query-agent-main-enterprise-transaction")
    @ApiOperation(value = "查询渠道商-商户交易数据", notes = "查询渠道商-商户交易数据")
    public R queryAgentMainEnterpriseTransaction(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryAgentMainEnterpriseTransaction(agentMainId);
    }
    
    @GetMapping("/query-agent-main-service-provider-transaction")
    @ApiOperation(value = "查询渠道商-服务商交易数据", notes = "查询渠道商-服务商交易数据")
    public R queryAgentMainServiceProviderTransaction(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryAgentMainServiceProviderTransaction(agentMainId);
    }

}
