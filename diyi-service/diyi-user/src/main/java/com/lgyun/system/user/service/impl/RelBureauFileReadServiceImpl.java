package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.RelBureauFileReadEntity;
import com.lgyun.system.user.mapper.RelBureauFileReadMapper;
import com.lgyun.system.user.service.IRelBureauFileReadService;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 监管文件阅读记录：相关局监管文件阅读管理表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauFileReadServiceImpl extends BaseServiceImpl<RelBureauFileReadMapper, RelBureauFileReadEntity> implements IRelBureauFileReadService {

    @Override
    public int queryServiceProviderCount(Long RelBureauFilesId) {
        return baseMapper.queryServiceProviderCount(RelBureauFilesId);
    }

    @Override
    public int queryRelBureauFileServiceProviderCount(Long relBureauFileId, Long serviceProviderWorkerId) {
        QueryWrapper<RelBureauFileReadEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RelBureauFileReadEntity::getRelBureauFileId, relBureauFileId)
                .eq(RelBureauFileReadEntity::getServicerProviderWorkerId, serviceProviderWorkerId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<IPage<ServiceProviderIdNameListVO>> queryReadServiceProviderList(Long relBureauFileId, IPage<ServiceProviderIdNameListVO> page) {
        return R.data(page.setRecords(baseMapper.queryReadServiceProviderList(relBureauFileId)));
    }

}
