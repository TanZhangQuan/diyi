package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.order.dto.AddressDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailInvoiceTaxDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailsByServiceProviderDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceExpressDTO;
import com.lgyun.system.order.service.*;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 商户自助开票相关接口
 *
 * @author tzq
 * @date 2020/7/30.
 * @time 17:37.
 */
@RestController
@RequestMapping("/order/webSelfhelpinvoice")
@Validated
@AllArgsConstructor
@Api(value = "自助开票相关接口(管理端)", tags = "自助开票相关接口(管理端)")
public class SelfHelpInvoiceServiceProviderController {

    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private IAddressService addressService;
    private IDictClient iDictClient;
    private IUserClient iUserClient;
    private ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;
    private ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;

    @GetMapping("/get-self-helf-invoices-by-service-provider")
    @ApiOperation(value = "查询当前服务商所有自助开票记录", notes = "查询当前服务商所有自助开票记录")
    public R getSelfHelfInvoicesByServiceProvider(@ApiParam(value = "创客类型") @NotNull(message = "请选择创客类型") @RequestParam(required = false) InvoicePeopleType invoicePeopleType,
                                                  @ApiParam(value = "自助开票-服务商状态") @NotNull(message = "请选择自助开票-服务商状态") @RequestParam(required = false) SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState,
                                                  SelfHelpInvoiceDetailsByServiceProviderDTO selfHelpInvoiceDetailsByServiceProviderDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.getSelfHelfInvoicesByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId(), invoicePeopleType, selfHelpInvoiceSpApplyState, selfHelpInvoiceDetailsByServiceProviderDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/get-single-self-helf-invoice-by-service-provider")
    @ApiOperation(value = "查询当前服务商某条自助开票记录详情", notes = "查询当前服务商某条自助开票记录详情")
    public R getSingleSelfHelfInvoiceByServiceProvider(@ApiParam(value = "自助开票ID") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.getSingleSelfHelfInvoiceByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId(), selfHelpInvoiceId);
    }

    @GetMapping("/get-self-helf-invoice-details-by-self-helf-invoice-and-service-provider")
    @ApiOperation(value = "查询当前服务商某条自助开票记录的所有自助开票明细", notes = "查询当前服务商某条自助开票记录的所有自助开票明细")
    public R getSelfHelfInvoiceDetailsBySelfHelfInvoiceAndServiceProvider(@ApiParam(value = "自助开票ID") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, Query query) {
        return selfHelpInvoiceService.getSelfHelfInvoiceDetailListBySelfHelfInvoice(selfHelpInvoiceId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/upload_invoice_tax_by_provider")
    @ApiOperation(value = "服务商自助开票上传发票税票", notes = "服务商自助开票上传发票税票")
    public R uploadInvoiceTaxByProvider(@Valid @RequestBody SelfHelpInvoiceDetailInvoiceTaxDTO selfHelpInvoiceDetailInvoiceTaxDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.uploadInvoiceTaxByProvider(serviceProviderWorkerEntity, selfHelpInvoiceDetailInvoiceTaxDto);
    }

    @PostMapping("/fill_express_by_provider")
    @ApiOperation(value = "服务商自助开票填写快递信息", notes = "服务商自助开票填写快递信息")
    public R fillExpressByProvider(@Valid @RequestBody SelfHelpInvoiceExpressDTO selfHelpInvoiceExpressDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.fillExpressByProvider(serviceProviderWorkerEntity, selfHelpInvoiceExpressDto);
    }

    @GetMapping("/get-self-helf-invoice-express-by-self-helf-invoice-and-provider")
    @ApiOperation(value = "查询当前服务商某条自助开票记录的快递信息", notes = "查询当前服务商某条自助开票记录的快递信息")
    public R getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(@ApiParam(value = "自助开票ID") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, BladeUser bladeUser) {

        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(serviceProviderWorkerEntity.getServiceProviderId(), selfHelpInvoiceId);
    }

    @PostMapping("/saveAddress")
    @ApiOperation(value = "新建收货地址", notes = "新建收货地址")
    public R saveAddress(@Valid @RequestBody AddressDTO addressDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return addressService.addOrUpdateAddress(addressDto, enterpriseWorkerEntity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE);
    }

    @GetMapping("/getAddressById")
    @ApiOperation(value = "地址详情接口", notes = "地址详情接口")
    public R getAddressById(Long addressId) {
        return addressService.getAddressById(addressId);
    }

    @PostMapping("/updateAddress")
    @ApiOperation(value = "地址编辑接口", notes = "地址编辑接口")
    public R updateAddress(@Valid @RequestBody AddressDTO addressDto) {
        return addressService.updateAddress(addressDto);
    }

    @PostMapping("/deleteAddress")
    @ApiOperation(value = "地址删除接口", notes = "地址删除接口")
    public R deleteAddress(Long addressId) {
        return addressService.deleteAddress(addressId);
    }

    @GetMapping("/findAddressMakerId")
    @ApiOperation(value = "查询收货地址", notes = "查询收货地址")
    public R findAddressMakerId(Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return addressService.findAddressMakerId(query.getCurrent(), query.getCurrent(), enterpriseWorkerEntity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE, null);
    }

    @GetMapping("/getInvoiceType")
    @ApiOperation(value = "开票类目", notes = "开票类目")
    public R getInvoiceType() {
        return iDictClient.getList("tax_category");
    }

    @PostMapping("/uploadDeliverSheetUrl")
    @ApiOperation(value = "上传交付支付验收单URL", notes = "上传交付支付验收单URL")
    public R uploadDeliverSheetUrl(@NotNull(message = "请输入自助开票明细编号") @RequestParam(required = false) Long selfHelpInvoiceDetailId,
                                   @NotBlank(message = "请上传交付支付验收单") @RequestParam(required = false) String deliverSheetUrl) {

        return selfHelpInvoiceDetailService.uploadDeliverSheetUrl(selfHelpInvoiceDetailId, deliverSheetUrl);
    }

    @PostMapping("/audit")
    @ApiOperation(value = "自助开票审核", notes = "自助开票审核")
    public R audit(@ApiParam(value = "自助开票编号") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, @ApiParam(value = "自助开票审核状态") @NotNull(message = "请选择自助开票审核状态") @RequestParam(required = false) SelfHelpInvoiceSpApplyState applyState, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.audit(serviceProviderWorkerEntity.getId(), selfHelpInvoiceId, applyState);
    }

}
