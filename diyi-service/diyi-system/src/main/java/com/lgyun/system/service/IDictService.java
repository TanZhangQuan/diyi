package com.lgyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.vo.DictVO;

import java.util.List;

/**
 * 服务类
 *
 * @author liangfeihu
 */
public interface IDictService extends IService<Dict> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dict
	 * @return
	 */
	IPage<DictVO> selectDictPage(IPage<DictVO> page, DictVO dict);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<DictVO> tree();

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
	 * 新增或修改
	 * @param dict
	 * @return
	 */
	boolean submit(Dict dict);


	/**
	 * 查询字典表
	 *
	 * @param parentId
	 * @return
	 */
	List<Dict> getParentList(Long parentId);

	/**
	 * 查询字典表
	 * @param code
	 * @return
	 */
	Dict getDict(String code);

}
