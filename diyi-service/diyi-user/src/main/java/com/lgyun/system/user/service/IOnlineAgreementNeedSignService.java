package com.lgyun.system.user.service;


import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.OnlineAgreementNeedSignEntity;
import com.lgyun.system.user.vo.OnlineAgreementNeedSignVO;

import java.util.List;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
public interface IOnlineAgreementNeedSignService extends BaseService<OnlineAgreementNeedSignEntity> {

    /**
     * 查询创客需要签署的授权协议
     *
     * @param makerId
     * @param isContract
     * @return
     */
   R<List<OnlineAgreementNeedSignVO>> getOnlineAgreementNeedSign(Long makerId,Integer isContract);

}

