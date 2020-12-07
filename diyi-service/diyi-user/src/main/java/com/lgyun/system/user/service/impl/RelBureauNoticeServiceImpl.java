package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.NoticeState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.RelBureauNoticeEntity;
import com.lgyun.system.user.mapper.RelBureauNoticeMapper;
import com.lgyun.system.user.service.IRelBureauNoticeService;
import com.lgyun.system.user.vo.RelBureauNoticeListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 相关局通知管理表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauNoticeServiceImpl extends BaseServiceImpl<RelBureauNoticeMapper, RelBureauNoticeEntity> implements IRelBureauNoticeService {

    @Override
    public R<IPage<RelBureauNoticeListVO>> queryBureauNoticeList(Long relBureauId, NoticeState noticeState, IPage<RelBureauNoticeListVO> page) {

        if (!(NoticeState.PUBLISHED.equals(noticeState)) && !(NoticeState.HAVEREAD.equals(noticeState))) {
            return R.fail("请选择已发布或已阅读状态");
        }

        return R.data(page.setRecords(baseMapper.queryBureauNoticeList(relBureauId, noticeState, page)));
    }

}
