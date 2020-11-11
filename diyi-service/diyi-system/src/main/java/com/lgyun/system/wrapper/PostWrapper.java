package com.lgyun.system.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.entity.Post;
import com.lgyun.system.service.IDictService;
import com.lgyun.system.vo.PostVO;

import java.util.Objects;

/**
 * 岗位表包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 */
public class PostWrapper extends BaseEntityWrapper<Post, PostVO> {

	private static IDictService dictService;

	static {
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static PostWrapper build() {
		return new PostWrapper();
	}

	@Override
	public PostVO entityVO(Post post) {
		PostVO postVO = Objects.requireNonNull(BeanUtil.copy(post, PostVO.class));
		String categoryName = dictService.queryDictValue("post_category", String.valueOf(post.getCategory()));
		postVO.setCategoryName(categoryName);
		return postVO;
	}

}
