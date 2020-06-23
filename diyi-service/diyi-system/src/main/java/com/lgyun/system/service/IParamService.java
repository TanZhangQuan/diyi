package com.lgyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.entity.Param;
import com.lgyun.system.vo.ParamVO;

/**
 * 服务类
 *
 * @author liangfeihu
 */
public interface IParamService extends BaseService<Param> {

	/***
	 * 自定义分页
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param);

}
