package com.lgyun.system.user.controller.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.vo.ServiceProviderWorkerVO;
import com.lgyun.system.user.vo.service.ServiceAccountRequest;
import com.lgyun.system.user.wrapper.ServiceProviderWorkerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务商账号 控制器
 *
 * @author liangfeihu
 * @since 2020/9/19 16:25
 */
@Slf4j
@RestController
@RequestMapping("/service/account")
@Validated
@AllArgsConstructor
@Api(value = "服务商账号相关接口(管理端)", tags = "服务商账号相关接口(管理端)")
public class ServiceProviderAccountController {

	private ISysClient sysClient;
	private IServiceProviderWorkerService serviceProviderWorkerService;

	@GetMapping("/list")
	@ApiOperation(value = "查询服务商所有主子账号详情", notes = "查询服务商所有主子账号详情")
	public R listDetail(BladeUser bladeUser) {
		log.info("查询当前服务商所有主子账号详情 userId={}", bladeUser.getUserId());
		try {
			//查询当前创客
			R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
			if (!(result.isSuccess())) {
				return result;
			}
			ServiceProviderWorkerEntity workerEntity = result.getData();

			QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getServiceProviderId, workerEntity.getServiceProviderId());
			List<ServiceProviderWorkerEntity> list = serviceProviderWorkerService.list(queryWrapper);

			List<ServiceProviderWorkerVO> responseList = new ArrayList<>();
			list.stream().forEach(entity -> {
				ServiceProviderWorkerVO response = new ServiceProviderWorkerVO();
				BeanUtils.copyProperties(entity, response);
				response.setWorkerSexDesc(entity.getWorkerSex().getDesc());
				response.setWorkerSexValue(entity.getWorkerSex().getValue());
				response.setAccountStateValue(entity.getServiceProviderWorkerState().getValue());
				response.setAccountStateDesc(entity.getServiceProviderWorkerState().getDesc());
				response.setPositionNameValue(entity.getPositionName().getValue());
				response.setPositionNameDesc(entity.getPositionName().getDesc());

				responseList.add(response);
			});
			return R.data(responseList);
		} catch (Exception e) {
			log.error("查询当前服务商所有账户详情，error", e);
		}
		return R.fail("查询失败");
	}

	@GetMapping("/detail")
	@ApiOperation(value = "查询服务商账号详情", notes = "查询服务商账号详情")
	public R oneDetail(@RequestParam("accountId") Long accountId, BladeUser bladeUser) {
		log.info("查询当前服务商账号详情 userId={} accountId={}", bladeUser.getUserId(), accountId);
		try {
			//查询当前创客
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
			response.setWorkerSexDesc(entity.getWorkerSex().getDesc());
			response.setWorkerSexValue(entity.getWorkerSex().getValue());
			response.setAccountStateValue(entity.getServiceProviderWorkerState().getValue());
			response.setAccountStateDesc(entity.getServiceProviderWorkerState().getDesc());
			response.setPositionNameValue(entity.getPositionName().getValue());
			response.setPositionNameDesc(entity.getPositionName().getDesc());

			List<String> menuIds = sysClient.getMenuIds(entity.getId());
			response.setMenuIds(menuIds);

			return R.data(response);
		} catch (Exception e) {
			log.error("查询商户服务商账号详情失败，error", e);
		}
		return R.fail("查询失败");
	}

	@PostMapping("/operate")
	@ApiOperation(value = "删除、停用 服务商主子账号", notes = "删除、停用 服务商主子账号")
	public R operateEnterpriseWorker(@RequestBody ServiceAccountRequest request, BladeUser bladeUser) {
		log.info("删除、停用 服务商主子账号 userId={}", bladeUser.getUserId());
		try {
			//查询当前创客
			R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
			if (!(result.isSuccess())) {
				return result;
			}
			ServiceProviderWorkerEntity workerEntity = result.getData();

			ServiceProviderWorkerEntity entity = serviceProviderWorkerService.getById(request.getAccountId());
			if (entity == null) {
				return R.fail("没有此账号");
			}

			if (ServiceAccountRequest.ACCOUNT_DEL.equals(request.getOperationCode())) {
				entity.setIsDeleted(1);
			} else if (ServiceAccountRequest.ACCOUNT_STOP.equals(request.getOperationCode())) {
				entity.setStatus(0);
			}

			entity.setUpdateUser(workerEntity.getId());
			entity.setUpdateTime(new Date());
			entity.setServiceProviderWorkerState(AccountState.FREEZE);
			serviceProviderWorkerService.updateById(entity);

			return R.success("操作成功");
		} catch (Exception e) {
			log.error("操作服务商账号 error=", e);
		}
		return R.fail("操作服务商账号失败");
	}

	@PostMapping("/save")
	@ApiOperation(value = "新增、更新(编辑)服务商主子账号", notes = "新增、更新(编辑)服务商主子账号")
	public R updateEnterpriseWorker(@RequestBody ServiceProviderWorkerVO request, BladeUser bladeUser) {
		log.info("新增、更新(编辑)服务商主子账号 userId={}", bladeUser.getUserId());
		try {
			//查询当前创客
			R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
			if (!(result.isSuccess())) {
				return result;
			}
			ServiceProviderWorkerEntity workerEntity = result.getData();

			// 执行新增、更新、授权操作
			R<String> stringR = serviceProviderWorkerService.saveServiceProviderAccount(request, workerEntity, bladeUser);
			return stringR;
		} catch (Exception e) {
			log.error("新增、更新服务商账号 error=", e);
		}
		return R.fail("新增、更新服务商账号失败");
	}

}
