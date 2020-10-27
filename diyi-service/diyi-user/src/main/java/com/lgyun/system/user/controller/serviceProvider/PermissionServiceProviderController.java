package com.lgyun.system.user.controller.serviceProvider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.dto.ServiceAccountDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.vo.ServiceProviderWorkerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/service-provider/permission")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---权限管理模块相关接口", tags = "服务商端---权限管理模块相关接口")
public class PermissionServiceProviderController {

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private ISysClient sysClient;

    @GetMapping("/query-child-account-list")
    @ApiOperation(value = "查询服务商所有主子账号详情", notes = "查询服务商所有主子账号详情")
    public R queryChildAccountList(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity workerEntity = result.getData();

        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getServiceProviderId, workerEntity.getServiceProviderId());
        List<ServiceProviderWorkerEntity> list = serviceProviderWorkerService.list(queryWrapper);

        List<ServiceProviderWorkerVO> responseList = new ArrayList<>();
        list.forEach(entity -> {
            ServiceProviderWorkerVO response = new ServiceProviderWorkerVO();
            BeanUtils.copyProperties(entity, response);
            response.setWorkerSex(entity.getWorkerSex());
            response.setAccountStateValue(entity.getServiceProviderWorkerState().getValue());
            response.setAccountStateDesc(entity.getServiceProviderWorkerState().getDesc());
            response.setPositionNameValue(entity.getPositionName().getValue());
            response.setPositionNameDesc(entity.getPositionName().getDesc());

            responseList.add(response);
        });

        return R.data(responseList);
    }

    @GetMapping("/query-account-detail")
    @ApiOperation(value = "查询服务商账号详情", notes = "查询服务商账号详情")
    public R queryAccountDetail(@RequestParam("accountId") Long accountId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        ServiceProviderWorkerEntity entity = serviceProviderWorkerService.getById(accountId);
        if (entity == null) {
            return R.fail("参数有误, 查询失败");
        }

        ServiceProviderWorkerVO response = new ServiceProviderWorkerVO();
        BeanUtils.copyProperties(entity, response);
        response.setWorkerSex(entity.getWorkerSex());
        response.setAccountStateValue(entity.getServiceProviderWorkerState().getValue());
        response.setAccountStateDesc(entity.getServiceProviderWorkerState().getDesc());
        response.setPositionNameValue(entity.getPositionName().getValue());
        response.setPositionNameDesc(entity.getPositionName().getDesc());

        List<String> menuIds = sysClient.getMenuIds(entity.getId());
        response.setMenuIds(menuIds);

        return R.data(response);
    }

    @PostMapping("/operate-child-account")
    @ApiOperation(value = "删除、停用 服务商主子账号", notes = "删除、停用 服务商主子账号")
    public R operateChildAccount(@RequestBody ServiceAccountDTO request, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity workerEntity = result.getData();

        ServiceProviderWorkerEntity entity = serviceProviderWorkerService.getById(request.getAccountId());
        if (entity == null) {
            return R.fail("没有此账号");
        }

        if (ServiceAccountDTO.ACCOUNT_DEL.equals(request.getOperationCode())) {
            entity.setIsDeleted(1);
        } else if (ServiceAccountDTO.ACCOUNT_STOP.equals(request.getOperationCode())) {
            entity.setStatus(0);
        }

        entity.setUpdateUser(workerEntity.getId());
        entity.setUpdateTime(new Date());
        entity.setServiceProviderWorkerState(AccountState.FREEZE);
        serviceProviderWorkerService.updateById(entity);

        return R.success("操作成功");
    }

    @PostMapping("/update-child-account")
    @ApiOperation(value = "新增、更新(编辑)服务商主子账号", notes = "新增、更新(编辑)服务商主子账号")
    public R updateChildAccount(@RequestBody ServiceProviderWorkerVO request, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity workerEntity = result.getData();

        // 执行新增、更新、授权操作
        return serviceProviderWorkerService.saveServiceProviderAccount(request, workerEntity, bladeUser);
    }

}
