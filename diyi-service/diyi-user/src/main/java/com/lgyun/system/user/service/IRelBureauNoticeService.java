package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.NoticeState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.RelBureauNoticeEntity;
import com.lgyun.system.user.vo.RelBureauNoticeListVO;

/**
 * 相关局通知管理表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauNoticeService extends BaseService<RelBureauNoticeEntity> {

    /**
     * 查询相关局通知
     *
     * @param relBureauId
     * @param noticeState
     * @param page
     * @return
     */
    R<IPage<RelBureauNoticeListVO>> queryBureauNoticeList(Long relBureauId, NoticeState noticeState, IPage<RelBureauNoticeListVO> page);

}

