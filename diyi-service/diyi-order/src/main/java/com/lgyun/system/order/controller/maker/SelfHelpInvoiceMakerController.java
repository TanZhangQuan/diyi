package com.lgyun.system.order.controller.maker;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.dto.DictDTO;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.order.dto.AddressDTO;
import com.lgyun.system.order.dto.ConfirmPaymentDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceDTO;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.excel.InvoiceListListener;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.ISelfHelpInvoiceAccountService;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoiceFeeService;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
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
import java.util.List;

@RestController
@RequestMapping("/maker/self-help-invoice")
@Validated
@AllArgsConstructor
@Api(value = "创客端---自助开票管理模块相关接口", tags = "创客端---自助开票管理模块相关接口")
public class SelfHelpInvoiceMakerController {

    private IUserClient userClient;
    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;
    private IAddressService addressService;
    private IDictClient dictClient;
    private ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;
    private ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;

    @PostMapping("/create-address")
    @ApiOperation(value = "新建或修改收货地址", notes = "新建或修改收货地址")
    public R createAddress(@Valid @RequestBody AddressDTO addressDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return addressService.addOrUpdateAddress(addressDto, makerEntity.getId(), ObjectType.MAKERPEOPLE);
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

    @GetMapping("/query-invoice-type")
    @ApiOperation(value = "开票类目", notes = "开票类目")
    public R queryInvoiceType(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return dictClient.getList("tax_category");
    }

    @GetMapping("/query-invoice-type-detail")
    @ApiOperation(value = "开票类目详情", notes = "开票类目详情")
    public R queryInvoiceTypeDetail(Long parentId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return dictClient.getParentList(parentId);
    }

    @PostMapping("/create-invoice-type")
    @ApiOperation(value = "新建开票类目", notes = "新建开票类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType", value = "1代表一级类目，2二级类目", paramType = "query", dataType = "int"),
    })
    public R createInvoiceType(@RequestParam String dictValue, @RequestParam Integer dictType, @RequestParam(required = false) Long parentId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        if (dictType != 1 && dictType != 2) {
            R.fail("参数错误");
        }
        Dict data = dictClient.getDict("tax_category");
        if (null == data) {
            R.fail("添加失败缺少开票类目");
        }
        DictDTO dictDTO = new DictDTO();
        if (dictType == 1) {
            dictDTO.setCode("tax_category");
            dictDTO.setDictKey(1);
            dictDTO.setDictValue(dictValue);
            dictDTO.setParentId(data.getId());
            dictDTO.setSort(0);
        } else {
            dictDTO.setCode("tax_category_details");
            dictDTO.setDictKey(2);
            dictDTO.setDictValue(dictValue);
            dictDTO.setParentId(parentId);
            dictDTO.setSort(0);
        }

        return dictClient.saveDict(dictDTO);
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
            return R.fail("Excel文件不能为空");
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
            List<IndividualEnterpriseEntity> individualEnterpriseEntities = userClient.queryIndividualEnterpriseFindByMakerId(makerEntity.getId());
            if (null == individualEnterpriseEntities || individualEnterpriseEntities.size() <= 0) {
                return R.fail("对不起，您还不符合个独开票的资质");
            }
        }

        if (MakerType.INDIVIDUALBUSINESS.equals(makerType)) {
            List<IndividualBusinessEntity> individualBusinessEntities = userClient.queryIndividualBusinessByMakerId(makerEntity.getId());
            if (null == individualBusinessEntities || individualBusinessEntities.size() <= 0) {
                return R.fail("对不起，您还不符合个体开票的资质");
            }
        }

        return R.success("成功");
    }

}
