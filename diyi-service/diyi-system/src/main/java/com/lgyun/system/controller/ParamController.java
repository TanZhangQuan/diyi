package com.lgyun.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.service.IParamService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;

import com.lgyun.common.api.R;
import com.lgyun.common.ctrl.BladeController;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.entity.Param;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/param")
@Api(value = "参数管理", tags = "接口")
public class ParamController extends BladeController {

	private IParamService paramService;

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入param")
	public R<Param> detail(Param param) {
		Param detail = paramService.getOne(Condition.getQueryWrapper(param));
		return R.data(detail);
	}

	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "paramName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "paramKey", value = "参数键名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "paramValue", value = "参数键值", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "分页", notes = "传入param")
	public R<IPage<Param>> list(@ApiIgnore @RequestParam Map<String, Object> param, Query query) {
		IPage<Param> pages = paramService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(param, Param.class));
		return R.data(pages);
	}

	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入param")
	public R submit(@Valid @RequestBody Param param) {
		return R.status(paramService.saveOrUpdate(param));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(paramService.deleteLogic(Func.toLongList(ids)));
	}

}
