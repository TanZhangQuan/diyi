package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 收件地址表控制器
 *
 * @author liangfeihu
 * @since 2020-08-31 14:48:05
 */
@Slf4j
@RestController
@RequestMapping("/address")
@Validated
@AllArgsConstructor
@Api(value = "收件地址表相关接口", tags = "收件地址表相关接口")
public class AddressController {

    private IAddressService addressService;
    private IUserClient iUserClient;

    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "当前服务商新建或修改收货地址", notes = "当前服务商新建或修改收货地址")
    public R addOrUpdate(@Valid @RequestBody AddressDto addressDto, BladeUser bladeUser) {

        log.info("新建或修改收货地址");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return addressService.addOrUpdateAddress(addressDto, serviceProviderWorkerEntity.getServiceProviderId(), ObjectType.SERVICEPEOPLE);
        } catch (Exception e) {
            log.error("当前服务商新建或修改收货地址异常", e);
        }

        return R.fail("操作失败");
    }

    @GetMapping("/list_by_service_provider")
    @ApiOperation(value = "查询当前服务商所有地址", notes = "查询当前服务商所有地址")
    public R list(Long addressId, Query query, BladeUser bladeUser) {

        log.info("查询当前服务商所有地址");
        try {
            //查询当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return addressService.findAddressMakerId(query.getCurrent(), query.getCurrent(), serviceProviderWorkerEntity.getServiceProviderId(), ObjectType.SERVICEPEOPLE, addressId);
        } catch (Exception e) {
            log.error("查询当前服务商所有地址异常", e);
        }

        return R.fail("查询失败");
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {

        log.info("删除收货地址");
        try {
            return R.status(addressService.removeByIds(Func.toLongList(ids)));
        } catch (Exception e) {
            log.error("删除收货地址异常", e);
        }

        return R.fail("删除失败");
    }

}
