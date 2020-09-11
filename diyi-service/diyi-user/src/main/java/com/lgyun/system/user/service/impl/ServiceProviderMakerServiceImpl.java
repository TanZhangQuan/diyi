package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.ServiceProviderMakerEntity;
import com.lgyun.system.user.mapper.ServiceProviderMakerMapper;
import com.lgyun.system.user.service.IServiceProviderMakerService;
import com.lgyun.system.user.vo.RelMakerListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 服务商创客关联表 Service 实现
 *
 * @author tzq
 * @since 2020-08-19 16:01:29
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderMakerServiceImpl extends BaseServiceImpl<ServiceProviderMakerMapper, ServiceProviderMakerEntity> implements IServiceProviderMakerService {

    @Override
    public R<IPage<RelMakerListVO>> getServiceProviderMakers(IPage<RelMakerListVO> page, Long serviceProviderId, String keyword) {
        return R.data(page.setRecords(baseMapper.getServiceProviderMakers(serviceProviderId, keyword, page)));
    }
}
