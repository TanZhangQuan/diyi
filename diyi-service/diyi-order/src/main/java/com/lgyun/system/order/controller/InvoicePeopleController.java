package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.InvoicePeopleEntity;
import com.lgyun.system.order.service.IInvoicePeopleService;
import com.lgyun.system.order.vo.InvoicePeopleVO;
import com.lgyun.system.order.wrapper.InvoicePeopleWrapper;
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

/**
 *  控制器
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@RestController
@RequestMapping("/invoicePeople")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "开票人", tags = "开票人")
public class InvoicePeopleController {
	private Logger logger = LoggerFactory.getLogger(InvoicePeopleController.class);
	private final IInvoicePeopleService invoicePeopleService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入invoicePeople")
	public R<InvoicePeopleVO> detail(InvoicePeopleEntity invoicePeople) {
		InvoicePeopleEntity detail = invoicePeopleService.getOne(Condition.getQueryWrapper(invoicePeople));
		return R.data(InvoicePeopleWrapper.build().entityVO(detail));
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入invoicePeople")
	public R<IPage<InvoicePeopleVO>> list(InvoicePeopleEntity invoicePeople, Query query) {
		IPage<InvoicePeopleEntity> pages = invoicePeopleService.page(Condition.getPage(query), Condition.getQueryWrapper(invoicePeople));
		return R.data(InvoicePeopleWrapper.build().pageVO(pages));
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入invoicePeople")
	public R save(@Valid @RequestBody InvoicePeopleEntity invoicePeople) {
		return R.status(invoicePeopleService.save(invoicePeople));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入invoicePeople")
	public R update(@Valid @RequestBody InvoicePeopleEntity invoicePeople) {
		return R.status(invoicePeopleService.updateById(invoicePeople));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入InvoicePeople")
	public R submit(@Valid @RequestBody InvoicePeopleEntity invoicePeople) {
		return R.status(invoicePeopleService.saveOrUpdate(invoicePeople));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(invoicePeopleService.removeByIds(Func.toLongList(ids)));
	}

}
