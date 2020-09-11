package com.lgyun.system.controller;


import com.lgyun.system.service.IDictService;
import com.lgyun.system.wrapper.DictWrapper;
import io.swagger.annotations.*;

import lombok.AllArgsConstructor;
import com.lgyun.common.api.R;
import com.lgyun.common.ctrl.BladeController;
import com.lgyun.common.node.INode;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;

import com.lgyun.system.entity.Dict;
import com.lgyun.system.vo.DictVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.lgyun.common.cache.CacheNames.DICT_LIST;
import static com.lgyun.common.cache.CacheNames.DICT_VALUE;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "字典", tags = "字典")
public class DictController extends BladeController {

	private IDictService dictService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入dict")
	public R<DictVO> detail(Dict dict) {
		Dict detail = dictService.getOne(Condition.getQueryWrapper(dict));
		return R.data(DictWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "列表", notes = "传入dict")
	public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> dict) {
		@SuppressWarnings("unchecked")
		List<Dict> list = dictService.list(Condition.getQueryWrapper(dict, Dict.class).lambda().orderByAsc(Dict::getSort));
		return R.data(DictWrapper.build().listNodeVO(list));
	}

	/**
	 * 查询字典树形结构
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DictVO>> tree() {
		List<DictVO> tree = dictService.tree();
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入dict")
	public R submit(@Valid @RequestBody Dict dict) {
		return R.status(dictService.submit(dict));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@CacheEvict(cacheNames = {DICT_LIST, DICT_VALUE}, allEntries = true)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(dictService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 查询字典
	 *
	 * @return
	 */
	@GetMapping("/dictionary")
	@ApiOperation(value = "查询字典", notes = "查询字典")
	public R<List<Dict>> dictionary(String code) {
		List<Dict> tree = dictService.getList(code);
		return R.data(tree);
	}

}
