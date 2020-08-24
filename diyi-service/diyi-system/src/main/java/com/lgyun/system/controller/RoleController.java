package com.lgyun.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.system.service.IRoleService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.vo.GrantRequest;
import com.lgyun.system.vo.RoleVO;
import com.lgyun.system.wrapper.RoleWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.ctrl.BladeController;
import com.lgyun.common.node.INode;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.system.entity.Role;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 角色 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色", tags = "角色")
public class RoleController extends BladeController {

	private IRoleService roleService;
	private IUserClient userClient;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入role")
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "列表", notes = "传入role")
	public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> role, BladeUser bladeUser) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list((!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Role::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<RoleVO>> tree(String tenantId, BladeUser bladeUser) {
		List<RoleVO> tree = roleService.tree(Func.toStr(tenantId, bladeUser.getTenantId()));
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public R submit(@Valid @RequestBody Role role, BladeUser user) {
		if (Func.isEmpty(role.getId())) {
			role.setTenantId(user.getTenantId());
		}
		//获取当前创客
		R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(user);
		if (!(result.isSuccess())) {
			return R.fail("当前登录用户失效");
		}
		return R.status(roleService.saveOrUpdate(role));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(roleService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 设置菜单权限
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperation(value = "权限设置", notes = "传入menuId集合")
	public R grant(@RequestBody GrantRequest request, BladeUser user) {
		//获取当前创客
		R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(user);
		if (!(result.isSuccess())) {
			return R.fail("当前登录用户失效");
		}
		EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
		if (!enterpriseWorkerEntity.getId().equals(request.getAccountId())) {
			return R.fail("请求参数非法");
		}
		boolean temp = roleService.grant(Arrays.asList(request.getAccountId()), request.getMenuIds());
		return R.status(temp);
	}

}
