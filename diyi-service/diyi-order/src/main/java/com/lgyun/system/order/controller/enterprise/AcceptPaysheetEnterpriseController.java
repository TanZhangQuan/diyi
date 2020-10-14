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
//@RequestMapping("/enterprise/accept-paysheet")
@Validated
@AllArgsConstructor
@Api(value = "商户端---交付支付验收单管理模块相关接口", tags = "商户端---交付支付验收单管理模块相关接口")
public class AcceptPaysheetEnterpriseController {

    private IUserClient iUserClient;
    private IAcceptPaysheetService acceptPaysheetService;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/web/accept_pay_sheet/get_accept_pay_sheet_by_enterprise")
    @ApiOperation(value = "查询当前商户所有总包交付支付验收单", notes = "查询当前商户所有总包交付支付验收单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acceptPayId", value = "交付支付验收单ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "makerName", value = "创客名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getAcceptPaySheetsByEnterprise(AcceptPayListDTO acceptPayListDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return acceptPaysheetService.getAcceptPaySheetsByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), acceptPayListDto, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/web/accept_pay_sheet/upload")
    @ApiOperation(value = "上传总包交付支付验收单", notes = "上传总包交付支付验收单")
    public R save(@Valid @RequestBody AcceptPaysheetSaveDTO acceptPaysheetSaveDto, BladeUser bladeUser) {

        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return acceptPaysheetService.upload(acceptPaysheetSaveDto, enterpriseWorkerEntity.getEnterpriseId(), "商户上传", enterpriseWorkerEntity.getWorkerName());
    }

    @GetMapping("/web/accept_pay_sheet/get_pay_enterprises_by_enterprise")
    @ApiOperation(value = "查询当前商户所有总包支付清单", notes = "查询当前商户所有总包支付清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceProviderName", value = "服务商名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getPayEnterprisesByEnterprise(PayEnterpriseDTO payEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.getPayEnterpriseList(enterpriseWorkerEntity.getEnterpriseId(), null, payEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/web/accept_pay_sheet/get_makers")
    @ApiOperation(value = "根据支付清单查询支付清单关联工单的创客", notes = "根据支付清单查询支付清单关联工单的创客")
    public R getMakers(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query) {
        return payEnterpriseService.getMakers(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/web/accept_pay_sheet/remove")
    @ApiOperation(value = "删除总包交付支付验收单", notes = "删除总包交付支付验收单")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(acceptPaysheetService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/web/accept_pay_sheet/get_maker_list")
    @ApiOperation(value = "根据总包交付支付验收单查询关联创客", notes = "根据总包交付支付验收单查询关联创客")
    public R getMakerList(@ApiParam(value = "总包交付支付验收单ID") @NotNull(message = "请输入总包交付支付验收单编号") @RequestParam(required = false) Long acceptPaysheetId, Query query) {
        return acceptPaysheetService.getMakerList(acceptPaysheetId, Condition.getPage(query.setDescs("create_time")));
    }

}
