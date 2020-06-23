package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.entity.Tenant;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author liangfeihu
 */
public interface TenantMapper extends BaseMapper<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	List<Tenant> selectTenantPage(IPage page, Tenant tenant);

}
