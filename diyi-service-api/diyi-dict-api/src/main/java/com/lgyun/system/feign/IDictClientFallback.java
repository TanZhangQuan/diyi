package com.lgyun.system.feign;

import com.lgyun.common.api.R;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author tzq
 * @since 2020/6/6 19:20
 */
@Component
public class IDictClientFallback implements IDictClient {

	@Override
	public R<String> getValue(String code, String dictKey) {
		return R.fail("查询数据失败");
	}

}
