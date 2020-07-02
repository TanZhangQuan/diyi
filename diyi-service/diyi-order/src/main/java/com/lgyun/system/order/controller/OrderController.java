package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.system.entity.Tenant;
import com.lgyun.system.order.dto.OrderDTO;
import com.lgyun.system.order.entity.OrderEntity;
import com.lgyun.system.order.service.IOrderService;
import com.lgyun.system.order.service.IWorkAchievementService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.order.vo.OrderVO;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.wrapper.OrderWrapper;
import com.lgyun.system.user.controller.AgreementController;
import io.swagger.annotations.*;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.Map;

/**
 *  订单
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
@RestController
@RequestMapping("/order")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "订单", tags = "订单")
public class OrderController {
	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	private final IOrderService orderService;

	private final IWorkAchievementService workAchievementService;
	/**
	 * 工单列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderState", value = "订单状态", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "工单列表", notes = "工单列表")
	public R<IPage<OrderVO>> list(@ApiIgnore @RequestParam Map<String, Object> order, Query query) {
		QueryWrapper<OrderEntity> queryWrapper = Condition.getQueryWrapper(order, OrderEntity.class);
		IPage<OrderEntity> pages = orderService.page(Condition.getPage(query),queryWrapper);
		return R.data(OrderWrapper.build().pageVO(pages));
	}

	/**
	 * 抢单
	 */
	@PostMapping("/robOrder")
	@ApiOperation(value = "抢单", notes = "传入订单id和创客id")
	public R robOrder(@ApiParam(value = "订单id", required = true) @RequestParam Long orderId,
					  @ApiParam(value = "创客id", required = true) @RequestParam Long makerId) {
		return orderService.robOrder(orderId,makerId);
	}
	/**
	 * 发布工单
	 */
	@PostMapping("/save")
	@ApiOperation(value = "发布工单", notes = "传入order")
	public R save(@Valid @RequestBody OrderDTO orderDTO) {
		logger.info("发布工单");
		OrderEntity orderEntity = BeanUtil.copy(orderDTO, OrderEntity.class);
		return R.status(orderService.save(orderEntity));
	}


	/**
	 * 验收工作成果
	 */
	@PostMapping("/accepted")
	@ApiOperation(value = "验收工作成果", notes = "验收工作成果")
	public R accepted(BigDecimal checkMoneyNum,Long workAchievementId,Long positionId) {
		return workAchievementService.accepted(checkMoneyNum, workAchievementId, positionId);
	}
}
