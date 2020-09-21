package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.wrapper.AdminWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 平台管理员信息表控制器
 *
 * @author liangfeihu
 * @since 2020-09-19 15:02:12
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@Validated
@AllArgsConstructor
@Api(value = "平台管理员信息表相关接口", tags = "平台管理员信息表相关接口")
public class AdminController {

	private IAdminService adminService;

	@GetMapping("/current-detail")
	@ApiOperation(value = "查询当前管理员详情", notes = "查询当前管理员详情")
	public R currentDetail(BladeUser bladeUser) {

		log.info("查询当前管理员详情");
		try {
			//查询当前管理员
			R<AdminEntity> result = adminService.currentAdmin(bladeUser);
			if (!(result.isSuccess())){
				return result;
			}
			AdminEntity adminEntity = result.getData();

			return R.data(AdminWrapper.build().entityVO(adminEntity));
		} catch (Exception e) {
			log.error("查询当前管理员详情异常", e);
		}
		return R.fail("查询失败");
	}

	@PostMapping("/update-password")
	@ApiOperation(value = "修改密码", notes = "修改密码")
	public R updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {

		log.info("修改密码");
		try {
			return adminService.updatePassword(updatePasswordDto);
		} catch (Exception e) {
			log.error("修改密码异常", e);
		}
		return R.fail("修改失败");
	}

}
