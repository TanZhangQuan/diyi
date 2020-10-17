package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddressDTO;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 平台端---服务商管理模块相关接口
 *
 * @author tzq
 * @date 2020-09-9
 */
@RestController
@RequestMapping("/admin/service-provider")
@Validated
@AllArgsConstructor
@Api(value = "平台端---服务商管理模块相关接口", tags = "平台端---服务商管理模块相关接口")
public class ServiceProviderAdminController {

    private IUserClient userClient;
    private IAddressService addressService;

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询服务商所有收货地址信息", notes = "查询服务商所有收货地址信息")
    public R queryAddressList(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.queryAddressList(ObjectType.SERVICEPEOPLE, serviceProviderId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/add-or-update-address")
    @ApiOperation(value = "添加/编辑收货地址", notes = "添加/编辑收货地址")
    public R addOrUpdateAddress(@ApiParam(value = "服务商编号") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                @Valid @RequestBody AddressDTO addressDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.addOrUpdateAddress(addressDto, serviceProviderId, ObjectType.SERVICEPEOPLE);
    }

    @PostMapping("/remove-address")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public R removeAddress(@ApiParam(value = "收货地址ID集合", required = true) @NotBlank(message = "请选择要删除的收货地址") @RequestParam(required = false) String ids, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(addressService.removeByIds(Func.toLongList(ids)));
    }

}
