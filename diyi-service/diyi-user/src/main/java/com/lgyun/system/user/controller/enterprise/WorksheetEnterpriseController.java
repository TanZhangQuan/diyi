package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户端---工单管理模块相关接口
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@RestController
@RequestMapping("/enterprise/worksheet")
@Validated
@AllArgsConstructor
@Api(value = "商户端---工单管理模块相关接口", tags = "商户端---工单管理模块相关接口")
public class WorksheetEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private IMakerService makerService;

    @GetMapping("query-maker-list")
    @ApiOperation(value = "查询创客", notes = "查询创客")
    public R queryMakerList(@RequestParam(required = false) String makerName, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.getMakerName(query, makerName);
    }

}
