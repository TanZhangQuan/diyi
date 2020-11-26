package com.lgyun.system.order.controller.maker;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddOrUpdateAddressDTO;
import com.lgyun.system.order.dto.ConfirmPaymentDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceDTO;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.excel.InvoiceListListener;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.ISelfHelpInvoiceAccountService;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoiceFeeService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/maker/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "创客端---自助开票管理模块相关接口", tags = "创客端---自助开票管理模块相关接口")
public class SelfHelpInvoiceMakerController {

    private IUserClient userClient;
    private IAddressService addressService;
    private ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;
    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;
    private ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;

    @PostMapping("/create-address")
    @ApiOperation(value = "新建或修改收货地址", notes = "新建或修改收货地址")
    public R createAddress(@Valid @RequestBody AddOrUpdateAddressDTO addOrUpdateAddressDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return addressService.addOrUpdateAddress(addOrUpdateAddressDto, makerEntity.getId(), ObjectType.MAKERPEOPLE);
    }

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询收货地址", notes = "查询收货地址")
    public R queryAddressList(Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return addressService.queryAddressList(ObjectType.MAKERPEOPLE, makerEntity.getId(), Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/submit-self-help-invoice")
    @ApiOperation(value = "创客提交自助开票", notes = "创客提交自助开票")
    @Transactional(rollbackFor = Exception.class)
    public R submitSelfHelpInvoice(@ApiParam(value = "文件", required = true) @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file,
                                   @Valid @RequestBody SelfHelpInvoiceDTO selfHelpInvoiceDto, BladeUser bladeUser) throws IOException {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();
        //判断文件内容是否为空
        if (file.isEmpty()) {
            return R.fail("Excel文件为空");
        }
        selfHelpInvoiceDto.setObjectType(ObjectType.MAKERPEOPLE);
        selfHelpInvoiceDto.setObjectId(makerEntity.getId());
        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename();
        if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
            return R.fail("请选择Excel文件");
        }
        InvoiceListListener makerImportListener = new InvoiceListListener(selfHelpInvoiceDto, selfHelpInvoiceDetailService);
        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        ExcelReaderBuilder builder = EasyExcel.read(inputStream, InvoiceListExcel.class, makerImportListener);
        builder.doReadAll();
        return R.success("申请成功");
    }

    @GetMapping("/immediate-payment")
    @ApiOperation(value = "立即支付", notes = "立即支付")
    public R immediatePayment(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.data(selfHelpInvoiceAccountService.immediatePayment());
    }

    @PostMapping("/confirm-payment")
    @ApiOperation(value = "确认支付", notes = "确认支付")
    public R confirmPayment(@Valid @RequestBody ConfirmPaymentDTO confirmPaymentDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.data(selfHelpInvoiceFeeService.confirmPayment(confirmPaymentDto));
    }

    @GetMapping("/judge-maker-aatural")
    @ApiOperation(value = "判断创客资质", notes = "判断创客资质")
    public R judgeMakerAatural(MakerType makerType, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        if (MakerType.INDIVIDUALENTERPRISE.equals(makerType)) {
            int individualEnterpriseEntities = userClient.queryIndividualEnterpriseNumByMakerId(makerEntity.getId());
            if (individualEnterpriseEntities <= 0) {
                return R.fail("对不起，您还不符合个独开票的资质");
            }
        }

        if (MakerType.INDIVIDUALBUSINESS.equals(makerType)) {
            int individualBusinessEntities = userClient.queryIndividualBusinessNumByMakerId(makerEntity.getId());
            if (individualBusinessEntities <= 0) {
                return R.fail("对不起，您还不符合个体开票的资质");
            }
        }

        return R.success("成功");
    }

}
