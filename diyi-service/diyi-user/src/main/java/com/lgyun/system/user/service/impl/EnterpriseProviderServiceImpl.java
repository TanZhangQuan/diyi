package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.EnterpriseProviderEntity;
import com.lgyun.system.user.mapper.EnterpriseProviderMapper;
import com.lgyun.system.user.service.IEnterpriseProviderService;
import com.lgyun.system.user.vo.ProviderListVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseProviderServiceImpl extends BaseServiceImpl<EnterpriseProviderMapper, EnterpriseProviderEntity> implements IEnterpriseProviderService {

    @Override
    public R<IPage<ProviderListVO>> listByEnterpriseId(IPage<ProviderListVO> page, Long enterpriseId) {
        return R.data(page.setRecords(baseMapper.listByEnterpriseId(enterpriseId, page)));
    }
}
