package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maker/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "创客端---自助开票管理模块相关接口", tags = "创客端---自助开票管理模块相关接口")
public class SelfHelpInvoiceMakerController {

    private IMakerService makerService;
    private IMakerEnterpriseService makerEnterpriseService;

    @GetMapping("/query-enterprise-by-maker-id")
    @ApiOperation(value = "根据创客查询商户", notes = "根据创客查询商户")
    public R queryEnterpriseByMakerId(Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerEnterpriseService.findEnterpriseIdNameByMakerId(makerEntity.getId(), Condition.getPage(query.setDescs("create_time")));
    }

}
