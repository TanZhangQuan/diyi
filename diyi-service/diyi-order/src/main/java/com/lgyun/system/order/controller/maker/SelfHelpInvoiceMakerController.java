package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.InvoiceCategory;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.ConfirmModificationDTO;
import com.lgyun.system.order.dto.NaturalPersonConfirmSubmitDTO;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.MakerEntity;
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
@RequestMapping("/maker/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "创客端---自助开票管理模块相关接口", tags = "创客端---自助开票管理模块相关接口")
public class SelfHelpInvoiceMakerController {

    private IUserClient userClient;
    private IAddressService addressService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询收件地址", notes = "查询收件地址")
    public R queryAddressList(Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return addressService.queryAddressList(ObjectType.MAKERPEOPLE, makerEntity.getId(), Condition.getPage(query.setDescs("create_time")));
    }


    @PostMapping("/natural-person-submit-form")
    @ApiOperation(value = "自助开票上传表单", notes = "自助开票上传表单")
    public R naturalPersonSubmitForm(@ApiParam(value = "自助开票清单", required = true) @NotNull(message = "请上传自助开票清单") @RequestParam(required = false) String listFile,
                                     @ApiParam(value = "服务商" )  @RequestParam(required = false) Long serviceProviderId,
                                     @ApiParam(value = "发票类型")  @RequestParam(required = false) InvoiceCategory invoiceCategory,
                                     @ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请输入开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                     @ApiParam(value = "众包支付模式", required = true) @NotNull(message = "请选择众包支付模式") @RequestParam(required = false) CrowdSourcingPayType payType,
                                     @ApiParam(value = "开票类目", required = true) @NotNull(message = "请选择开票类目") @RequestParam(required = false) String invoiceType,
                                     @ApiParam(value = "收货地址", required = true) @NotNull(message = "请选择收货地址") @RequestParam(required = false) Long addressId,BladeUser bladeUser) throws Exception{
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();


        return selfHelpInvoiceService.naturalPersonSubmitForm(ObjectType.MAKERPEOPLE,makerEntity.getId(),listFile,serviceProviderId,invoiceCategory,makerType,payType,invoiceType,addressId);
    }

    @PostMapping("/natural-person-confirm-submit")
    @ApiOperation(value = "自助开票确认提交表单", notes = "自助开票确认提交表单")
    public R naturalPersonConfirmSubmit(@Valid @RequestBody NaturalPersonConfirmSubmitDTO naturalPersonConfirmSubmitDto, BladeUser bladeUser){
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();


        return selfHelpInvoiceService.naturalPersonConfirmSubmit(ObjectType.MAKERPEOPLE,makerEntity.getId(),naturalPersonConfirmSubmitDto);
    }

    @GetMapping("/query-self-invoice-list")
    @ApiOperation(value = "创客查询自助开票", notes = "创客查询自助开票")
    public R querySelfInvoiceList(@ApiParam(value = "开票人身份类别", required = true) @NotNull(message = "请选择开票人身份类别") @RequestParam(required = false) MakerType makerType,
                                  @RequestParam(required = false)String startTiem,@RequestParam(required = false) String endTime,Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return selfHelpInvoiceService.querySelfInvoiceList(ObjectType.MAKERPEOPLE,makerEntity.getId(), makerType ,startTiem,endTime, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-self-invoice-details")
    @ApiOperation(value = "商户查询自助开票详情", notes = "商户查询自助开票详情")
    public R querySelfInvoiceDetails(@ApiParam(value = "自助开票id", required = true) @NotNull(message = "请输入自助开票id") @RequestParam(required = false) Long selfHelpInvoiceId,
                                     BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
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
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return selfHelpInvoiceService.submitSelfHelpInvoice(selfHelpInvoiceId);
    }


    @PostMapping("/confirm-modification")
    @ApiOperation(value = "确认修改", notes = "确认修改")
    public R confirmModification(@Valid @RequestBody ConfirmModificationDTO confirmModificationDTO, BladeUser bladeUser){
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
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
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return selfHelpInvoiceService.confirmPayment(selfHelpInvoiceId,selfHelpInvoiceFeeId,payCertificate);
    }

}
