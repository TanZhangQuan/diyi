package com.lgyun.system.feign;

import com.lgyun.common.api.R;
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
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<Dict>> getList(String code) {
		return R.fail("获取数据失败");
	}
}
