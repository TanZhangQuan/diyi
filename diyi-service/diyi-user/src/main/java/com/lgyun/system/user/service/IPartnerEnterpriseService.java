package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.PartnerEnterpriseEntity;
import com.lgyun.system.user.vo.CooperationEnterprisesListVO;

/**
 * 合伙人-商户关联表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
public interface IPartnerEnterpriseService extends BaseService<PartnerEnterpriseEntity> {

    /**
     * 合伙人匹配商户
     *
     * @param partnerId
     * @param enterpriseId
     * @param matchDesc
     * @return
     */
    R<String> relevancePartnerEnterprise(Long partnerId, Long enterpriseId, String matchDesc);

    /**
     * 查询合伙人-商户数
     *
     * @param partnerId
     * @param enterpriseId
     * @return
     */
    int queryPartnerEnterpriseCount(Long partnerId, Long enterpriseId);

    /**
     * 根据合伙人,商户查询关联
     *
     * @param partnerId
     * @param enterpriseId
     * @return
     */
    PartnerEnterpriseEntity queryByPartnerAndEnterprise(Long partnerId, Long enterpriseId);

    /**
     * 查询合伙人合作商户
     *
     * @param partnerId
     * @param enterpriseName
     * @param page
     * @return
     */
    R<IPage<CooperationEnterprisesListVO>> queryCooperationEnterpriseList(Long partnerId, String enterpriseName, IPage<CooperationEnterprisesListVO> page);

    /**
     * 更改合伙人商户合作关系
     *
     * @param partnerId
     * @param enterpriseId
     * @param cooperateStatus
     * @return
     */
    R<String> updateCooperationStatus(Long partnerId, Long enterpriseId, CooperateStatus cooperateStatus);
}

