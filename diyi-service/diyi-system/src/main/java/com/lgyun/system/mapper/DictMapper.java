package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.vo.DictVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author liangfeihu
 */
public interface DictMapper extends BaseMapper<Dict> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dict
	 * @return
	 */
	List<DictVO> selectDictPage(IPage page, DictVO dict);

	/**
	 * 查询字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	String getValue(String code, Integer dictKey);

	/**
	 * 查询字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	List<Dict> getList(String code);

	/**
	 * 查询树形节点
	 *
	 * @return
	 */
	List<DictVO> tree();

	/**
	 * 查询字典表
	 *
	 * @param parentId
	 * @return
	 */
	List<Dict> getParentList(Long parentId);

	Dict getDict(String code);

}
