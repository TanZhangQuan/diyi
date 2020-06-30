package com.lgyun.desk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.desk.entity.Notice;
import com.lgyun.desk.mapper.NoticeMapper;
import com.lgyun.desk.service.INoticeService;
import org.springframework.stereotype.Service;

/**
 * Notice 服务实现类
 *
 * @author liangfeihu
 * @since 2020/6/30 11:24
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

	@Override
	public IPage<Notice> selectNoticePage(IPage<Notice> page, Notice notice) {
		return page.setRecords(this.baseMapper.selectNoticePage(page, notice));
	}

}
