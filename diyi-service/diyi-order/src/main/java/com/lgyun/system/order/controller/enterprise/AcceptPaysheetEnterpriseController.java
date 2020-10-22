package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPayListDTO;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDTO;
import com.lgyun.system.order.dto.PayEnterpriseDTO;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商户端---交付支付验收单管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/enterprise/accept-paysheet")
@Validated
@AllArgsConstructor
@Api(value = "商户端---交付支付验收单管理模块相关接口", tags = "商户端---交付支付验收单管理模块相关接口")
public class AcceptPaysheetEnterpriseController {

    private IUserClient iUserClient;
    private IAcceptPaysheetService acceptPaysheetService;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/query-accept-paysheet-list")
    @ApiOperation(value = "查询当前商户所有总包交付支付验收单", notes = "查询当前商户所有总包交付支付验收单")
    public R queryAcceptPaysheetList(AcceptPayListDTO acceptPayListDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return acceptPaysheetService.getAcceptPaySheetsByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), acceptPayListDto, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/upload-accept-paysheet")
    @ApiOperation(value = "上传总包交付支付验收单", notes = "上传总包交付支付验收单")
    public R uploadAcceptPaysheet(@Valid @RequestBody AcceptPaysheetSaveDTO acceptPaysheetSaveDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return acceptPaysheetService.upload(acceptPaysheetSaveDto, enterpriseWorkerEntity.getEnterpriseId(), "商户上传", enterpriseWorkerEntity.getWorkerName());
    }

    @GetMapping("/query-pay-enterprise-list")
    @ApiOperation(value = "查询当前商户所有总包支付清单", notes = "查询当前商户所有总包支付清单")
    public R queryPayEnterpriseList(PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterpriseList(enterpriseWorkerEntity.getEnterpriseId(), null, payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-maker-list-by-pay-enterprise-id")
    @ApiOperation(value = "根据支付清单查询支付清单关联工单的创客", notes = "根据支付清单查询支付清单关联工单的创客")
    public R queryMakerListByPayEnterpriseId(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getMakers(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/remove-accept-paysheet")
    @ApiOperation(value = "删除总包交付支付验收单", notes = "删除总包交付支付验收单")
    public R removeAcceptPaysheet(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(acceptPaysheetService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/query-maker-list-by-accept-paysheet-id")
    @ApiOperation(value = "根据总包交付支付验收单查询关联创客", notes = "根据总包交付支付验收单查询关联创客")
    public R queryMakerListByAcceptPaysheetId(@ApiParam(value = "总包交付支付验收单") @NotNull(message = "请选择总包交付支付验收单") @RequestParam(required = false) Long acceptPaysheetId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return acceptPaysheetService.getMakerList(acceptPaysheetId, Condition.getPage(query.setDescs("create_time")));
    }

}
