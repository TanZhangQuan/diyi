package com.lgyun.system.user.controller.enterprise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.BeanServiceUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
import com.lgyun.system.user.vo.enterprise.EnterpriseAccountRequest;
import com.lgyun.system.user.vo.enterprise.EnterpriseContactRequest;
import com.lgyun.system.vo.GrantRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private ISysClient sysClient;
    private IUserService userService;

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

    @PostMapping("/save")
    @ApiOperation(value = "新增、更新(编辑)商户主子账号", notes = "新增、更新(编辑)商户主子账号")
    public R updateEnterpriseWorker(@RequestBody EnterpriseWorkerVO request, BladeUser bladeUser) {
        log.info("新增、更新(编辑)商户主子账号 userId={}", bladeUser.getUserId());
        try {
            //获取当前创客
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }

            if (request.getId() != null) {
                // 更新账号
                EnterpriseWorkerEntity entity = enterpriseWorkerService.getById(request.getId());
                if (entity == null) {
                    return R.fail("没有此账号");
                }

                BeanUtils.copyProperties(request, entity, BeanServiceUtil.getNullPropertyNames(request));
                if (request.getMenuNameList() != null && request.getMenuNameList().size() > 0) {
                    String collect = request.getMenuNameList().stream().collect(Collectors.joining(", "));
                    entity.setMenus(collect);
                }
                if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                    entity.setEmployeePwd(DigestUtil.encrypt(request.getEmployeePwd()));
                }
                enterpriseWorkerService.updateById(entity);

            } else {
                // 新增账号
                EnterpriseWorkerEntity workerServiceByPhoneNumber = enterpriseWorkerService.findByPhoneNumber(request.getPhoneNumber());
                if (workerServiceByPhoneNumber != null) {
                    return R.fail("该手机号已经注册过");
                }

                UserInfo userInfo = userService.userInfoByPhone(request.getPhoneNumber(), UserType.ENTERPRISE);
                if (userInfo != null) {
                    return R.fail("该手机号已经注册过");
                }
                //新建管理员
                User user = new User();
                user.setUserType(UserType.ENTERPRISE);
                user.setAccount(request.getPhoneNumber());
                user.setPassword(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
                user.setPhone(request.getPhoneNumber());
                user.setName(request.getWorkerName());
                userService.save(user);

                EnterpriseWorkerEntity entity = new EnterpriseWorkerEntity();
                BeanUtils.copyProperties(request, entity, BeanServiceUtil.getNullPropertyNames(request));
                if (request.getMenuNameList() != null && request.getMenuNameList().size() > 0) {
                    String collect = request.getMenuNameList().stream().collect(Collectors.joining(", "));
                    entity.setMenus(collect);
                }
                entity.setUserId(user.getId());
                if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                    entity.setEmployeePwd(DigestUtil.encrypt(request.getEmployeePwd()));
                }

                enterpriseWorkerService.save(entity);
                request.setId(entity.getId());
            }
            if (request.getMenuIds() != null && request.getMenuIds().size() > 0) {
                GrantRequest grantRequest = new GrantRequest();
                grantRequest.setAccountId(request.getId());
                grantRequest.setMenuIds(request.getMenuIds());

                R grant = sysClient.grant(grantRequest);
                if (!grant.isSuccess()) {
                    return R.fail("新增、更新商户账号失败");
                }
            }

            return R.success("操作成功");
        } catch (Exception e) {
            log.error("新增、更新商户账号 error=", e);
        }
        return R.fail("新增、更新商户账号失败");
    }

}
