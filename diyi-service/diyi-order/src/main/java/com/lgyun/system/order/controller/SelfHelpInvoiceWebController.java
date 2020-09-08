package com.lgyun.system.order.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.RealnameVerifyUtil;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.order.dto.*;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.excel.InvoiceListListener;
import com.lgyun.system.order.service.*;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * 商户自助开票相关接口
 *
 * @author jun.
 * @date 2020/7/30.
 * @time 17:37.
 */
@Slf4j
@RestController
@RequestMapping("/order/webSelfhelpinvoice")
@Validated
@AllArgsConstructor
@Api(value = "自助开票相关接口(管理端)", tags = "自助开票相关接口(管理端)")
public class SelfHelpInvoiceWebController {

    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private IAddressService addressService;
    private IDictClient iDictClient;
    private IUserClient iUserClient;
    private ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;
    private ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;

    @GetMapping("/get-self-helf-invoices-by-enterprise")
    @ApiOperation(value = "查询当前商户所有自助开票记录", notes = "查询当前商户所有自助开票记录")
    public R getSelfHelfInvoicesByEnterprise(@ApiParam(value = "创客类型") @NotNull(message = "请选择创客类型") @RequestParam(required = false) InvoicePeopleType invoicePeopleType, SelfHelpInvoicesByEnterpriseDto selfHelpInvoicesByEnterpriseDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户所有自助开票记录");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.getSelfHelfInvoicesByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), invoicePeopleType, selfHelpInvoicesByEnterpriseDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户所有自助开票记录异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get-single-self-helf-invoice-by-enterprise")
    @ApiOperation(value = "查询当前商户某条自助开票记录详情", notes = "查询当前商户某条自助开票记录详情")
    public R getSingleSelfHelfInvoiceByEnterprise(@ApiParam(value = "自助开票ID") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, BladeUser bladeUser) {

        log.info("查询当前商户某条自助开票记录详情");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.getSingleSelfHelfInvoiceByEnterprise(enterpriseWorkerEntity.getEnterpriseId(), selfHelpInvoiceId);
        } catch (Exception e) {
            log.error("查询当前商户某条自助开票记录详情异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get-self-helf-invoice-details-by-self-helf-invoice-and-enterprise")
    @ApiOperation(value = "查询当前商户某条自助开票记录的所有自助开票明细", notes = "查询当前商户某条自助开票记录的所有自助开票明细")
    public R getSelfHelfInvoiceDetailsBySelfHelfInvoiceAndEnterprise(@ApiParam(value = "自助开票ID") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, Query query, BladeUser bladeUser) {

        log.info("查询当前商户某条自助开票记录的所有自助开票明细");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.getSelfHelfInvoiceDetailsBySelfHelfInvoiceAndEnterprise(enterpriseWorkerEntity.getEnterpriseId(), selfHelpInvoiceId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户某条自助开票记录的所有自助开票明细异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get-self-helf-invoice-express-by-self-helf-invoice-and-enterprise")
    @ApiOperation(value = "查询当前商户某条自助开票记录的所有快递信息", notes = "查询当前商户某条自助开票记录的所有快递信息")
    public R getSelfHelfInvoiceExpressBySelfHelfInvoiceAndEnterprise(@ApiParam(value = "自助开票ID") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, Query query, BladeUser bladeUser) {

        log.info("查询当前商户某条自助开票记录的所有快递信息");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.getSelfHelfInvoiceExpressBySelfHelfInvoiceAndEnterprise(enterpriseWorkerEntity.getEnterpriseId(), selfHelpInvoiceId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前商户某条自助开票记录的所有快递信息异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get-self-helf-invoices-by-service-provider")
    @ApiOperation(value = "查询当前服务商所有自助开票记录", notes = "查询当前服务商所有自助开票记录")
    public R getSelfHelfInvoicesByServiceProvider(@ApiParam(value = "创客类型") @NotNull(message = "请选择创客类型") @RequestParam(required = false) InvoicePeopleType invoicePeopleType,
                                                        @ApiParam(value = "自助开票-服务商状态") @NotNull(message = "请选择自助开票-服务商状态") @RequestParam(required = false) SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState,
                                                        SelfHelpInvoiceDetailsByServiceProviderDto selfHelpInvoiceDetailsByServiceProviderDto, Query query, BladeUser bladeUser) {

        log.info("查询当前服务商所有自助开票记录");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.getSelfHelfInvoicesByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId(), invoicePeopleType, selfHelpInvoiceSpApplyState, selfHelpInvoiceDetailsByServiceProviderDto, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询当前服务商所有自助开票记录异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get-single-self-helf-invoice-by-service-provider")
    @ApiOperation(value = "查询当前服务商某条自助开票记录详情", notes = "查询当前服务商某条自助开票记录详情")
    public R getSingleSelfHelfInvoiceByServiceProvider(@ApiParam(value = "自助开票ID") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, BladeUser bladeUser) {

        log.info("查询当前服务商某条自助开票记录详情");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.getSingleSelfHelfInvoiceByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId(), selfHelpInvoiceId);
        } catch (Exception e) {
            log.error("查询当前服务商某条自助开票记录详情异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/upload_invoice_tax_by_provider")
    @ApiOperation(value = "服务商自助开票上传发票税票", notes = "服务商自助开票上传发票税票")
    public R uploadInvoiceTaxByProvider(@Valid @RequestBody SelfHelpInvoiceDetailInvoiceTaxDto selfHelpInvoiceDetailInvoiceTaxDto, BladeUser bladeUser) {

        log.info("服务商自助开票上传发票税票");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.uploadInvoiceTaxByProvider(serviceProviderWorkerEntity, selfHelpInvoiceDetailInvoiceTaxDto);
        } catch (Exception e) {
            log.error("服务商自助开票上传发票税票失败", e);
        }
        return R.fail("上传失败");
    }

    @PostMapping("/fill_express_by_provider")
    @ApiOperation(value = "服务商自助开票填写快递信息", notes = "服务商自助开票填写快递信息")
    public R fillExpressByProvider(@Valid @RequestBody SelfHelpInvoiceExpressDto selfHelpInvoiceExpressDto, BladeUser bladeUser) {

        log.info("服务商自助开票填写快递信息");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.fillExpressByProvider(serviceProviderWorkerEntity, selfHelpInvoiceExpressDto);
        } catch (Exception e) {
            log.error("服务商自助开票填写快递信息失败", e);
        }
        return R.fail("快递信息更新失败");
    }

    @GetMapping("/get-self-helf-invoice-express-by-self-helf-invoice-and-provider")
    @ApiOperation(value = "查询当前服务商某条自助开票记录的快递信息", notes = "查询当前服务商某条自助开票记录的快递信息")
    public R getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(@ApiParam(value = "自助开票ID") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, Query query, BladeUser bladeUser) {

        log.info("查询当前服务商某条自助开票记录的快递信息");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(serviceProviderWorkerEntity.getServiceProviderId(), selfHelpInvoiceId);
        } catch (Exception e) {
            log.error("查询当前服务商某条自助开票记录的快递信息异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/saveAddress")
    @ApiOperation(value = "新建收货地址", notes = "新建收货地址")
    public R saveAddress(@Valid @RequestBody AddressDto addressDto, BladeUser bladeUser) {
        log.info("新建收货地址");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return addressService.addOrUpdate(addressDto, enterpriseWorkerEntity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE);
        } catch (Exception e) {
            log.error("新建收货地址失败", e);
        }
        return R.fail("新建收货地址失败");
    }

    @GetMapping("/getAddressById")
    @ApiOperation(value = "地址详情接口", notes = "地址详情接口")
    public R getAddressById(Long addressId) {
        log.info("地址详情接口");
        try {
            return addressService.getAddressById(addressId);
        } catch (Exception e) {
            log.error("地址详情接口失败", e);
        }
        return R.fail("地址详情接口失败");
    }

    @PostMapping("/updateAddress")
    @ApiOperation(value = "地址编辑接口", notes = "地址编辑接口")
    public R updateAddress(@Valid @RequestBody AddressDto addressDto) {
        log.info("地址编辑接口");
        try {
            return addressService.updateAddress(addressDto);
        } catch (Exception e) {
            log.error("地址编辑接口失败", e);
        }
        return R.fail("地址编辑接口失败");
    }

    @PostMapping("/deleteAddress")
    @ApiOperation(value = "地址删除接口", notes = "地址删除接口")
    public R deleteAddress(Long addressId) {
        log.info("地址编辑接口");
        try {
            return addressService.deleteAddress(addressId);
        } catch (Exception e) {
            log.error("地址删除接口失败", e);
        }
        return R.fail("地址删除接口失败");
    }

    @GetMapping("/findAddressMakerId")
    @ApiOperation(value = "查询收货地址", notes = "查询收货地址")
    public R findAddressMakerId(Query query, BladeUser bladeUser) {
        log.info("查询收货地址");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return addressService.findAddressMakerId(query.getCurrent(), query.getCurrent(), enterpriseWorkerEntity.getEnterpriseId(), ObjectType.ENTERPRISEPEOPLE, null);
        } catch (Exception e) {
            log.error("查询收货地址失败", e);
        }
        return R.fail("查询收货地址失败");
    }

    @GetMapping("/getInvoiceType")
    @ApiOperation(value = "开票类目", notes = "开票类目")
    public R getInvoiceType() {
        log.info("开票类目");
        try {
            return iDictClient.getList("tax_category");
        } catch (Exception e) {
            log.error("开票类目-详情失败", e);
        }
        return R.fail("开票类目-详情失败");
    }

    @PostMapping("/uploadDeliverSheetUrl")
    @ApiOperation(value = "上传交付支付验收单URL", notes = "上传交付支付验收单URL")
    public R uploadDeliverSheetUrl(@NotNull(message = "请输入自助开票明细编号") @RequestParam(required = false) Long selfHelpInvoiceDetailId,
                                   @NotBlank(message = "请上传交付支付验收单") @RequestParam(required = false) String deliverSheetUrl) {
        log.info("上传交付支付验收单URL");
        try {
            return selfHelpInvoiceDetailService.uploadDeliverSheetUrl(selfHelpInvoiceDetailId, deliverSheetUrl);
        } catch (Exception e) {
            log.error("上传交付支付验收单URL失败", e);
        }
        return R.fail("上传交付支付验收单URL失败");
    }

    @PostMapping("/submitSelfHelpInvoiceWeb")
    @ApiOperation(value = "当前商户提交自助开票", notes = "商户提交自助开票")
    public R submitSelfHelpInvoiceWeb(@ApiParam(value = "文件") @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file,
                                      @Valid @RequestBody SelfHelpInvoiceDto selfHelpInvoiceDto, BladeUser bladeUser) {
        log.info("商户提交自助开票");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            //判断文件内容是否为空
            if (file.isEmpty()) {
                return R.fail("Excel文件不能为空");
            }

            //判断文件内容是否为空
            selfHelpInvoiceDto.setObjectType(ObjectType.ENTERPRISEPEOPLE);
            selfHelpInvoiceDto.setObjectId(enterpriseWorkerEntity.getEnterpriseId());

            // 获取上传文件的后缀
            String suffix = file.getOriginalFilename();
            if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
                return R.fail("请选择Excel文件");
            }

            InvoiceListListener makerImportListener = new InvoiceListListener(selfHelpInvoiceDto, selfHelpInvoiceDetailService);
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            ExcelReaderBuilder builder = EasyExcel.read(inputStream, InvoiceListExcel.class, makerImportListener);
            builder.doReadAll();
            return R.success("申请成功");
        } catch (Exception e) {
            log.error("商户提交自助开票失败", e);
        }
        return R.fail("商户提交自助开票失败");
    }

    @GetMapping("/immediatePaymentWeb")
    @ApiOperation(value = "立即支付", notes = "立即支付")
    public R immediatePaymentWeb() {
        log.info("立即支付");
        try {
            return R.data(selfHelpInvoiceAccountService.immediatePayment());
        } catch (Exception e) {
            log.error("立即支付失败", e);
        }
        return R.fail("立即支付失败");
    }

    @PostMapping("/confirmPaymentWeb")
    @ApiOperation(value = "确认支付", notes = "确认支付")
    public R confirmPaymentWeb(@Valid @RequestBody ConfirmPaymentDto confirmPaymentDto) {
        log.info("确认支付");
        try {
            return R.data(selfHelpInvoiceFeeService.confirmPayment(confirmPaymentDto));
        } catch (Exception e) {
            log.error("确认支付失败", e);
        }
        return R.fail("确认支付失败");
    }

    @GetMapping("/identificationCardWeb")
    @ApiOperation(value = "识别身份证", notes = "识别身份证")
    public R identificationCardWeb(String infoImg) {
        log.info("识别身份证");
        JSONObject jsonObject = null;
        try {
            jsonObject = RealnameVerifyUtil.idCardOCR(infoImg);
        } catch (Exception e) {
            log.error("识别失败", e);
        }
        return R.data(jsonObject);
    }

    @GetMapping("/find_enterprise_by_maker_id")
    @ApiOperation(value = "根据创客ID查询商户", notes = "根据创客ID查询商户")
    public R findEnterpriseByMakerId(Query query, BladeUser bladeUser) {
        log.info("根据创客ID查询商户");
        try {
            //获取当前创客
            R<MakerEntity> result = iUserClient.currentMaker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return iUserClient.findEnterpriseByMakerId(query.getCurrent(), query.getSize(), makerEntity.getId());
        } catch (Exception e) {
            log.error("根据创客ID查询商户异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/audit")
    @ApiOperation(value = "自助开票审核", notes = "自助开票审核")
    public R audit(@ApiParam(value = "自助开票编号") @NotNull(message = "请输入自助开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, @ApiParam(value = "自助开票审核状态") @NotNull(message = "请选择自助开票审核状态") @RequestParam(required = false) SelfHelpInvoiceSpApplyState applyState, BladeUser bladeUser) {

        log.info("自助开票审核");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.audit(serviceProviderWorkerEntity.getId(), selfHelpInvoiceId, applyState);
        } catch (Exception e) {
            log.error("自助开票审核异常", e);
        }
        return R.fail("审核失败");
    }

    @GetMapping("/query_crowd_year_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包/众采年流水", notes = "查询当前商户众包/众采年流水")
    public R queryCrowdYearTradeByEnterprise(BladeUser bladeUser) {

        log.info("查询当前商户众包/众采年流水");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdYearTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("查询当前商户众包/众采年流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_year_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包/众采年流水", notes = "查询当前服务商众包/众采年流水")
    public R queryCrowdYearTradeByServiceProvider(BladeUser bladeUser) {

        log.info("查询当前服务商众包/众采年流水");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdYearTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
        } catch (Exception e) {
            log.error("查询当前服务商众包/众采年流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_month_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包/众采本月流水", notes = "查询当前商户众包/众采本月流水")
    public R queryCrowdMonthTradeByEnterprise(BladeUser bladeUser) {

        log.info("查询当前商户众包/众采本月流水");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdMonthTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("查询当前商户众包/众采本月流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_month_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包/众采本月流水", notes = "查询当前服务商众包/众采本月流水")
    public R queryCrowdMonthTradeByServiceProvider(BladeUser bladeUser) {

        log.info("查询当前服务商众包/众采本月流水");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdMonthTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
        } catch (Exception e) {
            log.error("查询当前服务商众包/众采本月流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_week_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包包本周流水", notes = "查询当前商户众包包本周流水")
    public R queryCrowdWeekTradeByEnterprise(BladeUser bladeUser) {

        log.info("查询当前商户众包包本周流水");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdWeekTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("查询当前商户众包包本周流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_week_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包包本周流水", notes = "查询当前服务商众包包本周流水")
    public R queryCrowdWeekTradeByServiceProvider(BladeUser bladeUser) {

        log.info("查询当前服务商众包包本周流水");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdWeekTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
        } catch (Exception e) {
            log.error("查询当前服务商众包包本周流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_day_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包包今日流水", notes = "查询当前商户众包包今日流水")
    public R queryCrowdDayTradeByEnterprise(BladeUser bladeUser) {

        log.info("查询当前商户众包包今日流水");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdDayTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("查询当前商户众包包今日流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_day_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包包今日流水", notes = "查询当前服务商众包包今日流水")
    public R queryCrowdDayTradeByServiceProvider(BladeUser bladeUser) {

        log.info("查询当前服务商众包包今日流水");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdDayTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
        } catch (Exception e) {
            log.error("查询当前服务商众包包今日流水异常", e);
        }
        return R.fail("查询失败");
    }

}
