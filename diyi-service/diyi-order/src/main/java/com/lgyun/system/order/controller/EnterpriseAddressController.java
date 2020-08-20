package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 商户收货地址 接口
 *
 * @author liangfeihu
 * @since 2020/8/20 17:42
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/enterprise/address")
@AllArgsConstructor
@Api(value = "商户收货地址接口", tags = "商户收货地址接口")
public class EnterpriseAddressController {

    private IAddressService addressService;
    private IUserClient iUserClient;

    @GetMapping("/list")
    @ApiOperation(value = "获取商户所有地址信息", notes = "获取商户所有地址信息")
    public R getEnterpriseAddressListA(BladeUser bladeUser) {
        log.info("获取商户所有地址信息");
        try {
            //获取当前商户信息
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity entity = result.getData();
            QueryWrapper<AddressEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(AddressEntity::getObjectId, entity.getEnterpriseId()).eq(AddressEntity::getObjectType, ObjectType.ENTERPRISEPEOPLE);

            List<AddressEntity> list = addressService.list();
            return R.data(list);
        } catch (Exception e) {
            log.error("获取商户所有地址信息失败 error", e);
        }
        return R.fail("获取商户所有地址信息失败");
    }

    @PostMapping("/saveAddress")
    @ApiOperation(value = "新建收货地址", notes = "新建收货地址")
    public R saveAddress(@Valid @RequestBody AddressDto addressDto, BladeUser bladeUser) {
        log.info("新建收货地址");
        try {
            //获取当前商户信息
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity entity = result.getData();

            return addressService.saveAddress(addressDto, entity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE);
        } catch (Exception e) {
            log.error("新建收货地址失败", e);
        }
        return R.fail("新建收货地址失败");
    }

}
