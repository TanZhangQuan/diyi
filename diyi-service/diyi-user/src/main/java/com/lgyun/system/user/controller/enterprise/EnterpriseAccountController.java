package com.lgyun.system.user.controller.enterprise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
import com.lgyun.system.user.vo.enterprise.EnterpriseAccountRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商户主子账号相关接口 控制器
 *
 * @author tzq
 * @since 2020/8/24 11:37
 */
@RestController
@RequestMapping("/enterprise/account")
@AllArgsConstructor
@Api(value = "商户主子账号相关接口", tags = "商户主子账号相关接")
public class EnterpriseAccountController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private ISysClient sysClient;

    @GetMapping("/list")
    @ApiOperation(value = "查询商户所有主子账号详情", notes = "查询商户所有主子账号详情")
    public R listDetail(BladeUser bladeUser) {
        //查询当前创客
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getEnterpriseId, enterpriseWorkerEntity.getEnterpriseId());
        List<EnterpriseWorkerEntity> list = enterpriseWorkerService.list(queryWrapper);

        List<EnterpriseWorkerVO> responseList = new ArrayList<>();
        list.stream().forEach(entity -> {
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

    @GetMapping("/detail")
    @ApiOperation(value = "查询商户账号详情", notes = "查询商户账号详情")
    public R oneDetail(@RequestParam("accountId") Long accountId, BladeUser bladeUser) {
        //查询当前创客
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

    @PostMapping("/operate")
    @ApiOperation(value = "删除、停用 商户主子账号", notes = "删除、停用 商户主子账号")
    public R operateEnterpriseWorker(@RequestBody EnterpriseAccountRequest request, BladeUser bladeUser) {
        //查询当前创客
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        EnterpriseWorkerEntity entity = enterpriseWorkerService.getById(request.getAccountId());
        if (entity == null) {
            return R.fail("没有此账号");
        }

        if (EnterpriseAccountRequest.ACCOUNT_DEL.equals(request.getOperationCode())) {
            entity.setIsDeleted(1);
        } else if (EnterpriseAccountRequest.ACCOUNT_STOP.equals(request.getOperationCode())) {
            entity.setStatus(0);
        }

        entity.setUpdateUser(enterpriseWorkerEntity.getId());
        entity.setUpdateTime(new Date());
        entity.setEnterpriseWorkerState(AccountState.FREEZE);
        enterpriseWorkerService.updateById(entity);

        return R.success("操作成功");
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增、更新(编辑)商户主子账号", notes = "新增、更新(编辑)商户主子账号")
    public R updateEnterpriseWorker(@RequestBody EnterpriseWorkerVO request, BladeUser bladeUser) {
        //查询当前创客
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        // 执行新增、更新、授权操作
        R<String> stringR = enterpriseWorkerService.saveEnterpriseAccount(request, enterpriseWorkerEntity, bladeUser);

        return stringR;
    }

}
