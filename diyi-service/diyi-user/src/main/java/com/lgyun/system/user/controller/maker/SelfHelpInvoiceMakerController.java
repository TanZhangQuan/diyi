package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.ServiceProviderListDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.service.IServiceProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maker/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "创客端---自助开票管理模块相关接口", tags = "创客端---自助开票管理模块相关接口")
public class SelfHelpInvoiceMakerController {

    private IMakerService makerService;
    private IServiceProviderService serviceProviderService;

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderList(ServiceProviderListDTO serviceProviderListDTO, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return serviceProviderService.queryServiceProviderList(null, null, serviceProviderListDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

}
