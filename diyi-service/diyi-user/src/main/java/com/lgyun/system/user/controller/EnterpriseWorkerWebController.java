package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.wrapper.EnterpriseWorkerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@RestController
@RequestMapping("/web/enterprise_worker")
@Validated
@AllArgsConstructor
@Api(value = "商户员工相关接口(管理端)", tags = "商户员工相关接口(管理端)")
public class EnterpriseWorkerWebController {

    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/current-detail")
    @ApiOperation(value = "查询当前商户员工详情", notes = "查询当前商户员工详情")
    public R currentDetail(BladeUser bladeUser) {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return R.data(EnterpriseWorkerWrapper.build().entityVO(enterpriseWorkerEntity));
    }

}
