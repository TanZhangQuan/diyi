package com.lgyun.system.user.controller.enterprise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.dto.OperateEnterpriseWorkerDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
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
@RequestMapping("/enterprise/permission")
@Validated
@AllArgsConstructor
@Api(value = "商户端---权限管理模块相关接口", tags = "商户端---权限管理模块相关接口")
public class PermissionEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private ISysClient sysClient;

    @GetMapping("/query-child-account-list")
    @ApiOperation(value = "查询商户所有主子账号详情", notes = "查询商户所有主子账号详情")
    public R queryChildAccountList(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getEnterpriseId, enterpriseWorkerEntity.getEnterpriseId());
        List<EnterpriseWorkerEntity> list = enterpriseWorkerService.list(queryWrapper);

        List<EnterpriseWorkerVO> responseList = new ArrayList<>();
        list.forEach(entity -> {
            EnterpriseWorkerVO response = new EnterpriseWorkerVO();
            BeanUtils.copyProperties(entity, response);
            response.setWorkerSexDesc(entity.getWorkerSex().getDesc());
            response.setWorkerSexValue(entity.getWorkerSex().getValue());
            response.setAccountStateValue(entity.getEnterpriseWorkerState().getValue());
            response.setAccountStateDesc(entity.getEnterpriseWorkerState().getDesc());
            response.setPositionNameValue(entity.getPositionName().getValue());
            response.setPositionNameDesc(entity.getPositionName().getDesc());

            responseList.add(response);
        });

        return R.data(responseList);
    }

    @GetMapping("/query-account-detail")
    @ApiOperation(value = "查询商户账号详情", notes = "查询商户账号详情")
    public R queryAccountDetail(@RequestParam("accountId") Long accountId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        EnterpriseWorkerEntity entity = enterpriseWorkerService.getById(accountId);
        if (entity == null) {
            return R.fail("参数有误, 查询失败");
        }

        EnterpriseWorkerVO response = new EnterpriseWorkerVO();
        BeanUtils.copyProperties(entity, response);
        response.setWorkerSexDesc(entity.getWorkerSex().getDesc());
        response.setWorkerSexValue(entity.getWorkerSex().getValue());
        response.setAccountStateValue(entity.getEnterpriseWorkerState().getValue());
        response.setAccountStateDesc(entity.getEnterpriseWorkerState().getDesc());
        response.setPositionNameValue(entity.getPositionName().getValue());
        response.setPositionNameDesc(entity.getPositionName().getDesc());

        List<String> menuIds = sysClient.getMenuIds(entity.getId());
        response.setMenuIds(menuIds);

        return R.data(response);
    }

    @PostMapping("/operate-child-account")
    @ApiOperation(value = "删除、停用 商户主子账号", notes = "删除、停用 商户主子账号")
    public R operateChildAccount(@RequestBody OperateEnterpriseWorkerDTO request, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        EnterpriseWorkerEntity entity = enterpriseWorkerService.getById(request.getAccountId());
        if (entity == null) {
            return R.fail("没有此账号");
        }

        if (OperateEnterpriseWorkerDTO.ACCOUNT_DEL.equals(request.getOperationCode())) {
            entity.setIsDeleted(1);
        } else if (OperateEnterpriseWorkerDTO.ACCOUNT_STOP.equals(request.getOperationCode())) {
            entity.setStatus(0);
        }

        entity.setUpdateUser(enterpriseWorkerEntity.getId());
        entity.setUpdateTime(new Date());
        entity.setEnterpriseWorkerState(AccountState.FREEZE);
        enterpriseWorkerService.updateById(entity);

        return R.success("操作成功");
    }

    @PostMapping("/update-child-account")
    @ApiOperation(value = "新增、更新(编辑)商户主子账号", notes = "新增、更新(编辑)商户主子账号")
    public R updateChildAccount(@RequestBody EnterpriseWorkerVO request, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        // 执行新增、更新、授权操作
        return enterpriseWorkerService.saveEnterpriseAccount(request, enterpriseWorkerEntity, bladeUser);
    }

}
