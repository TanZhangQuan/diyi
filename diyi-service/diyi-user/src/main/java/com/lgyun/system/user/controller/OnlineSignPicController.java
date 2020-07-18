package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.OnlineSignPicEntity;
import com.lgyun.system.user.service.IOnlineSignPicService;
import com.lgyun.system.user.vo.OnlineSignPicVO;
import com.lgyun.system.user.wrapper.OnlineSignPicWrapper;
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
import java.util.List;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-18 15:59:14
 */
@Slf4j
@RestController
@RequestMapping("/order/onlinesignpic")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class OnlineSignPicController {

	private IOnlineSignPicService onlineSignPicService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody OnlineSignPicEntity onlineSignPic) {
		return R.status(onlineSignPicService.save(onlineSignPic));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody OnlineSignPicEntity onlineSignPic) {
		return R.status(onlineSignPicService.updateById(onlineSignPic));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<OnlineSignPicVO> detail(OnlineSignPicEntity onlineSignPic) {
		OnlineSignPicEntity detail = onlineSignPicService.getOne(Condition.getQueryWrapper(onlineSignPic));
		return R.data(OnlineSignPicWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<OnlineSignPicVO>> list(OnlineSignPicEntity onlineSignPic, Query query) {
		IPage<OnlineSignPicEntity> pages = onlineSignPicService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(onlineSignPic));
		return R.data(OnlineSignPicWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(onlineSignPicService.removeByIds(Func.toLongList(ids)));
	}

}
