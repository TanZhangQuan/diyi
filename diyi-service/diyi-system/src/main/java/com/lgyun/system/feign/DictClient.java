package com.lgyun.system.feign;

import com.lgyun.common.api.R;
import com.lgyun.system.service.IDictService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 字典服务Feign实现类
 *
 * @author liangfeihu
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class DictClient implements IDictClient {

	IDictService dictService;

	@Override
	@GetMapping(API_PREFIX + "/getValue")
	public R<String> getValue(String code, String dictKey) {
		return R.data(dictService.queryDictValue(code, dictKey));
	}

}
