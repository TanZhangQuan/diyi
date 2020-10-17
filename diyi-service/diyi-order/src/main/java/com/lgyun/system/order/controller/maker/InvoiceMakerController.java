package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发票和完税证明接口
 *
 * @author tzq
 * @date 2020/7/22.
 * @time 16:24.
 */
@RestController
@RequestMapping("/invoice")
@Validated
@AllArgsConstructor
@Api(value = "发票和完税证明接口", tags = "发票和完税证明接口")
public class InvoiceMakerController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient iUserClient;

    @GetMapping("/getEnterpriseAll")
    @ApiOperation(value = "根据创客id查询所有商户", notes = "根据创客id查询所有商户")
    public R getEnterpriseAll(Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payEnterpriseService.getEnterpriseAll(makerEntity.getId(), Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/getEnterpriseMakerIdAll")
    @ApiOperation(value = "根据创客id和商户id查询创客在商户下所开的票", notes = "根据创客id和商户id查询创客在商户下所开的票")
    public R getEnterpriseMakerIdAll(Query query, BladeUser bladeUser, Long enterpriseId) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();
        return payEnterpriseService.getEnterpriseMakerIdAll(makerEntity.getId(), enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/getEnterpriseMakerIdDetail")
    @ApiOperation(value = "根据创客id,商户id和创客支付id查询票的详情", notes = "根据创客id,商户id和创客支付id查询票的详情")
    public R getEnterpriseMakerIdDetail(BladeUser bladeUser, Long enterpriseId, Long payMakerId) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payEnterpriseService.getEnterpriseMakerIdDetail(makerEntity.getId(), enterpriseId, payMakerId);
    }
}
