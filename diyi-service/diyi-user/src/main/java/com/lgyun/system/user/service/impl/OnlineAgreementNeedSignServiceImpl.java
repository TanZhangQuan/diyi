package com.lgyun.system.user.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.OnlineAgreementNeedSignEntity;
import com.lgyun.system.user.mapper.OnlineAgreementNeedSignMapper;
import com.lgyun.system.user.service.IOnlineAgreementNeedSignService;
import com.lgyun.system.user.vo.OnlineAgreementNeedSignVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;

import java.util.List;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Slf4j
@Service
@AllArgsConstructor
public class OnlineAgreementNeedSignServiceImpl extends BaseServiceImpl<OnlineAgreementNeedSignMapper, OnlineAgreementNeedSignEntity> implements IOnlineAgreementNeedSignService {

    @Override
    public R<List<OnlineAgreementNeedSignVO>> getOnlineAgreementNeedSign(Long makerId) {
        List<OnlineAgreementNeedSignVO> onlineAgreementNeedSign = baseMapper.getOnlineAgreementNeedSign(makerId);
        return R.data(onlineAgreementNeedSign);
    }
}
