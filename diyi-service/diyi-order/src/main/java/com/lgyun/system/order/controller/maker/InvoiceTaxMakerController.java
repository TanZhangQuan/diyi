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

@RestController
@RequestMapping("/maker/invoice-tax")
@Validated
@AllArgsConstructor
@Api(value = "创客端---发票和完税证明管理模块相关接口", tags = "创客端---发票和完税证明管理模块相关接口")
public class InvoiceTaxMakerController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient iUserClient;

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "根据创客查询所有商户", notes = "根据创客查询所有商户")
    public R queryEnterpriseList(Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payEnterpriseService.getEnterpriseAll(makerEntity.getId(), Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-invoice-list")
    @ApiOperation(value = "根据创客和商户查询创客在商户下所开的票", notes = "根据创客和商户查询创客在商户下所开的票")
    public R queryInvoiceList(Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payEnterpriseService.getEnterpriseMakerIdAll(makerEntity.getId(), enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-invoice-detail")
    @ApiOperation(value = "根据创客,商户和创客支付查询票的详情", notes = "根据创客,商户和创客支付查询票的详情")
    public R queryInvoiceDetail(Long enterpriseId, Long payMakerId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payEnterpriseService.getEnterpriseMakerIdDetail(makerEntity.getId(), enterpriseId, payMakerId);
    }
}
