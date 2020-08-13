package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.wrapper.EnterpriseWorkerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Slf4j
@RestController
@RequestMapping("/web/enterprise_worker")
@Validated
@AllArgsConstructor
@Api(value = "商户员工相关接口(管理端)", tags = "商户员工相关接口(管理端)")
public class EnterpriseWorkerWebController {

    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/current-detail")
    @ApiOperation(value = "获取当前商户员工详情", notes = "获取当前商户员工详情")
    public R currentDetail(BladeUser bladeUser) {

        log.info("获取当前商户员工详情");
        try {
            //获取当前创客
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return R.data(EnterpriseWorkerWrapper.build().entityVO(enterpriseWorkerEntity));
        } catch (Exception e) {
            log.error("获取当前商户员工详情异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/update-password")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public R updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {

        log.info("修改密码");
        try {
            return enterpriseWorkerService.updatePassword(updatePasswordDto);
        } catch (Exception e) {
            log.error("修改密码异常", e);
        }
        return R.fail("修改失败");
    }

}
