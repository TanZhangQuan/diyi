package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.CreateCrowdsourcingInvoiceDTO;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
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
@RequestMapping("/service-provider/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---自助开票管理模块相关接口", tags = "服务商端---自助开票管理模块相关接口")
public class SelfHelpInvoiceServiceProviderController {

    private IUserClient userClient;
    private ISelfHelpInvoiceService selfHelpInvoiceService;


    @PostMapping("/create-crowdsourcing-invoice")
    @ApiOperation(value = "服务商众包开票", notes = "服务商众包开票")
    public R createCrowdsourcingInvoice(@Valid @RequestBody CreateCrowdsourcingInvoiceDTO createCrowdsourcingInvoiceDTO, BladeUser bladeUser){
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.createCrowdsourcingInvoice(createCrowdsourcingInvoiceDTO);
    }

    @GetMapping("/query-self-invoice-list")
    @ApiOperation(value = "服务商查询自助开票", notes = "服务商查询自助开票")
    public R querySelfInvoiceList(@ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请选择开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                  @RequestParam(required = false)String startTiem,@RequestParam(required = false)String endTime,Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        return selfHelpInvoiceService.queryServiceProviderSelfInvoiceList(serviceProviderWorkerEntity.getServiceProviderId(), makerType ,startTiem,endTime, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-self-invoice-details")
    @ApiOperation(value = "服务商查询自助开票详情", notes = "服务商查询自助开票详情")
    public R querySelfInvoiceDetails(@ApiParam(value = "自助开票id", required = true) @NotNull(message = "请输入自助开票id") @RequestParam(required = false) Long selfHelpInvoiceId,
                                     BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return selfHelpInvoiceService.querySelfInvoiceDetails(selfHelpInvoiceId);
    }
}
