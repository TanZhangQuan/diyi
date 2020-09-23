package com.lgyun.system.order.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.RealnameVerifyUtil;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.dto.DictDTO;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.dto.ConfirmPaymentDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.dto.admin.ToExamineSelfHelpInvoiceDto;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.excel.InvoiceListListener;
import com.lgyun.system.order.service.*;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@RestController
@RequestMapping("/service/selfhelpinvoice")
@Validated
@AllArgsConstructor
@Api(value = "（平台）自助开票相关接口", tags = "（平台）自助开票相关接口")
public class SelfHelpInvoiceAdminController {

    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private IAddressService addressService;
    private IDictClient iDictClient;
    private IUserClient iUserClient;
    private ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;
    private ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;

    @GetMapping("/getMakerTypeSelfHelpInvoice")
    @ApiOperation(value = "平台跟据创客身份查询自助开票", notes = "平台跟据创客身份查询自助开票")
    public R getMakerTypeSelfHelpInvoice(@ApiParam(value = "商户") @RequestParam(required = false) String enterpriseName,
                                         @ApiParam(value = "开始时间") @RequestParam(required = false) String startTime,
                                         @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
                                         @ApiParam(value = "创客身份") MakerType makerType,
                                         Query query, BladeUser bladeUser) {

        log.info("平台跟据创客身份查询自助开票");
        try {
            return selfHelpInvoiceService.getAdminMakerTypeSelfHelpInvoice(enterpriseName,startTime,endTime,makerType, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("平台跟据创客身份查询自助开票异常", e);
        }
        return R.fail("平台跟据创客身份查询自助开票失败");
    }

    @GetMapping("/getMakerTypeSelfHelpInvoiceDetails")
    @ApiOperation(value = "平台跟据创客身份查询自助开票详情", notes = "平台跟据创客身份查询自助开票详情")
    public R getMakerTypeSelfHelpInvoiceDetails(BladeUser bladeUser,Long selfHelpInvoiceId) {

        log.info("平台跟据创客身份查询自助开票详情");
        try {
            return selfHelpInvoiceService.getMakerTypeSelfHelpInvoiceDetails(selfHelpInvoiceId);
        } catch (Exception e) {
            log.error("平台跟据创客身份查询自助开票详情异常", e);
        }
        return R.fail("平台跟据创客身份查询自助开票详情失败");
    }

    @PostMapping("/toExamineSelfHelpInvoice")
    @ApiOperation(value = "平台审核自助开票", notes = "平台审核自助开票")
    public R toExamineSelfHelpInvoice(BladeUser bladeUser,@Valid @RequestBody ToExamineSelfHelpInvoiceDto toExamineSelfHelpInvoiceDto) {

        log.info("平台审核自助开票");
        try {
            return selfHelpInvoiceService.toExamineSelfHelpInvoice(toExamineSelfHelpInvoiceDto);
        } catch (Exception e) {
            log.error("平台审核自助开票异常", e);
        }
        return R.fail("平台审核自助开票失败");
    }

    @PostMapping("/matchServiceProvider")
    @ApiOperation(value = "平台上传支付回单和匹配服务商", notes = "平台上传支付回单和匹配服务商")
    public R matchServiceProvider(BladeUser bladeUser,Long selfHelpInvoiceId,Long selfHelpInvoiceFeeId,Long serviceProviderId,String payCertificate) {

        log.info("平台上传支付回单和匹配服务商开票");
        try {
            return selfHelpInvoiceService.matchServiceProvider( selfHelpInvoiceId,selfHelpInvoiceFeeId, serviceProviderId,payCertificate);
        } catch (Exception e) {
            log.error("平台上传支付回单和匹配服务商异常", e);
        }
        return R.fail("平台上传支付回单和匹配服务商失败");
    }

    @PostMapping("/uploadAdminExpress")
    @ApiOperation(value = "平台上传快递", notes = "平台上传快递")
    public R uploadAdminExpress(BladeUser bladeUser,Long selfHelpInvoiceId,
                                                    Long serviceProviderId,
                                                    String expressNo,
                                                    String expressCompanyName) {

        log.info("平台上传快递");
        try {
            return selfHelpInvoiceService.uploadAdminExpress(selfHelpInvoiceId, serviceProviderId,expressNo,expressCompanyName);
        } catch (Exception e) {
            log.error("平台上传快递异常", e);
        }
        return R.fail("平台上传快递失败");
    }

    @PostMapping("/uploadAdminInvoice")
    @ApiOperation(value = "平台上传发票", notes = "平台上传发票")
    public R uploadAdminInvoice(BladeUser bladeUser,
                                Long selfHelpInvoiceApplyProviderDetailId,
                                String invoiceScanPictures,
                                String taxScanPictures) {

        log.info("平台上传快递");
        try {
            return selfHelpInvoiceService.uploadAdminInvoice(selfHelpInvoiceApplyProviderDetailId,invoiceScanPictures,taxScanPictures);
        } catch (Exception e) {
            log.error("平台上传发票异常", e);
        }
        return R.fail("平台上传发票失败");
    }
}
