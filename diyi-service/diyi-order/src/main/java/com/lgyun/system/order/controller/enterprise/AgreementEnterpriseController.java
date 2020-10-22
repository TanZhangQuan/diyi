package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户端---合同管理模块相关接口
 *
 * @author .
 * @date 2020/8/15.
 * @time 9:52.
 */
@RestController
@RequestMapping("/enterprise/agreement")
@Validated
@AllArgsConstructor
@Api(value = "商户端---合同管理模块相关接口", tags = "商户端---合同管理模块相关接口")
public class AgreementEnterpriseController {


    private IUserClient userClient;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-ent-mak-sourc")
    @ApiOperation(value = "根据商户查询众包的合同", notes = "根据商户查询众包的合同")
    public R selectEntMakSourc(Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.findEnterpriseCrowdSourcing(enterpriseWorkerEntity.getEnterpriseId(), "", Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-ent-mak-sourc-detail")
    @ApiOperation(value = "根据自助开票查询众包的详情", notes = "根据自助开票查询众包的详情")
    public R selectEntMakSourcDetail(Long selfHelpInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.findDetailCrowdSourcing(selfHelpInvoiceId);
    }

}
