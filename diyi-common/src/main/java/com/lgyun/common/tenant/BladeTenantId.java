package com.lgyun.common.tenant;

import com.lgyun.common.tool.RandomType;
import com.lgyun.common.tool.StringUtil;

/**
 * blade租户id生成器
 *
 * @author Chill
 */
public class BladeTenantId implements TenantId {
	@Override
	public String generate() {
		return StringUtil.random(6, RandomType.INT);
	}
}
