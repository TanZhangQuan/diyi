package com.lgyun.system.order.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ApplyState;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.RealnameVerifyUtil;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.dto.ConfirmPaymentDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.dto.SelfHelpInvoicePayDto;
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


    @GetMapping("/findMakerTypeSelfHelpInvoice")
    @ApiOperation(value = "根据创客类型查询自助开票", notes = "根据创客类型查询自助开票")
    public R findMakerTypeSelfHelpInvoice(Query query, BladeUser bladeUser, MakerType makerType,
                                          @RequestParam(required = false) String invoicePeopleName,
                                          @RequestParam(required = false) String startTime,
                                          @RequestParam(required = false) String endTime) {
        log.info("根据创客类型查询自助开票");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
            return selfHelpInvoiceService.findMakerTypeSelfHelpInvoice(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), makerType,invoicePeopleName,startTime,endTime);
        } catch (Exception e) {
            log.error("根据创客类型查询自助开票失败", e);
        }
        return R.fail("根据创客类型查询自助开票失败");
    }

    @GetMapping("/getSelfHelpInvoiceDetails")
    @ApiOperation(value = "查询开票详情", notes = "查询开票详情")
    public R getSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
        log.info("查询开票详情");
        try {
            return selfHelpInvoiceService.getSelfHelpInvoiceDetails(selfHelpInvoiceId);
        } catch (Exception e) {
            log.error("查询开票详情失败", e);
        }
        return R.fail("查询开票详情失败");
    }

    @PostMapping("/saveAddress")
    @ApiOperation(value = "新建收货地址", notes = "新建收货地址")
    public R saveAddress(@Valid @RequestBody AddressDto addressDto, BladeUser bladeUser) {
        log.info("新建收货地址");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
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
            if (!(result.isSuccess())){
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
    public R uploadDeliverSheetUrl(@NotNull(message = "自助开票详情id有误") @RequestParam(required = false) Long selfHelpInvoiceDetailId,
                                   @NotNull(message = "交付支付验收单URL不能为空") @RequestParam(required = false) String deliverSheetUrl) {
        log.info("上传交付支付验收单URL");
        try {
            return selfHelpInvoiceDetailService.uploadDeliverSheetUrl(selfHelpInvoiceDetailId, deliverSheetUrl);
        } catch (Exception e) {
            log.error("上传交付支付验收单URL失败", e);
        }
        return R.fail("上传交付支付验收单URL失败");
    }

    @PostMapping("/submitSelfHelpInvoiceWeb")
    @ApiOperation(value = "商户提交自助开票", notes = "商户提交自助开票")
    public R submitSelfHelpInvoiceWeb(@ApiParam(value = "文件") @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file,
                                      @Valid @RequestBody SelfHelpInvoiceDto selfHelpInvoiceDto, BladeUser bladeUser) {
        log.info("商户提交自助开票");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
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
            InvoiceListListener makerImportListener = new InvoiceListListener(selfHelpInvoiceDto,selfHelpInvoiceDetailService);
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

    @GetMapping("/find_self_help_invoice_by_service_provider")
    @ApiOperation(value = "查询当前服务商的所有众包/众采", notes = "查询当前服务商的所有众包/众采")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "selfHelfInvoiceId", value = "众包/众采ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "enterpriseName", value = "商户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", paramType = "query", dataType = "date")
    })
    public R findSelfHelpInvoiceByServiceProvider(SelfHelpInvoicePayDto selfHelpInvoicePayDto, Query query, BladeUser bladeUser) {
        log.info("查询当前服务商的所有众包/众采");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.findSelfHelpInvoiceByServiceProvider(Condition.getPage(query.setDescs("create_time")), serviceProviderWorkerEntity.getServiceProviderId(), selfHelpInvoicePayDto);
        } catch (Exception e) {
            log.error("查询当前服务商的所有众包/众采失败", e);
        }

        return R.fail("查询失败");
    }

    @PostMapping("/audit")
    @ApiOperation(value = "自主开票审核", notes = "自主开票审核")
    public R audit(@ApiParam(value = "自主开票编号") @NotNull(message = "请输入自主开票编号") @RequestParam(required = false) Long selfHelpInvoiceId, @ApiParam(value = "自主开票审核状态") @NotNull(message = "请选择自主开票审核状态") @RequestParam(required = false) ApplyState applyState, BladeUser bladeUser) {

        log.info("自主开票审核");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.audit(serviceProviderWorkerEntity.getId(), selfHelpInvoiceId, applyState);
        } catch (Exception e) {
            log.error("自主开票审核异常", e);
        }
        return R.fail("审核失败");
    }

    @GetMapping("/query_crowd_year_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包年流水", notes = "查询当前商户众包年流水")
    public R queryCrowdYearTradeByEnterprise(BladeUser bladeUser) {

        log.info("查询当前商户众包年流水");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdYearTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("查询当前商户众包年流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_year_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包年流水", notes = "查询当前服务商众包年流水")
    public R queryCrowdYearTradeByServiceProvider(BladeUser bladeUser) {

        log.info("查询当前服务商众包年流水");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdYearTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
        } catch (Exception e) {
            log.error("查询当前服务商众包年流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_month_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包包本月流水", notes = "查询当前商户众包包本月流水")
    public R queryCrowdMonthTradeByEnterprise(BladeUser bladeUser) {

        log.info("查询当前商户众包包本月流水");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdMonthTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("查询当前商户众包包本月流水异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query_crowd_month_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包包本月流水", notes = "查询当前服务商众包包本月流水")
    public R queryCrowdMonthTradeByServiceProvider(BladeUser bladeUser) {

        log.info("查询当前服务商众包包本月流水");
        try {
            //获取当前服务商员工
            R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

            return selfHelpInvoiceService.queryCrowdMonthTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
        } catch (Exception e) {
            log.error("查询当前服务商众包包本月流水异常", e);
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
