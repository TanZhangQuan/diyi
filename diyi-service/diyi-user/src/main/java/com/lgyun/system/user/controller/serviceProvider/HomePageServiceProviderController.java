package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IRelBureauFileService;
import com.lgyun.system.user.service.IRelBureauNoticeService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/service-provider/home-page")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---首页管理模块相关接口", tags = "服务商端---首页管理模块相关接口")
public class HomePageServiceProviderController {

    private IRelBureauFileService relBureauFileService;
    private IRelBureauNoticeService relBureauNoticeService;
    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IEnterpriseServiceProviderService enterpriseServiceProviderService;

    @GetMapping("/query-current-service-provider-detail")
    @ApiOperation(value = "查询当前服务商员工详情", notes = "查询当前服务商员工详情")
    public R queryCurrentServiceProviderDetail(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderWorkerService.queryServiceProviderWorkerDetail(serviceProviderWorkerEntity.getId());
    }

    @GetMapping("/query-enterprise-id-and-name-list")
    @ApiOperation(value = "查询当前服务商合作商户", notes = "查询当前服务商合作商户")
    public R queryEnterpriseIdAndNameList(Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return enterpriseServiceProviderService.queryCooperationEnterpriseList(serviceProviderWorkerEntity.getServiceProviderId(), null, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-rel-bureau-file-list")
    @ApiOperation(value = "查询相关局监督文件列表", notes = "查询相关局监督文件列表")
    public R queryRelBureauFileList(Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return relBureauFileService.queryRelBureauFileListServiceProvider(serviceProviderWorkerEntity.getServiceProviderId(), serviceProviderWorkerEntity.getId(), query);
    }

//    @PostMapping("/have-read-rel-bureau-file")
//    @ApiOperation(value = "添加或修改服务商收款账户信息", notes = "添加或修改服务商收款账户信息")
//    public R haveReadRelBureauFile(@Valid @RequestBody BladeUser bladeUser) {
//        //查询当前服务商员工
//        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
//
//        return relBureauFileService.haveReadRelBureauFile(serviceProviderWorkerEntity.getServiceProviderId());
//    }

    @GetMapping("/query-rel-bureau-file-detail")
    @ApiOperation(value = "查询相关局监督文件详情", notes = "查询相关局监督文件详情")
    public R queryRelBureauFileDetail(@ApiParam("相关局监督文件") @NotNull(message = "请选择相关局监督文件") @RequestParam(required = false) Long relBureauFileId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauFileService.queryRelBureauFileDetail(relBureauFileId, true);
    }

    @GetMapping("/query-rel-bureau-notice-list")
    @ApiOperation(value = "查询相关局通知列表", notes = "查询相关局通知列表")
    public R queryRelBureauNoticeList(Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return relBureauNoticeService.queryRelBureauNoticeListServiceProvider(serviceProviderWorkerEntity.getServiceProviderId(), serviceProviderWorkerEntity.getId(), query);
    }

    @GetMapping("/query-rel-bureau-notice-detail")
    @ApiOperation(value = "查询相关局通知详情", notes = "查询相关局通知详情")
    public R queryRelBureauNoticeDetail(@ApiParam("相关局通知") @NotNull(message = "请选择相关局通知") @RequestParam(required = false) Long relBureauNoticeId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return relBureauNoticeService.queryRelBureauNoticeDetail(relBureauNoticeId);
    }

}
