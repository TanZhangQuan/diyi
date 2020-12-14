package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddOrUpdateAddressDTO;
import com.lgyun.system.order.dto.ConfirmPaymentDTO;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.ISelfHelpInvoiceAccountService;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoiceFeeService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @ApiOperation(value = "新建或修改收件地址", notes = "新建或修改收件地址")
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
