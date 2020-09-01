package com.lgyun.system.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.entity.Dept;
import com.lgyun.system.entity.Role;
import com.lgyun.system.service.IDeptService;
import com.lgyun.system.service.IPostService;
import com.lgyun.system.service.IRoleService;
import com.lgyun.system.vo.GrantRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;

/**
 * 系统服务Feign实现类
 *
 * @author liangfeihu
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class SysClient implements ISysClient {

	private IDeptService deptService;

	private IPostService postService;

	private IRoleService roleService;

	@Override
	@GetMapping(API_PREFIX + "/getDept")
	public Dept getDept(Long id) {
		return deptService.getById(id);
	}

	@Override
	@GetMapping(API_PREFIX + "/getDeptName")
	public String getDeptName(Long id) {
		return deptService.getById(id).getDeptName();
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		return deptService.getDeptIds(tenantId, deptNames);
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return deptService.getDeptNames(deptIds);
	}

	@Override
	public String getPostIds(String tenantId, String postNames) {
		return postService.getPostIds(tenantId, postNames);
	}

	@Override
	public List<String> getPostNames(String postIds) {
		return postService.getPostNames(postIds);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRole")
	public Role getRole(Long id) {
		return roleService.getById(id);
	}

	@Override
	public String getRoleIds(String tenantId, String roleNames) {
		return roleService.getRoleIds(tenantId, roleNames);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRoleName")
	public String getRoleName(Long id) {
		return roleService.getById(id).getRoleName();
	}

	@Override
	public List<String> getRoleNames(String roleIds) {
		return roleService.getRoleNames(roleIds);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRoleAlias")
	public String getRoleAlias(Long id) {
		return roleService.getById(id).getRoleAlias();
	}

	/**
	 * 授权接口
	 *
	 * @param request
	 * @return
	 */
	@Override
	@PostMapping(API_PREFIX + "/grant")
	public R grantFeign(GrantRequest request) {
		boolean temp = roleService.grantFeign(request);
		return R.status(temp);
	}

}
