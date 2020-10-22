package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddressDTO;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/enterprise/address")
@AllArgsConstructor
@Api(value = "商户收货地址接口", tags = "商户收货地址接口")
public class AddressEnterpriseController {

    private IUserClient iUserClient;
    private IAddressService addressService;

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询商户所有地址信息", notes = "查询商户所有地址信息")
    public R queryAddressList(Query query, BladeUser bladeUser) {
        //查询当前商户信息
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return addressService.findAddressMakerId(enterpriseWorkerEntity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE, null, query);
    }

    @PostMapping("/create-address")
    @ApiOperation(value = "新建收货地址", notes = "新建收货地址")
    public R createAddress(@Valid @RequestBody AddressDTO addressDto, BladeUser bladeUser) {
        //查询当前商户信息
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity entity = result.getData();

        return addressService.addOrUpdateAddress(addressDto, entity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE);
    }

}
