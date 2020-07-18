package com.lgyun.system.feign;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.dto.DictDTO;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.service.IDictService;
import lombok.AllArgsConstructor;
import com.lgyun.common.api.R;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 字典服务Feign实现类
 *
 * @author liangfeihu
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class DictClient implements IDictClient {

	IDictService service;

	@Override
	@GetMapping(API_PREFIX + "/getValue")
	public R<String> getValue(String code, Integer dictKey) {
		return R.data(service.getValue(code, dictKey));
	}

	@Override
	@GetMapping(API_PREFIX + "/getList")
	public R<List<Dict>> getList(String code) {
		return R.data(service.getList(code));
	}

	@Override
	public R<List<Dict>> getParentList(Long parentId) {
		return R.data(service.getParentList(parentId));
	}

	@Override
	public R saveDict(DictDTO dictDTO) {
		Dict dict = new Dict();
		BeanUtil.copy(dictDTO, dict);
		return R.data(service.save(dict));
	}

	@Override
	public Dict getDict(String code) {
		return service.getDict(code);
	}

}
