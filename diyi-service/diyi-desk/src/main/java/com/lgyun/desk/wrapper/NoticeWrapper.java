package com.lgyun.desk.wrapper;

import com.lgyun.common.api.R;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.desk.entity.Notice;
import com.lgyun.desk.vo.NoticeVO;
import com.lgyun.system.feign.IDictClient;

/**
 * Notice包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020/6/30 11:22
 */
public class NoticeWrapper extends BaseEntityWrapper<Notice, NoticeVO> {

	private static IDictClient dictClient;

	static {
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static NoticeWrapper build() {
		return new NoticeWrapper();
	}

	@Override
	public NoticeVO entityVO(Notice notice) {
		NoticeVO noticeVO = BeanUtil.copy(notice, NoticeVO.class);
		R<String> dict = dictClient.getValue("notice", noticeVO.getCategory());
		if (dict.isSuccess()) {
			String categoryName = dict.getData();
			noticeVO.setCategoryName(categoryName);
		}
		return noticeVO;
	}

}
