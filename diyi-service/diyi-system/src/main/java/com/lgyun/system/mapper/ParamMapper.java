package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.entity.Param;
import com.lgyun.system.vo.ParamVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author tzq
 */
public interface ParamMapper extends BaseMapper<Param> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	List<ParamVO> selectParamPage(IPage page, ParamVO param);

}
