package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.wrapper.AdminWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台端---首页管理模块相关接口
 *
 * @author jun.
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
//@RequestMapping("/admin/home-page")
@Validated
@AllArgsConstructor
@Api(value = "平台端---首页管理模块相关接口", tags = "平台端---首页管理模块相关接口")
public class HomePageAdminController {

    private IAdminService adminService;

    @GetMapping("/admin/current-detail")
    @ApiOperation(value = "查询当前管理员详情", notes = "查询当前管理员详情")
    public R currentDetail(BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return R.data(AdminWrapper.build().entityVO(adminEntity));
    }

}
