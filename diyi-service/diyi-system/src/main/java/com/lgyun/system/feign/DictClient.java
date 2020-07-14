package com.lgyun.system.feign;

import com.lgyun.system.entity.Dict;
import com.lgyun.system.service.IDictService;
import lombok.AllArgsConstructor;
import com.lgyun.common.api.R;
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

}
