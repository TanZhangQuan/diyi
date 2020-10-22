package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/enterprise/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户端---个独管理模块相关接口", tags = "商户端---个独管理模块相关接口")
public class IndividualEnterpriseEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private IIndividualEnterpriseService individualEnterpriseService;

    @GetMapping("/query-individual-enterprise-list")
    @ApiOperation(value = "查询当前商户的所有关联创客的所有个独", notes = "查询当前商户的所有关联创客的所有个独")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个独编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个独名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getByDtoEnterprise(IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualEnterpriseService.getIndividualEnterpriseList(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), null, individualBusinessEnterpriseDto);
    }

    @PostMapping("/create-individual-enterprise")
    @ApiOperation(value = "创建个独", notes = "创建个独")
    public R createIndividualEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseWebAddDTO individualBusinessEnterpriseWebAddDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualEnterpriseService.createIndividualEnterprise(individualBusinessEnterpriseWebAddDto, enterpriseWorkerEntity.getEnterpriseId());
    }

}
