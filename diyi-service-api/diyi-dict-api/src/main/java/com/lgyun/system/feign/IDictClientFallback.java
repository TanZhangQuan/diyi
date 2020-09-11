package com.lgyun.system.feign;

import com.lgyun.common.api.R;
import com.lgyun.system.dto.DictDTO;
import com.lgyun.system.entity.Dict;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author liangfeihu
 * @since 2020/6/6 19:20
 */
@Component
public class IDictClientFallback implements IDictClient {
	@Override
	public R<String> getValue(String code, Integer dictKey) {
		return R.fail("查询数据失败");
	}

	@Override
	public R<List<Dict>> getList(String code) {
		return R.fail("查询数据失败");
	}

	@Override
	public R<List<Dict>> getParentList(Long parentId) {
		return R.fail("查询数据失败");
	}

	@Override
	public R saveDict(DictDTO dictDTO) {
		return R.fail("保存数据失败");
	}

	@Override
	public Dict getDict(String code) {
		return null;
	}

}
