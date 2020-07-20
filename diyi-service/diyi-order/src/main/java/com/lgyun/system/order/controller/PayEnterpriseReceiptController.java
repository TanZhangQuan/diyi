package com.lgyun.system.order.controller;

import com.lgyun.system.order.service.IPayEnterpriseReceiptService;
import com.lgyun.system.order.vo.PayEnterpriseReceiptVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.wrapper.PayEnterpriseReceiptWrapper;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import java.util.List;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Slf4j
@RestController
@RequestMapping("/order/payenterprisereceipt")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class PayEnterpriseReceiptController {

	private IPayEnterpriseReceiptService payEnterpriseReceiptService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody PayEnterpriseReceiptEntity payEnterpriseReceipt) {
		return R.status(payEnterpriseReceiptService.save(payEnterpriseReceipt));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody PayEnterpriseReceiptEntity payEnterpriseReceipt) {
		return R.status(payEnterpriseReceiptService.updateById(payEnterpriseReceipt));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<PayEnterpriseReceiptVO> detail(PayEnterpriseReceiptEntity payEnterpriseReceipt) {
		PayEnterpriseReceiptEntity detail = payEnterpriseReceiptService.getOne(Condition.getQueryWrapper(payEnterpriseReceipt));
		return R.data(PayEnterpriseReceiptWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<PayEnterpriseReceiptVO>> list(PayEnterpriseReceiptEntity payEnterpriseReceipt, Query query) {
		IPage<PayEnterpriseReceiptEntity> pages = payEnterpriseReceiptService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(payEnterpriseReceipt));
		return R.data(PayEnterpriseReceiptWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(payEnterpriseReceiptService.removeByIds(Func.toLongList(ids)));
	}

}
