package com.lgyun.desk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.desk.entity.Notice;

/**
 * Notice 服务类
 *
 * @author liangfeihu
 * @since 2020/6/30 11:23
 */
public interface INoticeService extends BaseService<Notice> {

	/**
	 * 自定义分页
	 * @param page
	 * @param notice
	 * @return
	 */
	IPage<Notice> selectNoticePage(IPage<Notice> page, Notice notice);

}
