package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.ContactsInfoDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enterprise/basic-info")
@Validated
@AllArgsConstructor
@Api(value = "商户端---商户基本信息管理模块相关接口", tags = "商户端---商户基本信息管理模块相关接口")
public class BasicInfoEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-info")
    @ApiOperation(value = "查询商户基本信息", notes = "查询商户基本信息")
    public R queryEnterpriseInfo(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseService.queryEnterpriseInfo(enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/update-enterprise-info")
    @ApiOperation(value = "修改商户基本信息", notes = "修改商户基本信息")
    public R updateBasicInfo(@ApiParam("企业网址") @RequestParam(required = false) String enterpriseUrl, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseService.updateEnterpriseUrl(enterpriseWorkerEntity.getEnterpriseId(), enterpriseUrl);
    }

    @GetMapping("/query-contact")
    @ApiOperation(value = "查询当前商户所有联系人", notes = "查询当前商户所有联系人")
    public R queryContact(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return enterpriseService.queryContact(enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/update-contact")
    @ApiOperation(value = "修改当前商户联系人", notes = "修改当前商户联系人")
    public R updateContacts(BladeUser bladeUser, @RequestBody ContactsInfoDTO contactsInfoDTO) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return enterpriseService.updateContacts(enterpriseWorkerEntity.getEnterpriseId(),contactsInfoDTO);
    }

    @GetMapping("/query-invoice")
    @ApiOperation(value = "查询当前商户的开票信息", notes = "查询当前商户的开票信息")
    public R queryeInvoice(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return enterpriseService.queryeInvoice(enterpriseWorkerEntity.getEnterpriseId());
    }

}
