package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.system.order.vo.SelfHelpInvoiceSerProVO;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.*;

import java.util.List;
import java.util.Set;

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
     * 根据创客ID, 两者关系 查询商户
     *
     * @param page
     * @param makerId
     * @param relationshipType
     * @return
     */
    IPage<MakerEnterpriseRelationVO> selectMakerEnterprisePage(IPage<MakerEnterpriseRelationVO> page, Long makerId, RelationshipType relationshipType);

    /**
     * 根据创客ID查询关联商户
     *
     * @param page
     * @param makerId
     * @return
     */
    R<IPage<EnterprisesIdNameListVO>> findEnterpriseIdNameByMakerId(IPage<EnterprisesIdNameListVO> page, Long makerId);

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
    MakerEnterpriseEntity getEnterpriseIdAndMakerIdAndRelationshipType(Long enterpriseId, Long makerId, RelationshipType relationshipType);

    /**
     * 根获取当前商户的所有关联或关注创客
     *
     * @param page
     * @param enterpriseId
     * @param relationshipType
     * @param keyword
     * @return
     */
    R<IPage<RelMakerListVO>> getEnterpriseMakers(IPage<RelMakerListVO> page, Long enterpriseId, RelationshipType relationshipType, String keyword);

    /**
     * 批量关联创客
     *
     * @param makerIds
     * @param enterpriseId
     * @return
     */
    R<String> relMakers(Set<Long> makerIds, Long enterpriseId);

    /**
     * 批量取消创客关联或关注
     *
     * @param makerIds
     * @param relationshipType
     * @param enterpriseId
     * @return
     */
    R<String> cancelRelMakers(Set<Long> makerIds, RelationshipType relationshipType, Long enterpriseId);

    /**
     * 获取当前服务商的自主开票
     *
     * @param page
     * @param serviceProviderId
     * @return
     */
    R<IPage<SelfHelpInvoiceSerProVO>> getSelfHelpInvoiceByServiceProviderId(IPage<SelfHelpInvoiceSerProVO> page, Long serviceProviderId);

    /**
     * 根据商户id查询所有关联的创客
     */
    List<MakerEnterpriseEntity> getEnterpriseId(Long enterpriseId);

    /**
     *
     */
    R<IPage<MakerEnterpriseWebVO>> selectEnterpriseMaker(IPage<MakerEnterpriseWebVO> page, Long enterpriseId);
}

