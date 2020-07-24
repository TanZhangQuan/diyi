package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public interface IMakerEnterpriseService extends BaseService<MakerEnterpriseEntity> {

    /**
     * 查询关联商户和关注商户
     *
     * @param page
     * @param makerId
     * @param relationshipType
     * @return
     */
    IPage<MakerEnterpriseRelationVO> selectMakerEnterprisePage(IPage<MakerEnterpriseRelationVO> page, Long makerId, Integer relationshipType);

    /**
     * 添加和取消关注 relationshipType = 1,取消 2添加
     *
     * @param enterpriseId
     * @param makerId
     * @param attribute
     * @return
     */
    R addOrCancelfollow(Long enterpriseId, Long makerId, Integer attribute);

    /**
     * 通过商户id和创客id查询
     *
     * @param enterpriseId
     * @param makerId
     * @param relationshipType
     * @return
     */
    MakerEnterpriseEntity getEnterpriseIdAndMakerId(Long enterpriseId, Long makerId, Integer relationshipType);


}

