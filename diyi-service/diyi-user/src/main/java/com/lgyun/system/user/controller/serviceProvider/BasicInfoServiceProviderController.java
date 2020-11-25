package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddOrUpdateServiceProviderAccountDTO;
import com.lgyun.system.user.dto.ContactsInfoDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IServiceProviderAccountService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
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

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IServiceProviderService serviceProviderService;
    private IServiceProviderAccountService serviceProviderAccountService;

    @GetMapping("/query-service-provider-account-list")
    @ApiOperation(value = "查询服务商收款账户信息", notes = "查询服务商收款账户信息")
    public R queryServiceProviderAccountList(Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderAccountService.queryServiceProviderAccountList(serviceProviderWorkerEntity.getServiceProviderId(), Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/add-or-update-service-provider-account")
    @ApiOperation(value = "添加或修改服务商收款账户信息", notes = "添加或修改服务商收款账户信息")
    public R addOrUpdateServiceProviderAccount(@Valid @RequestBody AddOrUpdateServiceProviderAccountDTO addOrUpdateServiceProviderAccountDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderAccountService.addOrUpdateServiceProviderAccount(serviceProviderWorkerEntity.getServiceProviderId(), addOrUpdateServiceProviderAccountDTO);
    }

    @GetMapping("/query-service-provider-account-update-detail")
    @ApiOperation(value = "查询编辑服务商收款账户信息", notes = "查询编辑服务商收款账户信息")
    public R queryServiceProviderAccountUpdateDetail(@ApiParam(value = "服务商收款账户信息") @NotNull(message = "请选择服务商收款账户信息") @RequestParam(required = false) Long serviceProviderAccounttId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderAccountService.queryServiceProviderAccountUpdateDetail(serviceProviderAccounttId);
    }

    @PostMapping("/remove-service-provider-account")
    @ApiOperation(value = "删除服务商收款账户信息", notes = "删除服务商收款账户信息")
    public R removeServiceProviderAccountList(@ApiParam(value = "服务商收款账户信息", required = true) @NotNull(message = "请选择要删除的服务商收款账户信息") @RequestParam(required = false) Long serviceProviderAccounttId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(serviceProviderAccountService.removeById(serviceProviderAccounttId));
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
    public R updateContactPerson(@Valid @RequestBody ContactsInfoDTO contactsInfoDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.updateContactPerson(serviceProviderWorkerEntity.getServiceProviderId(), contactsInfoDTO);
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

        return serviceProviderService.queryeInvoice(serviceProviderWorkerEntity.getServiceProviderId());
    }

}
