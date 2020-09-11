package com.lgyun.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.node.TreeNode;
import com.lgyun.system.entity.Menu;
import com.lgyun.system.service.IMenuService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.lgyun.common.annotation.PreAuth;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.RoleConstant;
import com.lgyun.common.ctrl.BladeController;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.support.Kv;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.system.vo.MenuVO;
import com.lgyun.system.wrapper.MenuWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Menu 控制器
 *
 * @author Chill
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "菜单", tags = "菜单")
public class MenuController extends BladeController {

	private IMenuService menuService;
	private IUserClient userClient;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperation(value = "详情", notes = "传入menu")
	public R<MenuVO> detail(Menu menu) {
		Menu detail = menuService.getOne(Condition.getQueryWrapper(menu));
		return R.data(MenuWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string")
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperation(value = "列表", notes = "传入menu")
	public R<List<MenuVO>> list(@ApiIgnore @RequestParam Map<String, Object> menu) {
		@SuppressWarnings("unchecked")
		List<Menu> list = menuService.list(Condition.getQueryWrapper(menu, Menu.class).lambda().orderByAsc(Menu::getSort));
		return R.data(MenuWrapper.build().listNodeVO(list));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperation(value = "新增或修改", notes = "传入menu")
	public R submit(@Valid @RequestBody Menu menu) {
		return R.status(menuService.saveOrUpdate(menu));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(menuService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 前端菜单数据
	 */
	@GetMapping("/routes")
	@ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
	public R<List<MenuVO>> routes(BladeUser user) {
		log.info("[routes]user info = {}", JSONObject.toJSONString(user));
		//查询当前创客
		R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(user);
		if (!(result.isSuccess())) {
			return R.fail("当前登录用户失效");
		}
		EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

		List<MenuVO> list = menuService.routes(enterpriseWorkerEntity.getId().toString());
		return R.data(list);
	}

	/**
	 * 前端按钮数据
	 */
	@GetMapping("/buttons")
	@ApiOperation(value = "前端按钮数据", notes = "前端按钮数据")
	public R<List<MenuVO>> buttons(BladeUser user) {
		List<MenuVO> list = menuService.buttons(user.getRoleId());
		return R.data(list);
	}

	/**
	 * 查询菜单树形结构
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<TreeNode>> tree(BladeUser user) {
		//查询当前创客
		R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(user);
		if (!(result.isSuccess())) {
			return R.fail("当前登录用户失效");
		}
		List<TreeNode> tree = menuService.tree(UserType.ENTERPRISE.getValue());
		return R.data(tree);
	}

	/**
	 * 查询权限分配树形结构
	 */
	@GetMapping("/grant-tree")
	@ApiOperation(value = "权限分配树形结构", notes = "权限分配树形结构")
	public R<List<MenuVO>> grantTree(BladeUser user) {
		return R.data(menuService.grantTree(user));
	}

	/**
	 * 查询权限分配树形结构
	 */
	@GetMapping("/role-tree-keys")
	@ApiOperation(value = "角色所分配的树", notes = "角色所分配的树")
	public R<List<String>> roleTreeKeys(String roleIds) {
		return R.data(menuService.roleTreeKeys(roleIds));
	}

	/**
	 * 查询配置的角色权限
	 */
	@GetMapping("auth-routes")
	@ApiOperation(value = "菜单的角色权限")
	public R<List<Kv>> authRoutes(BladeUser user) {
		return R.data(menuService.authRoutes(user));
	}

}
