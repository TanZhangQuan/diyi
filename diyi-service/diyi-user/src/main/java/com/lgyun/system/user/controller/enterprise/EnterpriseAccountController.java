package com.lgyun.system.user.controller.enterprise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
import com.lgyun.system.user.vo.enterprise.EnterpriseAccountRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商户主子账号相关接口 控制器
 *
 * @author liangfeihu
 * @since 2020/8/24 11:37
 */
@Slf4j
@RestController
@RequestMapping("/enterprise/account")
@AllArgsConstructor
@Api(value = "商户主子账号相关接口", tags = "商户主子账号相关接")
public class EnterpriseAccountController {

    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/list")
    @ApiOperation(value = "获取商户所有主子账号详情", notes = "获取商户所有主子账号详情")
    public R listDetail(BladeUser bladeUser) {
        log.info("获取当前商户所有主子账号详情 userId={}", bladeUser.getUserId());
        try {
            //获取当前创客
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
        } catch (Exception e) {
            log.error("获取当前商户所有账户详情，error", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取商户账号详情", notes = "获取商户账号详情")
    public R oneDetail(@RequestParam("accountId") Long accountId, BladeUser bladeUser) {
        log.info("获取当前商户账号详情 userId={} accountId={}", bladeUser.getUserId(), accountId);
        try {
            //获取当前创客
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

            return R.data(response);
        } catch (Exception e) {
            log.error("获取商户账户详情失败，error", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/operate")
    @ApiOperation(value = "删除、停用 商户主子账号", notes = "删除、停用 商户主子账号")
    public R operateEnterpriseWorker(@RequestBody EnterpriseAccountRequest request, BladeUser bladeUser) {
        log.info("删除、停用 商户主子账号 userId={}", bladeUser.getUserId());
        try {
            //获取当前创客
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }

            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            if (!enterpriseWorkerEntity.getId().equals(request.getAccountId())) {
                return R.fail("请求参数非法");
            }

            EnterpriseWorkerEntity entity = enterpriseWorkerService.getById(request.getAccountId());
            if (entity == null) {
                return R.fail("没有此账号");
            }
            if (EnterpriseAccountRequest.ACCOUNT_DEL.equals(request.getOperationCode())) {
                entity.setIsDeleted(1);
            } else if (EnterpriseAccountRequest.ACCOUNT_STOP.equals(request.getOperationCode())) {
                entity.setStatus(0);
            }
            entity.setEnterpriseWorkerState(AccountState.FREEZE);
            enterpriseWorkerService.save(entity);

            return R.success("操作成功");
        } catch (Exception e) {
            log.error("操作商户账号 error=", e);
        }
        return R.fail("操作商户账号失败");
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新(编辑)商户主子账号", notes = "更新(编辑)商户主子账号")
    public R updateEnterpriseWorker(@RequestBody EnterpriseWorkerVO request, BladeUser bladeUser) {
        log.info("更新(编辑)商户主子账号 userId={}", bladeUser.getUserId());
        try {
            //获取当前创客
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity entity = enterpriseWorkerService.getById(request.getId());
            if (entity == null) {
                return R.fail("没有此账号");
            }

            BeanUtils.copyProperties(request, entity);
            if (request.getMenuNameList() != null && request.getMenuNameList().size() > 0) {
                String collect = request.getMenuNameList().stream().collect(Collectors.joining(", "));
                entity.setMenus(collect);
            }

            enterpriseWorkerService.save(entity);

            return R.success("操作成功");
        } catch (Exception e) {
            log.error("更新商户账号 error=", e);
        }
        return R.fail("更新商户账号失败");
    }

}
