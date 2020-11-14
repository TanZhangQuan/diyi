package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ApplyScope;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddOrUpdateAddressDTO;
import com.lgyun.system.order.dto.AddOrUpdateEnterpriseProviderInvoiceCatalogDTO;
import com.lgyun.system.order.dto.AddOrUpdateProviderInvoiceCatalogDTO;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.IEnterpriseProviderInvoiceCatalogsService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IServiceProviderInvoiceCatalogsService;
import com.lgyun.system.user.entity.AdminEntity;
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
@RequestMapping("/admin/service-provider")
@Validated
@AllArgsConstructor
@Api(value = "平台端---服务商管理模块相关接口", tags = "平台端---服务商管理模块相关接口")
public class ServiceProviderAdminController {

    private IUserClient userClient;
    private IAddressService addressService;
    private IPayEnterpriseService payEnterpriseService;
    private IServiceProviderInvoiceCatalogsService serviceProviderInvoiceCatalogsService;
    private IEnterpriseProviderInvoiceCatalogsService enterpriseProviderInvoiceCatalogsService;

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询服务商所有收货地址信息", notes = "查询服务商所有收货地址信息")
    public R queryAddressList(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.queryAddressList(ObjectType.SERVICEPEOPLE, serviceProviderId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-address-update-detail")
    @ApiOperation(value = "查询编辑收货地址详情", notes = "查询编辑收货地址详情")
    public R queryAddressUpdateDetail(@ApiParam(value = "收货地址") @NotNull(message = "请选择收货地址") @RequestParam(required = false) Long addressId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.queryAddressUpdateDetail(addressId);
    }

    @PostMapping("/add-or-update-address")
    @ApiOperation(value = "添加/编辑收货地址", notes = "添加/编辑收货地址")
    public R addOrUpdateAddress(@ApiParam(value = "服务商编号") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                @Valid @RequestBody AddOrUpdateAddressDTO addOrUpdateAddressDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.addOrUpdateAddress(addOrUpdateAddressDto, serviceProviderId, ObjectType.SERVICEPEOPLE);
    }

    @PostMapping("/delete-address")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public R deleteAddress(@ApiParam(value = "收货地址", required = true) @NotNull(message = "请选择要删除的收货地址") @RequestParam(required = false) Long addressId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return addressService.deleteAddress(addressId);
    }

    @GetMapping("/query-invoice-catalog-list")
    @ApiOperation(value = "查询服务商所有开票类目", notes = "查询服务商所有开票类目")
    public R queryInvoiceCatalogList(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderInvoiceCatalogsService.queryInvoiceCatalogList(serviceProviderId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-invoice-catalog-update-detail")
    @ApiOperation(value = "查询编辑服务商开票类目详情", notes = "查询编辑服务商开票类目详情")
    public R queryInvoiceCatalogUpdateDetail(@ApiParam(value = "服务商开票类目") @NotNull(message = "请选择服务商开票类目") @RequestParam(required = false) Long invoiceCatalogId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderInvoiceCatalogsService.queryInvoiceCatalogUpdateDetail(invoiceCatalogId);
    }

    @PostMapping("/add-or-update-invoice-catalog")
    @ApiOperation(value = "添加/编辑服务商开票类目", notes = "添加/编辑服务商开票类目")
    public R addOrUpdateInvoiceCatalog(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                       @Valid @RequestBody AddOrUpdateProviderInvoiceCatalogDTO addOrUpdateProviderInvoiceCatalogDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return serviceProviderInvoiceCatalogsService.addOrUpdateInvoiceCatalog(addOrUpdateProviderInvoiceCatalogDTO, serviceProviderId, adminEntity.getName());
    }

    @PostMapping("/delete-invoice-catalog")
    @ApiOperation(value = "删除服务商开票类目", notes = "删除服务商开票类目")
    public R deleteInvoiceCatalog(@ApiParam(value = "服务商开票类目", required = true) @NotNull(message = "请选择要删除的服务商开票类目") @RequestParam(required = false) Long invoiceCatalogId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(serviceProviderInvoiceCatalogsService.removeById(invoiceCatalogId));
    }

    @GetMapping("/query-enterprise-transaction")
    @ApiOperation(value = "查询服务商交易数据", notes = "查询服务商交易数据")
    public R queryEnterpriseTransaction(@ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.transactionByServiceProvider(serviceProviderId);
    }

    @GetMapping("/query-service-provider-invoice-catalogs")
    @ApiOperation(value = "查询服务商开票类目", notes = "查询服务商开票类目")
    public R queryServiceProviderInvoiceCatalogs(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                                 @ApiParam(value = "服务商开票类目应用范围") @NotNull(message = "请选择服务商开票类目应用范围") @RequestParam(required = false) ApplyScope applyScope, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderInvoiceCatalogsService.queryProviderInvoiceCatalogNameList(serviceProviderId, applyScope);
    }

    @GetMapping("/query-enterprise-provider-invoice-catalog-list")
    @ApiOperation(value = "查询商户-服务商所有开票类目", notes = "查询商户-服务商所有开票类目")
    public R queryEnterpriseProviderInvoiceCatalogList(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                                       @ApiParam(value = "商户") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseProviderInvoiceCatalogsService.queryEnterpriseProviderInvoiceCatalogList(serviceProviderId, enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-enterprise-provider-invoice-catalog-update-detail")
    @ApiOperation(value = "查询编辑商户-服务商开票类目详情", notes = "查询编辑商户-服务商开票类目详情")
    public R queryEnterpriseProviderInvoiceCatalogUpdateDetail(@ApiParam(value = "商户-服务商开票类目") @NotNull(message = "请选择商户-服务商开票类目") @RequestParam(required = false) Long enterpriseProviderInvoiceCatalogId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseProviderInvoiceCatalogsService.queryEnterpriseProviderInvoiceCatalogUpdateDetail(enterpriseProviderInvoiceCatalogId);
    }

    @PostMapping("/add-or-update-enterprise-provider-invoice-catalog")
    @ApiOperation(value = "添加/编辑商户-服务商开票类目", notes = "添加/编辑商户-服务商开票类目")
    public R addOrUpdateEnterpriseProviderInvoiceCatalog(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                                         @ApiParam(value = "商户") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                                         @Valid @RequestBody AddOrUpdateEnterpriseProviderInvoiceCatalogDTO addOrUpdateEnterpriseProviderInvoiceCatalogDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return enterpriseProviderInvoiceCatalogsService.addOrUpdateEnterpriseProviderInvoiceCatalog(addOrUpdateEnterpriseProviderInvoiceCatalogDTO, serviceProviderId, enterpriseId, adminEntity.getName());
    }

    @PostMapping("/delete-enterprise-provider-invoice-catalog")
    @ApiOperation(value = "删除商户-服务商开票类目", notes = "删除商户-服务商开票类目")
    public R deleteEnterpriseProviderInvoiceCatalog(@ApiParam(value = "开票类目", required = true) @NotNull(message = "请选择要删除的开票类目") @RequestParam(required = false) Long invoiceCatalogId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(enterpriseProviderInvoiceCatalogsService.removeById(invoiceCatalogId));
    }

}
