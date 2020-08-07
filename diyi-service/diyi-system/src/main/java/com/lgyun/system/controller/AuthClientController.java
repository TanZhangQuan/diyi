package com.lgyun.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.entity.AuthClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.lgyun.common.annotation.PreAuth;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.RoleConstant;
import com.lgyun.common.ctrl.BladeController;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.service.IAuthClientService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 *  应用管理控制器
 *
 * @author liangfeihu
 * @since 2020/6/6 23:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@ApiIgnore
@Api(value = "应用管理", tags = "接口")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class AuthClientController extends BladeController {

	private IAuthClientService clientService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入client")
	public R<AuthClient> detail(AuthClient authClient) {
		AuthClient detail = clientService.getOne(Condition.getQueryWrapper(authClient));
		return R.data(detail);
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入client")
	public R<IPage<AuthClient>> list(AuthClient authClient, Query query) {
		IPage<AuthClient> pages = clientService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(authClient));
		return R.data(pages);
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入client")
	public R save(@Valid @RequestBody AuthClient authClient) {
		return R.status(clientService.save(authClient));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入client")
	public R update(@Valid @RequestBody AuthClient authClient) {
		return R.status(clientService.updateById(authClient));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入client")
	public R submit(@Valid @RequestBody AuthClient authClient) {
		return R.status(clientService.saveOrUpdate(authClient));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(clientService.deleteLogic(Func.toLongList(ids)));
	}


}
