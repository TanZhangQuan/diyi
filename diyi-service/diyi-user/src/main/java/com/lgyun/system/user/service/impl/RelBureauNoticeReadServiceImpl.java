package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.RelBureauNoticeReadEntity;
import com.lgyun.system.user.mapper.RelBureauNoticeReadMapper;
import com.lgyun.system.user.service.IRelBureauNoticeReadService;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 相关局通知阅读管理表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauNoticeReadServiceImpl extends BaseServiceImpl<RelBureauNoticeReadMapper, RelBureauNoticeReadEntity> implements IRelBureauNoticeReadService {


    @Override
    public int queryServiceProviderCount(Long relBureauNoticeId) {
        return baseMapper.queryServiceProviderCount(relBureauNoticeId);
    }

    @Override
    public int queryRelBureauNoticeServiceProviderCount(Long relBureauNoticeId, Long serviceProviderWorkerId) {
        QueryWrapper<RelBureauNoticeReadEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RelBureauNoticeReadEntity::getRelBureauNoticeId, relBureauNoticeId)
                .eq(RelBureauNoticeReadEntity::getServicerProviderWorkerId, serviceProviderWorkerId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<IPage<ServiceProviderIdNameListVO>> queryReadServiceProviderList(Long relBureauNoticeId, IPage<ServiceProviderIdNameListVO> page) {
        return R.data(page.setRecords(baseMapper.queryReadServiceProviderList(relBureauNoticeId)));
    }

}
