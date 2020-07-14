package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.dto.ConfirmPaymentDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.dto.SelfHelpInvoicePersonDto;
import com.lgyun.system.order.service.*;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.service.IRunCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

	private IRunCompanyService runCompanyService;

	private ISelfHelpInvoicePersonService selfHelpInvoicePersonService;

	private ISelfHelpInvoiceService selfHelpInvoiceService;

	private IAddressService addressService;

	private IDictClient iDictClient;

	private ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;

	private ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;

	/**
	 * 新建购买方
	 */
	@PostMapping("/runCompanySave")
	@ApiOperation(value = "新建购买方", notes = "新建购买方")
	public R runCompanySave(@Valid @RequestBody RunCompanyDto runCompanyDto,Long makerId) {
		return runCompanyService.runCompanySave(runCompanyDto,makerId);
	}

	/**
	 * 通过创客id查询购买方
	 */
	@GetMapping("/findMakerId")
	@ApiOperation(value = "查询购买方", notes = "查询购买方")
	public R findMakerId(Query query,Long makerId) {
		return runCompanyService.findMakerId(Condition.getPage(query),makerId);
	}

	/**
	 * 新建非创客开票人
	 */
	@PostMapping("/saveSelfHelpInvoicePerson")
	@ApiOperation(value = "新建非创客开票人", notes = "新建非创客开票人")
	public R saveSelfHelpInvoicePerson(@Valid @RequestBody SelfHelpInvoicePersonDto selfHelpInvoicePersonDto, Long makerId) {
		return selfHelpInvoicePersonService.saveSelfHelpInvoicePerson(selfHelpInvoicePersonDto,makerId);
	}

	/**
	 * 根据创客Idc查询自助开票非创客开票人
	 */
	@GetMapping("/findPersonMakerId")
	@ApiOperation(value = "查询非创客开票人", notes = "查询非创客开票人")
	public R findPersonMakerId(Query query,Long makerId) {
		return selfHelpInvoicePersonService.findPersonMakerId(Condition.getPage(query),makerId);
	}

	/**
	 * 新建收货地址
	 */
	@PostMapping("/saveAddress")
	@ApiOperation(value = "新建收货地址", notes = "新建收货地址")
	public R saveAddress(@Valid @RequestBody AddressDto addressDto, Long makerId) {
		return addressService.saveAddress(addressDto,makerId);
	}

	/**
	 * 根据创客Id收货地址
	 */
	@GetMapping("/findAddressMakerId")
	@ApiOperation(value = "查询收货地址", notes = "查询收货地址")
	public R findAddressMakerId(Query query,Long makerId) {
		return addressService.findAddressMakerId(Condition.getPage(query),makerId);
	}

	/**
	 * 开票类目
	 */
	@GetMapping("/getInvoiceType")
	@ApiOperation(value = "开票类目", notes = "开票类目")
	public R getInvoiceType() {
		return iDictClient.getList("tax_category");
	}

	/**
	 *开票类目-详情
	 */
	@GetMapping("/getInvoiceTypeDetails")
	@ApiOperation(value = "开票类目-详情", notes = "开票类目-详情")
	public R getInvoiceTypeDetails(Long parentId) {
		return iDictClient.getParentList(parentId);
	}

	/**
	 * 创客提交自助开票
	 */
	@PostMapping("/submitSelfHelpInvoice")
	@ApiOperation(value = "创客提交自助开票", notes = "创客提交自助开票")
	public R submitSelfHelpInvoice(@Valid @RequestBody SelfHelpInvoiceDto selfHelpInvoiceDto) {
		return selfHelpInvoiceService.submitSelfHelpInvoice(selfHelpInvoiceDto);
	}

	/**
	 * 查询开票详情
	 */
	@GetMapping("/getSelfHelpInvoiceDetails")
	@ApiOperation(value = "查询开票详情", notes = "查询开票详情")
	public R getSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
		return selfHelpInvoiceService.getSelfHelpInvoiceDetails(selfHelpInvoiceId);
	}

	/**
	 * 立即支付
	 */
	@GetMapping("/immediatePayment")
	@ApiOperation(value = "立即支付", notes = "立即支付")
	public R immediatePayment() {
		return R.data(selfHelpInvoiceAccountService.immediatePayment());
	}

	/**
	 * 确认支付
	 */
	@GetMapping("/confirmPayment")
	@ApiOperation(value = "确认支付", notes = "确认支付")
	public R confirmPayment(@Valid @RequestBody ConfirmPaymentDto confirmPaymentDto) {
		return R.data(selfHelpInvoiceFeeService.confirmPayment(confirmPaymentDto));
	}
}
