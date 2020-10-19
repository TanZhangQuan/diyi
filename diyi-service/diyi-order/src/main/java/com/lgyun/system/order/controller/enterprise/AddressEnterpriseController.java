package com.lgyun.system.order.controller.enterprise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.dto.AddressDTO;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 商户端---交付支付验收单管理模块相关接口
 *
 * @author liangfeihu
 * @since 2020/8/20 17:42
 */
@Validated
@RestController
@RequestMapping("/enterprise/address")
@AllArgsConstructor
@Api(value = "商户收货地址接口", tags = "商户收货地址接口")
public class AddressEnterpriseController {

    private IAddressService addressService;
    private IUserClient iUserClient;

    @GetMapping("/list")
    @ApiOperation(value = "查询商户所有地址信息", notes = "查询商户所有地址信息")
    public R getEnterpriseAddressListA(BladeUser bladeUser) {
        //查询当前商户信息
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity entity = result.getData();
        QueryWrapper<AddressEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AddressEntity::getObjectId, entity.getEnterpriseId()).eq(AddressEntity::getObjectType, ObjectType.ENTERPRISEPEOPLE);

        List<AddressEntity> list = addressService.list();
        return R.data(list);
    }

    @PostMapping("/saveAddress")
    @ApiOperation(value = "新建收货地址", notes = "新建收货地址")
    public R saveAddress(@Valid @RequestBody AddressDTO addressDto, BladeUser bladeUser) {
        //查询当前商户信息
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity entity = result.getData();

        return addressService.addOrUpdateAddress(addressDto, entity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE);
    }

}
