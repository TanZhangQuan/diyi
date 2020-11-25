package com.lgyun.system.order.controller.agentMain;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddOrUpdateAddressDTO;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.dto.PayEnterpriseListSimpleDTO;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
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
@RequestMapping("/agent-main/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "渠道商端---商户管理模块相关接口", tags = "渠道商端---商户管理模块相关接口")
public class EnterpriseAgentMainController {

    private IUserClient userClient;
    private IAddressService addressService;
    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询商户所有收货地址信息", notes = "查询商户所有收货地址信息")
    public R queryAddressList(@ApiParam(value = "商户") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.queryAddressList(ObjectType.ENTERPRISEPEOPLE, enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-address-update-detail")
    @ApiOperation(value = "查询编辑收货地址详情", notes = "查询编辑收货地址详情")
    public R queryAddressUpdateDetail(@ApiParam(value = "收货地址") @NotNull(message = "请选择收货地址") @RequestParam(required = false) Long addressId, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.queryAddressUpdateDetail(addressId);
    }

    @PostMapping("/add-or-update-address")
    @ApiOperation(value = "添加/编辑收货地址", notes = "添加/编辑收货地址")
    public R addOrUpdateAddress(@ApiParam(value = "商户编号") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                @Valid @RequestBody AddOrUpdateAddressDTO addOrUpdateAddressDto, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.addOrUpdateAddress(addOrUpdateAddressDto, enterpriseId, ObjectType.ENTERPRISEPEOPLE);
    }

    @PostMapping("/delete-address")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public R deleteAddress(@ApiParam(value = "收货地址", required = true) @NotNull(message = "请选择要删除的收货地址") @RequestParam(required = false) Long addressId, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.deleteAddress(addressId);
    }

    @GetMapping("/query-agent-main-enterprise-transaction")
    @ApiOperation(value = "查询渠道商-商户交易数据", notes = "查询渠道商-商户交易数据")
    public R queryAgentMainEnterpriseTransaction(BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return payEnterpriseService.queryAgentMainEnterpriseTransaction(agentMainWorkerEntity.getId());
    }

    @GetMapping("/query-pay-enterprise-list")
    @ApiOperation(value = "查询商户总包+分包", notes = "查询商户总包+分包")
    public R queryPayEnterpriseList(@ApiParam(value = "商户") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, PayEnterpriseListSimpleDTO payEnterpriseListSimpleDTO, Query query, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseListAgentMain(enterpriseId, null, payEnterpriseListSimpleDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

//    @GetMapping("/query-self-help-invoice-list")
//    @ApiOperation(value = "查询商户众包/众采", notes = "查询商户众包/众采")
//    public R querySelfHelpInvoiceList(@ApiParam(value = "商户") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, PayEnterpriseListSimpleDTO payEnterpriseListSimpleDTO, Query query, BladeUser bladeUser) {
//        //查询当前渠道商员工
//        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.querySelfHelpInvoiceListAgentMain(enterpriseId, null, payEnterpriseListSimpleDTO, Condition.getPage(query.setDescs("t1.create_time")));
//    }

}
