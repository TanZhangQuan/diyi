package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.vo.AddressVO;
import com.lgyun.system.order.wrapper.AddressWrapper;
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
@RequestMapping("/address")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "收件地址", tags = "收件地址")
public class AddressController {
	private Logger logger = LoggerFactory.getLogger(AddressController.class);
	private final IAddressService addressService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入address")
	public R<AddressVO> detail(AddressEntity address) {
		AddressEntity detail = addressService.getOne(Condition.getQueryWrapper(address));
		return R.data(AddressWrapper.build().entityVO(detail));
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入address")
	public R<IPage<AddressVO>> list(AddressEntity address, Query query) {
		IPage<AddressEntity> pages = addressService.page(Condition.getPage(query), Condition.getQueryWrapper(address));
		return R.data(AddressWrapper.build().pageVO(pages));
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入address")
	public R save(@Valid @RequestBody AddressEntity address) {
		return R.status(addressService.save(address));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入address")
	public R update(@Valid @RequestBody AddressEntity address) {
		return R.status(addressService.updateById(address));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入Address")
	public R submit(@Valid @RequestBody AddressEntity address) {
		return R.status(addressService.saveOrUpdate(address));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(addressService.removeByIds(Func.toLongList(ids)));
	}

}
