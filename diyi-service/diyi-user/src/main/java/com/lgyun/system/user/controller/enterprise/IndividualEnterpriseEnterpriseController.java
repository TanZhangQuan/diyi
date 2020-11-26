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
import com.lgyun.system.user.service.IIndividualEnterpriseService;
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
@RequestMapping("/enterprise/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户端---个独管理模块相关接口", tags = "商户端---个独管理模块相关接口")
public class IndividualEnterpriseEnterpriseController {

    private IMakerService makerService;
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IEnterpriseReportService enterpriseReportService;
    private IIndividualEnterpriseService individualEnterpriseService;

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询当前商户关联的创客", notes = "查询当前商户关联的创客")
    public R queryMakerList(MakerListIndividualDTO makerListIndividualDTO, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerService.queryMakerListIndividual(enterpriseWorkerEntity.getEnterpriseId(), null, makerListIndividualDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/add-or-update-individual-enterprise")
    @ApiOperation(value = "添加/编辑个独", notes = "添加/编辑个独")
    public R addOrUpdateIndividualEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualEnterpriseService.addOrUpdateIndividualEnterprise(individualBusinessEnterpriseAddOrUpdateDto, enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query-individual-enterprise-list")
    @ApiOperation(value = "查询当前商户的所有关联创客的所有个独", notes = "查询当前商户的所有关联创客的所有个独")
    public R queryIndividualEnterpriseList(IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualEnterpriseService.queryIndividualEnterpriseList(Condition.getPage(query.setDescs("t1.create_time")), enterpriseWorkerEntity.getEnterpriseId(), null, individualBusinessEnterpriseListDto);
    }

    @GetMapping("/query-individual-business-detail")
    @ApiOperation(value = "查询个独详情", notes = "查询个独详情")
    public R queryIndividualBusinessDetail(@ApiParam(value = "个独") @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.queryIndividualEnterpriseDetail(individualEnterpriseId);
    }

    @GetMapping("/query-update-individual-business-detail")
    @ApiOperation(value = "查询编辑个独详情", notes = "查询编辑个独详情")
    public R queryUpdateIndividualBusinessDetail(@ApiParam(value = "个独") @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.queryUpdateIndividualEnterpriseDetail(individualEnterpriseId);
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个独年审信息", notes = "查询个独年审信息")
    public R queryEnterpriseReportList(@ApiParam(value = "个独", required = true) @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findByBodyTypeAndBodyId(BodyType.INDIVIDUALENTERPRISE, individualEnterpriseId, query);
    }

}
