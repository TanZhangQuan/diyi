package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.ServiceProviderBankCardDTO;
import com.lgyun.system.user.dto.ServiceProviderContactPersonDTO;
import com.lgyun.system.user.dto.ServiceProviderInvoiceDTO;
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
@RequestMapping("/service-provider/")
@Validated
@AllArgsConstructor
@Api(value = "服务商相关接口", tags = "服务商相关接口")
public class ServiceProviderController {

    private IServiceProviderService serviceProviderService;
    private IServiceProviderWorkerService serviceProviderWorkerService;

    @GetMapping("/query-bank-card")
    @ApiOperation(value = "查询当前服务商银行卡信息", notes = "查询当前服务商银行卡信息")
    public R queryBankCard(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.getBankCard(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @PostMapping("/add_or_update_bank_card")
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

    @PostMapping("/add_or_update_contact_person")
    @ApiOperation(value = "新增或修改当前服务商联系人信息", notes = "新增或修改当前服务商联系人信息")
    public R addOrUpdateContactPerson(@Valid @RequestBody ServiceProviderContactPersonDTO serviceProviderContactPersonDto, BladeUser bladeUser) {
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

    @PostMapping("/add_or_update_invoice")
    @ApiOperation(value = "新增或修改当前服务商开票信息", notes = "新增或修改当前服务商开票信息")
    public R addOrUpdateInvoice(@Valid @RequestBody ServiceProviderInvoiceDTO serviceProviderInvoiceDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.addOrUpdateInvoice(serviceProviderInvoiceDto, serviceProviderWorkerEntity.getServiceProviderId());
    }

}
