package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.entity.Dept;
import com.lgyun.system.vo.DeptVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author tzq
 */
public interface DeptMapper extends BaseMapper<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	List<DeptVO> selectDeptPage(IPage page, DeptVO dept);

	/**
	 * 查询树形节点
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 查询部门名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptNames(Long[] ids);

}
