package com.lgyun.system.user.controller.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.ServiceProviderBankCardDto;
import com.lgyun.system.user.dto.ServiceProviderContactPersonDto;
import com.lgyun.system.user.dto.ServiceProviderInvoiceDto;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.wrapper.ServiceProviderWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
@RestController
@RequestMapping("/serviceprovider")
@Validated
@AllArgsConstructor
@Api(value = "服务商相关接口", tags = "服务商相关接口")
public class ServiceProviderController {

    private IServiceProviderService serviceProviderService;
    private IServiceProviderWorkerService serviceProviderWorkerService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody ServiceProviderEntity serviceProvider) {
        return R.status(serviceProviderService.save(serviceProvider));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody ServiceProviderEntity serviceProvider) {
        return R.status(serviceProviderService.updateById(serviceProvider));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R detail(ServiceProviderEntity serviceProvider) {
        ServiceProviderEntity detail = serviceProviderService.getOne(Condition.getQueryWrapper(serviceProvider));
        return R.data(ServiceProviderWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(ServiceProviderEntity serviceProvider, Query query) {
        IPage<ServiceProviderEntity> pages = serviceProviderService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(serviceProvider));
        return R.data(ServiceProviderWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(serviceProviderService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/get_bank_card")
    @ApiOperation(value = "查询当前服务商银行卡信息", notes = "查询当前服务商银行卡信息")
    public R getBankCard(BladeUser bladeUser) {
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
    public R addOrUpdateBankCard(@Valid @RequestBody ServiceProviderBankCardDto serviceProviderBankCardDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.addOrUpdateBankCard(serviceProviderBankCardDto, serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/get_contact_person")
    @ApiOperation(value = "查询当前服务商联系人信息", notes = "查询当前服务商联系人信息")
    public R getContactPerson(BladeUser bladeUser) {
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
    public R addOrUpdateContactPerson(@Valid @RequestBody ServiceProviderContactPersonDto serviceProviderContactPersonDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.addOrUpdateContactPerson(serviceProviderContactPersonDto, serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/get_invoice")
    @ApiOperation(value = "查询当前服务商开票信息", notes = "查询当前服务商开票信息")
    public R getInvoice(BladeUser bladeUser) {
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
    public R addOrUpdateInvoice(@Valid @RequestBody ServiceProviderInvoiceDto serviceProviderInvoiceDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderService.addOrUpdateInvoice(serviceProviderInvoiceDto, serviceProviderWorkerEntity.getServiceProviderId());
    }

}
