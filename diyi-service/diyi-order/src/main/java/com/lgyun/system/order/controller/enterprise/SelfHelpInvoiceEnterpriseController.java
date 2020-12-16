package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.ConfirmModificationDTO;
import com.lgyun.system.order.dto.NaturalPersonConfirmSubmitDTO;
import com.lgyun.system.order.service.*;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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



//    @GetMapping("/query-self-helf-invoices-list")
//    @ApiOperation(value = "查询当前商户所有自助开票记录", notes = "查询当前商户所有自助开票记录")
//    public R querySelfHelfInvoicesList(@ApiParam(value = "创客类型", required = true) @NotNull(message = "请选择创客类型") @RequestParam(required = false) MakerType makerType, SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, Query query, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//
//        return selfHelpInvoiceService.getSelfHelfInvoicesByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), makerType, selfHelpInvoicesByEnterpriseDto, Condition.getPage(query.setDescs("t1.create_time")));
//    }
//
//    @GetMapping("/query-self-helf-invoices-detail")
//    @ApiOperation(value = "查询当前商户某条自助开票记录详情", notes = "查询当前商户某条自助开票记录详情")
//    public R querySelfHelfInvoicesDetail(@ApiParam(value = "自助开票", required = true) @NotNull(message = "请选择自助开票") @RequestParam(required = false) Long selfHelpInvoiceId, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//
//        return selfHelpInvoiceService.getSingleSelfHelfInvoiceByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), selfHelpInvoiceId);
//    }
//
//    @GetMapping("/query-self-helf-invoices-detail-list")
//    @ApiOperation(value = "查询当前商户某条自助开票记录的所有自助开票明细", notes = "查询当前商户某条自助开票记录的所有自助开票明细")
//    public R querySelfHelfInvoicesDetailList(@ApiParam(value = "自助开票", required = true) @NotNull(message = "请输入自助开票") @RequestParam(required = false) Long selfHelpInvoiceId, Query query, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceService.getSelfHelfInvoiceDetailListBySelfHelfInvoice(selfHelpInvoiceId, Condition.getPage(query.setDescs("t2.create_time")));
//    }
//
//    @GetMapping("/query-self-helf-invoice-express-list")
//    @ApiOperation(value = "查询当前商户某条自助开票记录的所有快递信息", notes = "查询当前商户某条自助开票记录的所有快递信息")
//    public R querySelfHelfInvoiceExpressList(@ApiParam(value = "自助开票", required = true) @NotNull(message = "请选择自助开票") @RequestParam(required = false) Long selfHelpInvoiceId, Query query, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//
//        return selfHelpInvoiceService.getSelfHelfInvoiceExpressBySelfHelfInvoiceAndEnterprise(enterpriseWorkerEntity.getEnterpriseId(), selfHelpInvoiceId, Condition.getPage(query.setDescs("t3.create_time")));
//    }
//
//    @PostMapping("/create-address")
//    @ApiOperation(value = "新建或修改收件地址", notes = "新建或修改收件地址")
//    public R createAddress(@Valid @RequestBody AddOrUpdateAddressDTO addOrUpdateAddressDto, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//
//        return addressService.addOrUpdateAddress(addOrUpdateAddressDto, enterpriseWorkerEntity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE);
//    }
//
//    @GetMapping("/query-address-list")
//    @ApiOperation(value = "查询收件地址", notes = "查询收件地址")
//    public R queryAddressList(Query query, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//
//        return addressService.queryAddressList(ObjectType.ENTERPRISEPEOPLE, enterpriseWorkerEntity.getEnterpriseId(), Condition.getPage(query.setDescs("create_time")));
//    }
//
//    @PostMapping("/upload-deliver-sheet")
//    @ApiOperation(value = "上传交付支付验收单", notes = "上传交付支付验收单")
//    public R uploadDeliverSheet(@NotNull(message = "请选择自助开票明细") @RequestParam(required = false) Long selfHelpInvoiceDetailId,
//                                @NotBlank(message = "请上传交付支付验收单") @RequestParam(required = false) String deliverSheetUrl, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return selfHelpInvoiceDetailService.uploadDeliverSheetUrl(selfHelpInvoiceDetailId, deliverSheetUrl);
//    }
//
//    @PostMapping("/submit-self-help-invoice")
//    @ApiOperation(value = "当前商户提交自助开票", notes = "商户提交自助开票")
//    public R submitSelfHelpInvoice(@ApiParam(value = "文件") @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file,
//                                   @Valid @RequestBody SelfHelpInvoiceDTO selfHelpInvoiceDto, BladeUser bladeUser) throws IOException {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
//        //判断文件内容是否为空
//        if (file == null || file.isEmpty()) {
//            return R.fail("Excel文件为空");
//        }
//
//        //判断文件内容是否为空
//        selfHelpInvoiceDto.setObjectType(ObjectType.ENTERPRISEPEOPLE);
//        selfHelpInvoiceDto.setObjectId(enterpriseWorkerEntity.getEnterpriseId());
//
//        // 查询上传文件的后缀
//        String suffix = file.getOriginalFilename();
//        if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
//            return R.fail("请选择Excel文件");
//        }
//
//        InvoiceListListener makerImportListener = new InvoiceListListener(selfHelpInvoiceDto, selfHelpInvoiceDetailService);
//        InputStream inputStream = new BufferedInputStream(file.getInputStream());
//        ExcelReaderBuilder builder = EasyExcel.read(inputStream, InvoiceListExcel.class, makerImportListener);
//        builder.doReadAll();
//        return R.success("申请成功");
//    }
//
//    @GetMapping("/immediate-payment")
//    @ApiOperation(value = "立即支付", notes = "立即支付")
//    public R immediatePaymentWeb(BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return R.data(selfHelpInvoiceAccountService.immediatePayment());
//    }
//
//    @PostMapping("/confirm-payment")
//    @ApiOperation(value = "确认支付", notes = "确认支付")
//    public R confirmPaymentWeb(@Valid @RequestBody ConfirmPaymentDTO confirmPaymentDto, BladeUser bladeUser) {
//        //查询当前商户员工
//        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
//        if (!(result.isSuccess())) {
//            return result;
//        }
//
//        return R.data(selfHelpInvoiceFeeService.confirmPayment(confirmPaymentDto));
//    }

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

    @PostMapping("/natural-person-submit-form")
    @ApiOperation(value = "自助开票上传表单", notes = "自助开票上传表单")
    public R naturalPersonSubmitForm(@ApiParam(value = "自助开票清单", required = true) @NotNull(message = "请上传自助开票清单") @RequestParam(required = false) String listFile,
                                     @ApiParam(value = "服务商" )  @RequestParam(required = false) Long serviceProviderId,
                                     @ApiParam(value = "发票类型")  @RequestParam(required = false) String invoiceCategory,
                                     @ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请输入开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                     @ApiParam(value = "众包支付模式", required = true) @NotNull(message = "请选择众包支付模式") @RequestParam(required = false) CrowdSourcingPayType payType,
                                     @ApiParam(value = "开票类目", required = true) @NotNull(message = "请选择开票类目") @RequestParam(required = false) String invoiceType,
                                     @ApiParam(value = "收货地址", required = true) @NotNull(message = "请选择收货地址") @RequestParam(required = false) Long addressId,BladeUser bladeUser) throws Exception{
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.naturalPersonSubmitForm(enterpriseWorkerEntity.getEnterpriseId(),listFile,serviceProviderId,invoiceCategory,makerType,payType,invoiceType,addressId);
    }


    @PostMapping("/natural-person-confirm-submit")
    @ApiOperation(value = "自助开票确认提交表单", notes = "自助开票确认提交表单")
    public R naturalPersonConfirmSubmit(@Valid @RequestBody NaturalPersonConfirmSubmitDTO naturalPersonConfirmSubmitDto, BladeUser bladeUser){
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.naturalPersonConfirmSubmit(enterpriseWorkerEntity.getEnterpriseId(),naturalPersonConfirmSubmitDto);
    }


    @GetMapping("/query-self-invoice-list")
    @ApiOperation(value = "商户查询自助开票", notes = "商户查询自助开票")
    public R querySelfInvoiceList(@ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请选择开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                  @RequestParam(required = false)String startTiem,@RequestParam(required = false) String endTime,Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.querySelfInvoiceList(enterpriseWorkerEntity.getEnterpriseId(), makerType ,startTiem,endTime, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-self-invoice-details")
    @ApiOperation(value = "商户查询自助开票详情", notes = "商户查询自助开票详情")
    public R querySelfInvoiceDetails(@ApiParam(value = "自助开票id", required = true) @NotNull(message = "请输入自助开票id") @RequestParam(required = false) Long selfHelpInvoiceId,
                                  BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return selfHelpInvoiceService.querySelfInvoiceDetails(selfHelpInvoiceId);
    }


    @PostMapping("/submit-self-help-invoice")
    @ApiOperation(value = "提交自助开票", notes = "提交自助开票")
    public R submitSelfHelpInvoice(@ApiParam(value = "自助开票id", required = true) @NotNull(message = "请输入自助开票id") @RequestParam(required = false) Long selfHelpInvoiceId,
                                   BladeUser bladeUser){
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return selfHelpInvoiceService.submitSelfHelpInvoice(selfHelpInvoiceId);
    }


    @PostMapping("/confirm-modification")
    @ApiOperation(value = "确认修改", notes = "确认修改")
    public R confirmModification(@Valid @RequestBody ConfirmModificationDTO confirmModificationDTO, BladeUser bladeUser){
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return selfHelpInvoiceService.confirmModification(confirmModificationDTO.getSelfHelpInvoiceId(),confirmModificationDTO.getList());
    }

    @PostMapping("/confirm-payment")
    @ApiOperation(value = "确认支付", notes = "确认支付")
    public R confirmPayment(@ApiParam(value = "自助开票id", required = true) @NotNull(message = "请输入自助开票id") @RequestParam(required = false) Long selfHelpInvoiceId,
                            @ApiParam(value = "自助开票支付id", required = true) @NotNull(message = "请输入自助开票支付id") @RequestParam(required = false)Long selfHelpInvoiceFeeId,
                            @ApiParam(value = "支付回单", required = true) @NotNull(message = "请输入支付回单") @RequestParam(required = false)String payCertificate, BladeUser bladeUser){
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return selfHelpInvoiceService.confirmPayment(selfHelpInvoiceId,selfHelpInvoiceFeeId,payCertificate);
    }


}
