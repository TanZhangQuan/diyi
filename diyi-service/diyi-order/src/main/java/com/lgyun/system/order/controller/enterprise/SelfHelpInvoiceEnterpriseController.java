package com.lgyun.system.order.controller.enterprise;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddOrUpdateAddressDTO;
import com.lgyun.system.order.dto.ConfirmPaymentDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceDTO;
import com.lgyun.system.order.dto.SelfHelpInvoicesByEnterpriseDTO;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.excel.InvoiceListListener;
import com.lgyun.system.order.service.*;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/enterprise/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "商户端---自助开票管理模块相关接口", tags = "商户端---自助开票管理模块相关接口")
public class SelfHelpInvoiceEnterpriseController {

    private IUserClient userClient;
    private IAddressService addressService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;
    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;
    private ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;


    @GetMapping("/query-self-helf-invoices-list")
    @ApiOperation(value = "查询当前商户所有自助开票记录", notes = "查询当前商户所有自助开票记录")
    public R querySelfHelfInvoicesList(@ApiParam(value = "创客类型", required = true) @NotNull(message = "请选择创客类型") @RequestParam(required = false) MakerType makerType, SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.getSelfHelfInvoicesByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), makerType, selfHelpInvoicesByEnterpriseDto, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-self-helf-invoices-detail")
    @ApiOperation(value = "查询当前商户某条自助开票记录详情", notes = "查询当前商户某条自助开票记录详情")
    public R querySelfHelfInvoicesDetail(@ApiParam(value = "自助开票", required = true) @NotNull(message = "请选择自助开票") @RequestParam(required = false) Long selfHelpInvoiceId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.getSingleSelfHelfInvoiceByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), selfHelpInvoiceId);
    }

    @GetMapping("/query-self-helf-invoices-detail-list")
    @ApiOperation(value = "查询当前商户某条自助开票记录的所有自助开票明细", notes = "查询当前商户某条自助开票记录的所有自助开票明细")
    public R querySelfHelfInvoicesDetailList(@ApiParam(value = "自助开票", required = true) @NotNull(message = "请输入自助开票") @RequestParam(required = false) Long selfHelpInvoiceId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.getSelfHelfInvoiceDetailListBySelfHelfInvoice(selfHelpInvoiceId, Condition.getPage(query.setDescs("t2.create_time")));
    }

    @GetMapping("/query-self-helf-invoice-express-list")
    @ApiOperation(value = "查询当前商户某条自助开票记录的所有快递信息", notes = "查询当前商户某条自助开票记录的所有快递信息")
    public R querySelfHelfInvoiceExpressList(@ApiParam(value = "自助开票", required = true) @NotNull(message = "请选择自助开票") @RequestParam(required = false) Long selfHelpInvoiceId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.getSelfHelfInvoiceExpressBySelfHelfInvoiceAndEnterprise(enterpriseWorkerEntity.getEnterpriseId(), selfHelpInvoiceId, Condition.getPage(query.setDescs("t3.create_time")));
    }

    @PostMapping("/create-address")
    @ApiOperation(value = "新建或修改收件地址", notes = "新建或修改收件地址")
    public R createAddress(@Valid @RequestBody AddOrUpdateAddressDTO addOrUpdateAddressDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return addressService.addOrUpdateAddress(addOrUpdateAddressDto, enterpriseWorkerEntity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE);
    }

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询收件地址", notes = "查询收件地址")
    public R queryAddressList(Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return addressService.queryAddressList(ObjectType.ENTERPRISEPEOPLE, enterpriseWorkerEntity.getEnterpriseId(), Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/upload-deliver-sheet")
    @ApiOperation(value = "上传交付支付验收单", notes = "上传交付支付验收单")
    public R uploadDeliverSheet(@NotNull(message = "请选择自助开票明细") @RequestParam(required = false) Long selfHelpInvoiceDetailId,
                                @NotBlank(message = "请上传交付支付验收单") @RequestParam(required = false) String deliverSheetUrl, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceDetailService.uploadDeliverSheetUrl(selfHelpInvoiceDetailId, deliverSheetUrl);
    }

    @PostMapping("/submit-self-help-invoice")
    @ApiOperation(value = "当前商户提交自助开票", notes = "商户提交自助开票")
    public R submitSelfHelpInvoice(@ApiParam(value = "文件") @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file,
                                   @Valid @RequestBody SelfHelpInvoiceDTO selfHelpInvoiceDto, BladeUser bladeUser) throws IOException {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        //判断文件内容是否为空
        if (file == null || file.isEmpty()) {
            return R.fail("Excel文件为空");
        }

        //判断文件内容是否为空
        selfHelpInvoiceDto.setObjectType(ObjectType.ENTERPRISEPEOPLE);
        selfHelpInvoiceDto.setObjectId(enterpriseWorkerEntity.getEnterpriseId());

        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename();
        if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
            return R.fail("请选择Excel文件");
        }

        InvoiceListListener makerImportListener = new InvoiceListListener(selfHelpInvoiceDto, selfHelpInvoiceDetailService);
        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        ExcelReaderBuilder builder = EasyExcel.read(inputStream, InvoiceListExcel.class, makerImportListener);
        builder.doReadAll();

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @GetMapping("/immediate-payment")
    @ApiOperation(value = "立即支付", notes = "立即支付")
    public R immediatePaymentWeb(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.data(selfHelpInvoiceAccountService.immediatePayment());
    }

    @PostMapping("/confirm-payment")
    @ApiOperation(value = "确认支付", notes = "确认支付")
    public R confirmPaymentWeb(@Valid @RequestBody ConfirmPaymentDTO confirmPaymentDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.data(selfHelpInvoiceFeeService.confirmPayment(confirmPaymentDto));
    }

}
