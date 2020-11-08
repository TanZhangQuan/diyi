package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddOrUpdateDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.dto.MakerListIndividualDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/enterprise/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "商户端---个体户管理模块相关接口", tags = "商户端---个体户管理模块相关接口")
public class IndividualBusinessEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private IIndividualBusinessService individualBusinessService;
    private IEnterpriseReportService enterpriseReportService;
    private IMakerService makerService;

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询当前商户关联的创客", notes = "查询当前商户关联的创客")
    public R queryMakerList(MakerListIndividualDTO makerListIndividualDTO, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerService.queryMakerListIndividual(enterpriseWorkerEntity.getEnterpriseId(), makerListIndividualDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/add-or-update-individual-business")
    @ApiOperation(value = "添加/编辑个体户", notes = "添加/编辑个体户")
    public R addOrUpdateIndividualBusiness(@Valid @RequestBody IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualBusinessService.addOrUpdateIndividualBusiness(individualBusinessEnterpriseAddOrUpdateDto, enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query-individual-business-list")
    @ApiOperation(value = "查询当前商户的关联创客的所有个体户", notes = "查询当前商户的所有关联创客的所有个体户")
    public R queryIndividualBusinessList(IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualBusinessService.queryIndividualBusinessList(enterpriseWorkerEntity.getEnterpriseId(), null, individualBusinessEnterpriseListDto, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-individual-business-detail")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R queryIndividualBusinessDetail(@ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.queryIndividualBusinessDetail(individualBusinessId);
    }

    @GetMapping("/query-update-individual-business-detail")
    @ApiOperation(value = "查询编辑个体户详情", notes = "查询编辑个体户详情")
    public R queryUpdateIndividualBusinessDetail(@ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.queryUpdateIndividualBusinessDetail(individualBusinessId);
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个体户年审信息", notes = "查询个体户年审信息")
    public R queryEnterpriseReportList(@ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findByBodyTypeAndBodyId(BodyType.INDIVIDUALBUSINESS, individualBusinessId, query);
    }

}
