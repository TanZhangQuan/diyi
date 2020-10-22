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
 * 服务商端---自助开票管理模块相关接口
 *
 * @author tzq
 * @date 2020/7/30.
 * @time 17:37.
 */
@RestController
@RequestMapping("/service-provider/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---自助开票管理模块相关接口", tags = "服务商端---自助开票管理模块相关接口")
public class SelfHelpInvoiceServiceProviderController {

    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private IAddressService addressService;
    private IDictClient iDictClient;
    private IUserClient iUserClient;

    @GetMapping("/query-self-helf-invoice-list")
    @ApiOperation(value = "查询当前服务商所有自助开票记录", notes = "查询当前服务商所有自助开票记录")
    public R querySelfHelfInvoiceList(@ApiParam(value = "创客类型") @NotNull(message = "请选择创客类型") @RequestParam(required = false) InvoicePeopleType invoicePeopleType,
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

    @GetMapping("/query-self-helf-invoice-detail")
    @ApiOperation(value = "查询当前服务商某条自助开票记录详情", notes = "查询当前服务商某条自助开票记录详情")
    public R querySelfHelfInvoiceDetail(@ApiParam(value = "自助开票ID") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.getSingleSelfHelfInvoiceByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId(), selfHelpInvoiceId);
    }

    @GetMapping("/query-self-helf-invoice-detail-list")
    @ApiOperation(value = "查询当前服务商某条自助开票记录的所有自助开票明细", notes = "查询当前服务商某条自助开票记录的所有自助开票明细")
    public R querySelfHelfInvoiceDetailList(@ApiParam(value = "自助开票") @NotNull(message = "请选择自助开票") @RequestParam(required = false) Long selfHelpInvoiceId, Query query) {
        return selfHelpInvoiceService.getSelfHelfInvoiceDetailListBySelfHelfInvoice(selfHelpInvoiceId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/upload-invoice-tax")
    @ApiOperation(value = "上传发票税票", notes = "上传发票税票")
    public R uploadInvoiceTax(@Valid @RequestBody SelfHelpInvoiceDetailInvoiceTaxDTO selfHelpInvoiceDetailInvoiceTaxDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.uploadInvoiceTaxByProvider(serviceProviderWorkerEntity, selfHelpInvoiceDetailInvoiceTaxDto);
    }

    @PostMapping("/fill-express")
    @ApiOperation(value = "填写快递信息", notes = "填写快递信息")
    public R fillExpress(@Valid @RequestBody SelfHelpInvoiceExpressDTO selfHelpInvoiceExpressDto, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.fillExpressByProvider(serviceProviderWorkerEntity, selfHelpInvoiceExpressDto);
    }

    @GetMapping("/query-self-helf-invoice-express")
    @ApiOperation(value = "查询当前服务商某条自助开票记录的快递信息", notes = "查询当前服务商某条自助开票记录的快递信息")
    public R querySelfHelfInvoiceExpress(@ApiParam(value = "自助开票") @NotNull(message = "请选择自助开票") @RequestParam(required = false) Long selfHelpInvoiceId, BladeUser bladeUser) {

        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(serviceProviderWorkerEntity.getServiceProviderId(), selfHelpInvoiceId);
    }

    @PostMapping("/create-address")
    @ApiOperation(value = "新建收货地址", notes = "新建收货地址")
    public R createAddress(@Valid @RequestBody AddressDTO addressDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return addressService.addOrUpdateAddress(addressDto, enterpriseWorkerEntity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE);
    }

    @GetMapping("/query-address-detail")
    @ApiOperation(value = "地址详情接口", notes = "地址详情接口")
    public R queryAddressDetail(@ApiParam(value = "地址") @NotNull(message = "请选择地址") @RequestParam(required = false) Long addressId) {
        return addressService.getAddressById(addressId);
    }

    @PostMapping("/update-address")
    @ApiOperation(value = "地址编辑接口", notes = "地址编辑接口")
    public R updateAddress(@Valid @RequestBody AddressDTO addressDto) {
        return addressService.updateAddress(addressDto);
    }

    @PostMapping("/delete-address")
    @ApiOperation(value = "地址删除接口", notes = "地址删除接口")
    public R deleteAddress(@ApiParam(value = "地址") @NotNull(message = "请选择地址") @RequestParam(required = false) Long addressId) {
        return addressService.deleteAddress(addressId);
    }

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询收货地址", notes = "查询收货地址")
    public R queryAddressList(Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return addressService.findAddressMakerId(enterpriseWorkerEntity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE, null, query);
    }

    @GetMapping("/query-invoice-type")
    @ApiOperation(value = "开票类目", notes = "开票类目")
    public R queryInvoiceType() {
        return iDictClient.getList("tax_category");
    }

    @PostMapping("/upload-deliver-sheet")
    @ApiOperation(value = "上传交付支付验收单", notes = "上传交付支付验收单")
    public R uploadDeliverSheetUrl(@ApiParam(value = "自助开票明细") @NotNull(message = "请选择自助开票明细") @RequestParam(required = false) Long selfHelpInvoiceDetailId,
                                   @ApiParam(value = "交付支付验收单") @NotBlank(message = "请上传交付支付验收单") @RequestParam(required = false) String deliverSheetUrl) {

        return selfHelpInvoiceDetailService.uploadDeliverSheetUrl(selfHelpInvoiceDetailId, deliverSheetUrl);
    }

    @PostMapping("/self-helf-invoice-audit")
    @ApiOperation(value = "自助开票审核", notes = "自助开票审核")
    public R selfHelfInvoiceAudit(@ApiParam(value = "自助开票编号") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, @ApiParam(value = "自助开票审核状态") @NotNull(message = "请选择自助开票审核状态") @RequestParam(required = false) SelfHelpInvoiceSpApplyState applyState, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.audit(serviceProviderWorkerEntity.getId(), selfHelpInvoiceId, applyState);
    }

}
