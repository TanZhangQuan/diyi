package com.lgyun.system.user.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;

import java.util.List;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public interface IEnterpriseService extends BaseService<EnterpriseEntity> {

    /**
     * 通过商户名字查询
     */
    List<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName);

    /**
     * 通过商户id查询
     */
    MakerEnterpriseRelationVO getEnterpriseId(Long enterpriseId,Integer difference);

}

