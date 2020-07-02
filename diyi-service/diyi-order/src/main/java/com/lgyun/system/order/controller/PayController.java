package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.system.order.entity.PayEntity;
import com.lgyun.system.order.service.IPayService;
import com.lgyun.system.order.vo.PayVO;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.wrapper.PayWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  支付
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
@RestController
@RequestMapping("/pay")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "支付", tags = "支付")
public class PayController {
	private Logger logger = LoggerFactory.getLogger(PayController.class);
	private final IPayService payService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入pay")
	public R<PayVO> detail(PayEntity pay) {
		PayEntity detail = payService.getOne(Condition.getQueryWrapper(pay));
		return R.data(PayWrapper.build().entityVO(detail));
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入pay")
	public R<IPage<PayVO>> list(PayEntity pay, Query query) {
		IPage<PayEntity> pages = payService.page(Condition.getPage(query), Condition.getQueryWrapper(pay));
		return R.data(PayWrapper.build().pageVO(pages));
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入pay")
	public R save(@Valid @RequestBody PayEntity pay) {
		return R.status(payService.save(pay));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入pay")
	public R update(@Valid @RequestBody PayEntity pay) {
		return R.status(payService.updateById(pay));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入Pay")
	public R submit(@Valid @RequestBody PayEntity pay) {
		return R.status(payService.saveOrUpdate(pay));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(payService.removeByIds(Func.toLongList(ids)));
	}

}
