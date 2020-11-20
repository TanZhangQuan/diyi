package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.system.user.vo.RelBureauNoticeVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.mapper.RelBureauNoticeMapper;
import com.lgyun.system.user.entity.RelBureauNoticeEntity;
import com.lgyun.system.user.service.IRelBureauNoticeService;

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

    /**
     * 分页查询税务局管理的通知
     * @param bureauId
     * @param page
     * @return
     */
    @Override
    public R<IPage<RelBureauNoticeVO>> queryBureauNotice(Long bureauId, IPage<RelBureauNoticeVO> page) {
        return R.data(page.setRecords(baseMapper.queryTaxBureauNotice(bureauId, page)));
    }
}
