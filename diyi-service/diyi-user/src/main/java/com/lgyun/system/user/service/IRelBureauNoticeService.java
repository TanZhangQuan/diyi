package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.RelBureauNoticeEntity;
import com.lgyun.system.user.vo.RelBureauNoticeVO;

/**
 * 相关局通知管理表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauNoticeService extends BaseService<RelBureauNoticeEntity> {

    R<IPage<RelBureauNoticeVO>> queryBureauNotice(Long bureauId, IPage<RelBureauNoticeVO> page);
}

