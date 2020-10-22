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
@RequestMapping("/maker/accept-paysheet")
@Validated
@AllArgsConstructor
@Api(value = "创客端---交付支付验收单管理模块相关接口", tags = "创客端---交付支付验收单管理模块相关接口")
public class AcceptPaysheetMakerController {

    private IMakerService makerService;
    private IMakerEnterpriseService makerEnterpriseService;

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询创客所有总包交付支付验收单的商户", notes = "查询创客所有总包交付支付验收单的商户")
    public R queryEnterpriseList(Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerEnterpriseService.getEnterprisesByWorksheet(Condition.getPage(query.setDescs("create_time")), makerEntity.getId());
    }

}
