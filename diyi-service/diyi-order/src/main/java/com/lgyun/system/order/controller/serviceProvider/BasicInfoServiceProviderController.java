package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddOrUpdateAddressDTO;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
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
@RequestMapping("/service-provider/basic-info")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---服务商基本信息管理模块相关接口", tags = "服务商端---服务商基本信息管理模块相关接口")
public class BasicInfoServiceProviderController {

    private IUserClient userClient;
    private IAddressService addressService;

    @PostMapping("/add-or-update-address")
    @ApiOperation(value = "新建或修改收货地址", notes = "新建或修改收货地址")
    public R addOrUpdateAddress(@Valid @RequestBody AddOrUpdateAddressDTO addOrUpdateAddressDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return addressService.addOrUpdateAddress(addOrUpdateAddressDto, serviceProviderWorkerEntity.getServiceProviderId(), ObjectType.SERVICEPEOPLE);
    }

    @PostMapping("/set-default-address")
    @ApiOperation(value = "设置默认地址", notes = "设置默认地址")
    public R setDefaultAddress(@ApiParam(value = "收货地址") @NotNull(message = "请选择收货地址") @RequestParam(required = false) Long addressId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return addressService.setDefaultAddress(serviceProviderWorkerEntity.getServiceProviderId(), ObjectType.SERVICEPEOPLE, addressId);
    }

    @GetMapping("/query-address-detail")
    @ApiOperation(value = "查询收货地址详情", notes = "查询收货地址详情")
    public R queryAddressDetail(@ApiParam(value = "收货地址") @NotNull(message = "请选择收货地址") @RequestParam(required = false) Long addressId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.getAddressById(addressId);
    }

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询收货地址", notes = "查询收货地址")
    public R queryAddressList(Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return addressService.queryAddressList(ObjectType.SERVICEPEOPLE, serviceProviderWorkerEntity.getServiceProviderId(), Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/remove-address")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public R removeAddress(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(addressService.removeByIds(Func.toLongList(ids)));
    }

}
