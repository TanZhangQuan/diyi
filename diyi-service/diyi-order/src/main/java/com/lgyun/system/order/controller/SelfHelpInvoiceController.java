package com.lgyun.system.order.controller;

import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.dto.SelfHelpInvoicePersonDto;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.ISelfHelpInvoicePersonService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.service.IRunCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import lombok.RequiredArgsConstructor;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.vo.SelfHelpInvoiceVO;
import com.lgyun.system.order.wrapper.SelfHelpInvoiceWrapper;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@RestController
@RequestMapping("/order/selfhelpinvoice")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "自助开票相关接口", tags = "自助开票相关接口")
public class SelfHelpInvoiceController {
	private Logger logger = LoggerFactory.getLogger(SelfHelpInvoiceController.class);

	private final ISelfHelpInvoiceService selfHelpInvoiceService;

	private final IRunCompanyService runCompanyService;

	private final ISelfHelpInvoicePersonService selfHelpInvoicePersonService;

	private final IAddressService addressService;

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
		return R.data("");
	}

	/**
	 * 创客提交自助开票
	 */

}
