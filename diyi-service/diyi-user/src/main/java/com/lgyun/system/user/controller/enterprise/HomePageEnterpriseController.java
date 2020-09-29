package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户端---首页管理模块相关接口
 *
 * @author jun.
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
//@RequestMapping("/enterprise/home-page")
@Validated
@AllArgsConstructor
@Api(value = "商户端---首页管理模块相关接口", tags = "商户端---首页管理模块相关接口")
public class HomePageEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/web/enterprise_worker/current-detail")
    @ApiOperation(value = "查询当前商户员工详情", notes = "查询当前商户员工详情")
    public R currentDetail(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())){
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseWorkerService.queryEnterpriseWorkerDetail(enterpriseWorkerEntity.getId());
    }

}