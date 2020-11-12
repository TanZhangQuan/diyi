package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.ServiceProviderBankCardDTO;
import com.lgyun.system.user.dto.ServiceProviderContactPersonDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/service-provider/basic-info")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---服务商基本信息管理模块相关接口", tags = "服务商端---服务商基本信息管理模块相关接口")
public class BasicInfoServiceProviderController {

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IServiceProviderService serviceProviderService;

    @GetMapping("/query-account-list")
    @ApiOperation(value = "查询当前服务商银行卡信息", notes = "查询当前服务商银行卡信息")
    public R queryAccountList(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.getBankCard(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @PostMapping("/add-or-update-bank-card")
    @ApiOperation(value = "新增或修改当前服务商银行卡信息", notes = "新增或修改当前服务商银行卡信息")
    public R addOrUpdateBankCard(@Valid @RequestBody ServiceProviderBankCardDTO serviceProviderBankCardDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.addOrUpdateBankCard(serviceProviderBankCardDto, serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/query-contact-person")
    @ApiOperation(value = "查询当前服务商联系人信息", notes = "查询当前服务商联系人信息")
    public R queryContactPerson(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.getContactPerson(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @PostMapping("/update-contact-person")
    @ApiOperation(value = "修改当前服务商联系人信息", notes = "修改当前服务商联系人信息")
    public R updateContactPerson(@Valid @RequestBody ServiceProviderContactPersonDTO serviceProviderContactPersonDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.addOrUpdateContactPerson(serviceProviderContactPersonDto, serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/query-invoice")
    @ApiOperation(value = "查询当前服务商开票信息", notes = "查询当前服务商开票信息")
    public R queryInvoice(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.getInvoice(serviceProviderWorkerEntity.getServiceProviderId());
    }

}
