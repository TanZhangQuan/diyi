package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddressDTO;
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

/**
 * 创客端---收件地址表相关接口
 *
 * @author liangfeihu
 * @since 2020-08-31 14:48:05
 */
@RestController
@RequestMapping("/maker/address")
@Validated
@AllArgsConstructor
@Api(value = "创客端---收件地址表相关接口", tags = "创客端---收件地址表相关接口")
public class AddressMakerController {

    private IAddressService addressService;
    private IUserClient iUserClient;

    @PostMapping("/add-or-update-address")
    @ApiOperation(value = "当前服务商新建或修改收货地址", notes = "当前服务商新建或修改收货地址")
    public R addOrUpdateAddress(@Valid @RequestBody AddressDTO addressDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return addressService.addOrUpdateAddress(addressDto, serviceProviderWorkerEntity.getServiceProviderId(), ObjectType.SERVICEPEOPLE);
    }

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询当前服务商所有地址", notes = "查询当前服务商所有地址")
    public R queryAddressList(Long addressId, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return addressService.findAddressMakerId(query.getCurrent(), query.getCurrent(), serviceProviderWorkerEntity.getServiceProviderId(), ObjectType.SERVICEPEOPLE, addressId);
    }

    @PostMapping("/remove-address")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public R removeAddress(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(addressService.removeByIds(Func.toLongList(ids)));
    }

}
