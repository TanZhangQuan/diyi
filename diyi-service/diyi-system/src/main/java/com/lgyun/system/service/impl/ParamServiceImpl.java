package com.lgyun.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.entity.Param;
import com.lgyun.system.mapper.ParamMapper;
import com.lgyun.system.service.IParamService;
import com.lgyun.system.vo.ParamVO;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author tzq
 */
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param) {
		return page.setRecords(baseMapper.selectParamPage(page, param));
	}

}
