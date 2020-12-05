package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ApplyScope;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddOrUpdateEnterpriseProviderInvoiceCatalogDTO;
import com.lgyun.system.order.service.IEnterpriseProviderInvoiceCatalogsService;
import com.lgyun.system.order.service.IServiceProviderInvoiceCatalogsService;
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
@RequestMapping("/service-provider/cooperation-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---合作商户管理模块相关接口", tags = "服务商端---合作商户管理模块相关接口")
public class CooperationEnterpriseServiceProviderController {

    private IUserClient userClient;
    private IServiceProviderInvoiceCatalogsService serviceProviderInvoiceCatalogsService;
    private IEnterpriseProviderInvoiceCatalogsService enterpriseProviderInvoiceCatalogsService;

    @GetMapping("/query-service-provider-invoice-catalogs")
    @ApiOperation(value = "查询服务商开票类目", notes = "查询服务商开票类目")
    public R queryServiceProviderInvoiceCatalogs(@ApiParam(value = "开票类目应用范围") @NotNull(message = "请选择开票类目应用范围") @RequestParam(required = false) ApplyScope applyScope, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderInvoiceCatalogsService.queryProviderInvoiceCatalogNameList(serviceProviderWorkerEntity.getServiceProviderId(), applyScope);
    }

    @GetMapping("/query-enterprise-provider-invoice-catalog-list")
    @ApiOperation(value = "查询商户-服务商所有开票类目", notes = "查询商户-服务商所有开票类目")
    public R queryEnterpriseProviderInvoiceCatalogList(@ApiParam(value = "商户") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return enterpriseProviderInvoiceCatalogsService.queryEnterpriseProviderInvoiceCatalogList(serviceProviderWorkerEntity.getServiceProviderId(), enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-enterprise-provider-invoice-catalog-update-detail")
    @ApiOperation(value = "查询编辑商户-服务商开票类目详情", notes = "查询编辑商户-服务商开票类目详情")
    public R queryEnterpriseProviderInvoiceCatalogUpdateDetail(@ApiParam(value = "商户-服务商开票类目") @NotNull(message = "请选择商户-服务商开票类目") @RequestParam(required = false) Long enterpriseProviderInvoiceCatalogId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseProviderInvoiceCatalogsService.queryEnterpriseProviderInvoiceCatalogUpdateDetail(enterpriseProviderInvoiceCatalogId);
    }

    @PostMapping("/add-or-update-enterprise-provider-invoice-catalog")
    @ApiOperation(value = "添加/编辑商户-服务商开票类目", notes = "添加/编辑商户-服务商开票类目")
    public R addOrUpdateInvoiceCatalog(@ApiParam(value = "商户") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                       @Valid @RequestBody AddOrUpdateEnterpriseProviderInvoiceCatalogDTO addOrUpdateEnterpriseProviderInvoiceCatalogDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return enterpriseProviderInvoiceCatalogsService.addOrUpdateEnterpriseProviderInvoiceCatalog(addOrUpdateEnterpriseProviderInvoiceCatalogDTO, serviceProviderWorkerEntity.getServiceProviderId(), enterpriseId);
    }

    @PostMapping("/delete-enterprise-provider-invoice-catalog")
    @ApiOperation(value = "删除商户-服务商开票类目", notes = "删除商户-服务商开票类目")
    public R deleteEnterpriseProviderInvoiceCatalog(@ApiParam(value = "开票类目", required = true) @NotNull(message = "请选择要删除的开票类目") @RequestParam(required = false) Long enterpriseProviderInvoiceCatalogId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(enterpriseProviderInvoiceCatalogsService.removeById(enterpriseProviderInvoiceCatalogId));
    }

}
