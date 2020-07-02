package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.entity.InvoicePeopleEntity;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.IInvoicePeopleService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.order.vo.AddressVO;
import com.lgyun.system.order.vo.InvoicePeopleVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceVO;
import com.lgyun.system.order.wrapper.AddressWrapper;
import com.lgyun.system.order.wrapper.InvoicePeopleWrapper;
import com.lgyun.system.order.wrapper.SelfHelpInvoiceWrapper;
import com.lgyun.system.user.entity.RunCompanyEntity;
import com.lgyun.system.user.service.IRunCompanyService;
import com.lgyun.system.user.vo.RunCompanyVO;
import com.lgyun.system.user.wrapper.RunCompanyWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  控制器
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@RestController
@RequestMapping("/order")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "自助开票", tags = "自助开票")
public class SelfHelpInvoiceController {
	private Logger logger = LoggerFactory.getLogger(SelfHelpInvoiceController.class);
	private final ISelfHelpInvoiceService selfHelpInvoiceService;

	private final IRunCompanyService runCompanyService;

	private final IInvoicePeopleService invoicePeopleService;

	private final IAddressService addressService;
	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入selfHelpInvoice")
	public R<SelfHelpInvoiceVO> detail(SelfHelpInvoiceEntity selfHelpInvoice) {
		SelfHelpInvoiceEntity detail = selfHelpInvoiceService.getOne(Condition.getQueryWrapper(selfHelpInvoice));
		return R.data(SelfHelpInvoiceWrapper.build().entityVO(detail));
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入selfHelpInvoice")
	public R<IPage<SelfHelpInvoiceVO>> list(SelfHelpInvoiceEntity selfHelpInvoice, Query query) {
		IPage<SelfHelpInvoiceEntity> pages = selfHelpInvoiceService.page(Condition.getPage(query), Condition.getQueryWrapper(selfHelpInvoice));
		return R.data(SelfHelpInvoiceWrapper.build().pageVO(pages));
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入selfHelpInvoice")
	public R save(@Valid @RequestBody SelfHelpInvoiceEntity selfHelpInvoice) {
		return R.status(selfHelpInvoiceService.save(selfHelpInvoice));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入selfHelpInvoice")
	public R update(@Valid @RequestBody SelfHelpInvoiceEntity selfHelpInvoice) {
		return R.status(selfHelpInvoiceService.updateById(selfHelpInvoice));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入SelfHelpInvoice")
	public R submit(@Valid @RequestBody SelfHelpInvoiceEntity selfHelpInvoice) {
		return R.status(selfHelpInvoiceService.saveOrUpdate(selfHelpInvoice));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(selfHelpInvoiceService.removeByIds(Func.toLongList(ids)));
	}


	/**
	 * 根据购买方姓名查询
	 */
	@GetMapping("/findCompanyName")
	@ApiOperation(value = "根据购买方姓名查询", notes = "根据购买方姓名查询")
	public R<List<RunCompanyVO>> findCompanyName(String companyName) {
		List<RunCompanyEntity> runCompanyEntities = runCompanyService.findCompanyName(companyName);
		List<RunCompanyVO> runCompanyVOS = RunCompanyWrapper.build().listVO(runCompanyEntities);
		return R.data(runCompanyVOS);
	}

	/**
	 * 添加购买方
	 */
	@PostMapping("/addRunCompany")
	@ApiOperation(value = "添加购买方", notes = "添加购买方")
	public R addRunCompany(@Valid @RequestBody RunCompanyEntity runCompanyEntity) {
		return R.status(runCompanyService.save(runCompanyEntity));
	}

	/**
	 * 通过创客id查询开票人
	 */
	@GetMapping("/findInvoicePeopleMakerId")
	@ApiOperation(value = "通过创客id查询开票人", notes = "通过创客id查询开票人")
	public R<List<InvoicePeopleVO>> findInvoicePeopleMakerId(Long makerId) {
		List<InvoicePeopleEntity> invoicePeopleMakerId = invoicePeopleService.findInvoicePeopleMakerId(makerId);
		return R.data(InvoicePeopleWrapper.build().listVO(invoicePeopleMakerId));
	}

	/**
	 * 添加开票人
	 */
	@PostMapping("/addInvoicePeople")
	@ApiOperation(value = "添加开票人", notes = "添加开票人")
	public R addInvoicePeople(@Valid @RequestBody InvoicePeopleEntity invoicePeopleEntity) {
		return R.status(invoicePeopleService.save(invoicePeopleEntity));
	}

	/**
	 * 通过购买方id查询收件地址
	 */
	@GetMapping("/findAddressCompanyId")
	@ApiOperation(value = "通过购买方id查询收件地址", notes = "通过购买方id查询收件地址")
	public R<List<AddressVO>> findAddressCompanyId(Long companyId) {
		List<AddressEntity> addressCompanyId = addressService.findAddressCompanyId(companyId);
		return R.data(AddressWrapper.build().listVO(addressCompanyId));
	}

	/**
	 * 添加收件地址
	 */
	@PostMapping("/addAddress")
	@ApiOperation(value = "添加收件地址", notes = "添加收件地址")
	public R addAddress(@Valid @RequestBody AddressEntity addressEntity) {
		return R.status(addressService.save(addressEntity));
	}
}
