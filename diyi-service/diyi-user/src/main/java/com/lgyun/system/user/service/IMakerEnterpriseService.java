package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.RelEnterpriseMakerVO;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public interface IMakerEnterpriseService extends BaseService<MakerEnterpriseEntity> {

    /**
     * 添加创客商户关联
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    void makerEnterpriseEntitySave(Long enterpriseId, Long makerId);

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
    R<String> addOrCancelfollow(Long enterpriseId, Long makerId, Integer attribute);

    /**
     * 通过商户id和创客id查询
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    MakerEnterpriseEntity getEnterpriseIdAndMakerId(Long enterpriseId, Long makerId);

    /**
     * 通过商户id，创客id，关系查询
     *
     * @param enterpriseId
     * @param makerId
     * @param relationshipType
     * @return
     */
    MakerEnterpriseEntity getEnterpriseIdAndMakerIdAndRelationshipType(Long enterpriseId, Long makerId, Integer relationshipType);

    /**
     * 获取关注当前商户的所有创客
     *
     * @param page
     * @param enterpriseId
     * @return
     */
    R<IPage<RelEnterpriseMakerVO>> getRelEnterpriseMaker(IPage<RelEnterpriseMakerVO> page, Long enterpriseId);

}

