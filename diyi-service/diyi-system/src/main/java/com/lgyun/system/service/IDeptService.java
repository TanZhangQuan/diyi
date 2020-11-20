package com.lgyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.entity.Dept;
import com.lgyun.system.vo.DeptVO;

import java.util.List;

/**
 * 服务类
 *
 * @author tzq
 */
public interface IDeptService extends BaseService<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept);

	/**
	 * 树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 查询部门ID
	 *
	 * @param tenantId
	 * @param deptNames
	 * @return
	 */
	String getDeptIds(String tenantId, String deptNames);

	/**
	 * 查询部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptNames(String deptIds);

}
