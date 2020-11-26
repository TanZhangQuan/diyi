package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.enumeration.WorksheetType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;
import com.lgyun.system.user.vo.MakerEnterpriseDetailYearMonthVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.MakerEnterpriseWebVO;

import java.util.List;
import java.util.Set;

/**
 * Service 接口
 *
 * @author tzq
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
    R<IPage<MakerEnterpriseRelationVO>> selectMakerEnterprisePage(Long makerId, RelationshipType relationshipType, IPage<MakerEnterpriseRelationVO> page);

    /**
     * 查询创客关联商户的编号名称
     *
     * @param page
     * @param makerId
     * @return
     */
    R<IPage<EnterprisesIdNameListVO>> queryRelevanceEnterpriseIdAndNameList(Long makerId, IPage<EnterprisesIdNameListVO> page);

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
    MakerEnterpriseEntity queryMakerEnterprise(Long enterpriseId, Long makerId);

    /**
     * 通过商户id，创客id，关系查询
     *
     * @param enterpriseId
     * @param makerId
     * @param relationshipType
     * @return
     */
    int queryMakerEnterpriseNum(Long enterpriseId, Long makerId, RelationshipType relationshipType);

    /**
     * 批量关联创客
     *
     * @param makerIds
     * @param enterpriseId
     * @return
     */
    R<String> relevanceMakerList(Set<Long> makerIds, Long enterpriseId);

    /**
     * 批量取消创客关联或关注
     *
     * @param makerIds
     * @param enterpriseId
     * @return
     */
    R<String> cancelRelevanceOrAttentionMakerList(Set<Long> makerIds, Long enterpriseId);

    /**
     * 根据商户id查询所有关联的创客
     */
    List<MakerEnterpriseEntity> getEnterpriseId(Long enterpriseId);

    /**
     * 根据商户id查询关联的创客
     *
     * @param page
     * @param enterpriseId
     * @return
     */
    R<IPage<MakerEnterpriseWebVO>> selectEnterpriseMaker(IPage<MakerEnterpriseWebVO> page, Long enterpriseId,String makerName);

    /**
     * 查询关联商户和创客的明细
     *
     * @param page
     * @param makerId
     * @param enterpriseId
     * @param worksheetType
     * @return
     */
    R<IPage<MakerEnterpriseDetailYearMonthVO>> getMakerDetailed(IPage<MakerEnterpriseDetailYearMonthVO> page, Long makerId, Long enterpriseId, WorksheetType worksheetType);

}

