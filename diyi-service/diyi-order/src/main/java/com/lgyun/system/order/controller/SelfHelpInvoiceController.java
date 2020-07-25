package com.lgyun.system.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.RealnameVerifyUtil;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.dto.DictDTO;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.dto.ConfirmPaymentDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.dto.SelfHelpInvoicePersonDto;
import com.lgyun.system.order.service.*;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@RestController
@RequestMapping("/order/selfhelpinvoice")
@Validated
@AllArgsConstructor
@Api(value = "自助开票相关接口", tags = "自助开票相关接口")
public class SelfHelpInvoiceController {

    private ISelfHelpInvoicePersonService selfHelpInvoicePersonService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private IAddressService addressService;
    private IDictClient iDictClient;
    private IUserClient iUserClient;
    private ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;
    private ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;

    @PostMapping("/runCompanySave")
    @ApiOperation(value = "新建购买方", notes = "新建购买方")
    public R runCompanySave(@Valid @RequestBody RunCompanyDto runCompanyDto, BladeUser bladeUser) {
        log.info("新建购买方");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            runCompanyDto.setMakerId(makerEntity.getId());
            return iUserClient.runCompanySave(runCompanyDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("新建购买方失败");
        }
    }

    @GetMapping("/findMakerId")
    @ApiOperation(value = "查询购买方", notes = "查询购买方")
    public R findMakerId(Query query, BladeUser bladeUser) {
        log.info("通过创客id查询购买方");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return iUserClient.findRunCompanyMakerId(query.getCurrent(), query.getSize(), makerEntity.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("通过创客id查询购买方失败");
        }
    }

    @PostMapping("/saveSelfHelpInvoicePerson")
    @ApiOperation(value = "新建非创客开票人", notes = "新建非创客开票人")
    public R saveSelfHelpInvoicePerson(@Valid @RequestBody SelfHelpInvoicePersonDto selfHelpInvoicePersonDto, BladeUser bladeUser) {
        log.info("新建非创客开票人");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return selfHelpInvoicePersonService.saveSelfHelpInvoicePerson(selfHelpInvoicePersonDto, makerEntity.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("新建非创客开票人失败");
        }
    }

    @GetMapping("/findPersonMakerId")
    @ApiOperation(value = "查询非创客开票人", notes = "查询非创客开票人")
    public R findPersonMakerId(Query query, MakerType makerType, BladeUser bladeUser) {
        log.info("根据创客Idc查询自助开票非创客开票人");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            switch (makerType) {

                case INDIVIDUALBUSINESS:
                    return iUserClient.individualBusinessListByMaker(query.getCurrent(), query.getSize(), makerEntity.getId(), null);

                case NATURALPERSON:
                    return selfHelpInvoicePersonService.findPersonMakerId(query.getCurrent(), query.getSize(), makerEntity.getId(), makerType);

                case INDIVIDUALENTERPRISE:
                    return iUserClient.individualEnterpriseListByMaker(query.getCurrent(), query.getSize(), makerEntity.getId(), null);

                default:
                    return R.fail("创客类型有误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("根据创客Idc查询自助开票非创客开票人失败");
        }
    }

    @PostMapping("/saveAddress")
    @ApiOperation(value = "新建收货地址", notes = "新建收货地址")
    public R saveAddress(@Valid @RequestBody AddressDto addressDto, BladeUser bladeUser) {
        log.info("新建收货地址");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return addressService.saveAddress(addressDto, makerEntity.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("新建收货地址失败");
        }
    }

    @GetMapping("/getAddressById")
    @ApiOperation(value = "地址详情接口", notes = "地址详情接口")
    public R getAddressById(Long addressId) {
        log.info("地址详情接口");
        try {
            return addressService.getAddressById(addressId);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("地址详情接口失败");
        }
    }

    @PostMapping("/updateAddress")
    @ApiOperation(value = "地址编辑接口", notes = "地址编辑接口")
    public R updateAddress(@Valid @RequestBody AddressDto addressDto) {
        log.info("地址编辑接口");
        try {
            return addressService.updateAddress(addressDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("地址编辑接口失败");
        }
    }

    @PostMapping("/deleteAddress")
    @ApiOperation(value = "地址删除接口", notes = "地址删除接口")
    public R deleteAddress(Long addressId) {
        log.info("地址编辑接口");
        try {
            return addressService.deleteAddress(addressId);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("地址删除接口失败");
        }
    }

    @GetMapping("/findAddressMakerId")
    @ApiOperation(value = "查询收货地址", notes = "查询收货地址")
    public R findAddressMakerId(Query query, BladeUser bladeUser) {
        log.info("查询收货地址");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return addressService.findAddressMakerId(query.getCurrent(), query.getCurrent(), makerEntity.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("查询收货地址失败");
        }
    }

    @GetMapping("/getInvoiceType")
    @ApiOperation(value = "开票类目", notes = "开票类目")
    public R getInvoiceType() {
        log.info("开票类目");
        try {
            return iDictClient.getList("tax_category");
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("开票类目-详情失败");
        }
    }

    @GetMapping("/getInvoiceTypeDetails")
    @ApiOperation(value = "开票类目-详情", notes = "开票类目-详情")
    public R getInvoiceTypeDetails(Long parentId) {
        log.info("开票类目-详情");
        try {
            return iDictClient.getParentList(parentId);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("开票类目-详情失败");
        }
    }

    @PostMapping("/saveInvoiceTypeDetails")
    @ApiOperation(value = "新建开票类目", notes = "新建开票类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType", value = "1代表一级类目，2二级类目", paramType = "query", dataType = "int"),
    })
    public R saveInvoiceTypeDetails(@RequestParam String dictValue, @RequestParam Integer dictType, @RequestParam(required = false) Long parentId) {
        log.info("新建开票类目");
        if (dictType != 1 && dictType != 2) {
            R.fail("参数错误");
        }
        Dict data = iDictClient.getDict("tax_category");
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
        try {
            return iDictClient.saveDict(dictDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("新建开票类目失败");
        }
    }

    @PostMapping("/submitSelfHelpInvoice")
    @ApiOperation(value = "创客提交自助开票", notes = "创客提交自助开票")
    public R submitSelfHelpInvoice(@Valid @RequestBody SelfHelpInvoiceDto selfHelpInvoiceDto, BladeUser bladeUser) {
        log.info("创客提交自助开票");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            selfHelpInvoiceDto.setApplyMakerId(makerEntity.getId());
            return selfHelpInvoiceService.submitSelfHelpInvoice(selfHelpInvoiceDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("创客提交自助开票失败");
        }
    }

    @GetMapping("/getSelfHelpInvoiceDetails")
    @ApiOperation(value = "查询开票详情", notes = "查询开票详情")
    public R getSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
        log.info("查询开票详情");
        try {
            return selfHelpInvoiceService.getSelfHelpInvoiceDetails(selfHelpInvoiceId);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("查询开票详情失败");
        }
    }

    @GetMapping("/immediatePayment")
    @ApiOperation(value = "立即支付", notes = "立即支付")
    public R immediatePayment() {
        log.info("立即支付");
        try {
            return R.data(selfHelpInvoiceAccountService.immediatePayment());
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("立即支付失败");
        }
    }

    @PostMapping("/confirmPayment")
    @ApiOperation(value = "确认支付", notes = "确认支付")
    public R confirmPayment(@Valid @RequestBody ConfirmPaymentDto confirmPaymentDto) {
        log.info("确认支付");
        try {
            return R.data(selfHelpInvoiceFeeService.confirmPayment(confirmPaymentDto));
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("确认支付失败");
        }
    }

    @GetMapping("/identificationCard")
    @ApiOperation(value = "识别身份证", notes = "识别身份证")
    public R identificationCard(String infoImg) {
        log.info("识别身份证");
        JSONObject jsonObject = null;
        try {
            jsonObject = RealnameVerifyUtil.idCardOCR(infoImg);
        } catch (Exception e) {
            return R.fail("识别失败");
        }
        return R.data(jsonObject);
    }

    @GetMapping("/judgeMakerAatural")
    @ApiOperation(value = "判断创客资质", notes = "判断创客资质")
    public R judgeMakerAatural(MakerType makerType, BladeUser bladeUser) {

        log.info("判断创客资质");
        try {
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            if (MakerType.INDIVIDUALENTERPRISE.equals(makerType)) {
                List<IndividualEnterpriseEntity> individualEnterpriseEntities = iUserClient.individualEnterpriseFindByMakerId(makerEntity.getId());
                if (null == individualEnterpriseEntities || individualEnterpriseEntities.size() <= 0) {
                    return R.fail("对不起，您还不符合个独开票的资质");
                }
            }

            if (MakerType.INDIVIDUALBUSINESS.equals(makerType)) {
                List<IndividualBusinessEntity> individualBusinessEntities = iUserClient.individualBusinessByMakerId(makerEntity.getId());
                if (null == individualBusinessEntities || individualBusinessEntities.size() <= 0) {
                    return R.fail("对不起，您还不符合个体开票的资质");
                }
            }
            return R.success("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("判断创客资质失败");
        }
    }
}
