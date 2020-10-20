package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailProviderVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceSerProVO;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.mapper.MakerEnterpriseMapper;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.MakerEnterpriseWebVO;
import com.lgyun.system.user.vo.RelMakerListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class MakerEnterpriseServiceImpl extends BaseServiceImpl<MakerEnterpriseMapper, MakerEnterpriseEntity> implements IMakerEnterpriseService {

    @Override
    public void makerEnterpriseEntitySave(Long enterpriseId, Long makerId) {
        MakerEnterpriseEntity makerEnterpriseEntity = getEnterpriseIdAndMakerId(enterpriseId, makerId);
        if (makerEnterpriseEntity != null) {
            if (!(RelationshipType.RELEVANCE.equals(makerEnterpriseEntity.getRelationshipType()) && CooperateStatus.COOPERATING.equals(makerEnterpriseEntity.getCooperateStatus()))) {
                makerEnterpriseEntity.setRelationshipType(RelationshipType.RELEVANCE);
                makerEnterpriseEntity.setFirstCooperation(false);
                makerEnterpriseEntity.setRelMemo("关联");
                makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                updateById(makerEnterpriseEntity);
                return;
            } else {
                return;
            }
        }

        makerEnterpriseEntity = new MakerEnterpriseEntity();
        makerEnterpriseEntity.setMakerId(makerId);
        makerEnterpriseEntity.setEnterpriseId(enterpriseId);
        makerEnterpriseEntity.setRelationshipType(RelationshipType.RELEVANCE);
        makerEnterpriseEntity.setRelType(EnterpriseMakerRelType.ENTERPRISEREL);
        makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
        makerEnterpriseEntity.setCooperationStartTime(new Date());
        makerEnterpriseEntity.setFirstCooperation(true);
        makerEnterpriseEntity.setRelMemo("关联");
        save(makerEnterpriseEntity);
    }

    @Override
    public IPage<MakerEnterpriseRelationVO> selectMakerEnterprisePage(IPage<MakerEnterpriseRelationVO> page, Long makerId, RelationshipType relationshipType) {
        return page.setRecords(baseMapper.selectMakerEnterprisePage(makerId, relationshipType, page));
    }

    @Override
    public R<IPage<EnterprisesIdNameListVO>> getEnterprisesByWorksheet(IPage<EnterprisesIdNameListVO> page, Long makerId) {
        return R.data(page.setRecords(baseMapper.getEnterprisesByWorksheet(makerId, page)));
    }

    @Override
    public R<IPage<EnterprisesIdNameListVO>> findEnterpriseIdNameByMakerId(IPage<EnterprisesIdNameListVO> page, Long makerId) {
        return R.data(page.setRecords(baseMapper.findEnterpriseIdNameByMakerId(makerId, page)));
    }

    @Override
    public R<String> addOrCancelfollow(Long enterpriseId, Long makerId, Integer attribute) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getMakerId, makerId)
                .eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId)
                .eq(MakerEnterpriseEntity::getRelationshipType, 1);
        MakerEnterpriseEntity makerEnterpriseEntity = baseMapper.selectOne(queryWrapper);

        if (attribute == 1 && null == makerEnterpriseEntity) {
            return R.fail("取消成功1");
        }
        if (null == makerEnterpriseEntity) {
            makerEnterpriseEntity = new MakerEnterpriseEntity();
            makerEnterpriseEntity.setMakerId(makerId);
            makerEnterpriseEntity.setEnterpriseId(enterpriseId);
            makerEnterpriseEntity.setRelationshipType(RelationshipType.ATTENTION);
            makerEnterpriseEntity.setRelType(EnterpriseMakerRelType.MAKERREL);
            makerEnterpriseEntity.setCooperationStartTime(new Date());
            makerEnterpriseEntity.setFirstCooperation(true);
            makerEnterpriseEntity.setRelMemo("关注");
        }
        saveOrUpdate(makerEnterpriseEntity);

        return R.success("成功");
    }

    @Override
    public MakerEnterpriseEntity getEnterpriseIdAndMakerId(Long enterpriseId, Long makerId) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getMakerId, makerId)
                .eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public MakerEnterpriseEntity getEnterpriseIdAndMakerIdAndRelationshipType(Long enterpriseId, Long makerId, RelationshipType relationshipType) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getMakerId, makerId)
                .eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId)
                .eq(MakerEnterpriseEntity::getRelationshipType, relationshipType)
                .eq(MakerEnterpriseEntity::getCooperateStatus, CooperateStatus.COOPERATING);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<RelMakerListVO>> getEnterpriseMakerList(IPage<RelMakerListVO> page, Long enterpriseId, RelationshipType relationshipType, CertificationState certificationState, String keyword) {
        return R.data(page.setRecords(baseMapper.getEnterpriseMakerList(enterpriseId, relationshipType, certificationState, keyword, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> relMakers(Set<Long> makerIds, Long enterpriseId) {

        makerIds.forEach(makerId -> {
            try {
                //关联创客
                makerEnterpriseEntitySave(enterpriseId, makerId);
            } catch (Exception e) {
                log.error("关联创客异常", e);
            }
        });

        return R.success("关联成功");
    }

    @Override
    public R<String> cancelRelMakers(Set<Long> makerIds, RelationshipType relationshipType, Long enterpriseId) {

        switch (relationshipType) {

            case ATTENTION:
                //取消创客关注
                makerIds.forEach(makerId -> {
                    try {
                        MakerEnterpriseEntity makerEnterpriseEntity = getEnterpriseIdAndMakerId(enterpriseId, makerId);
                        if (makerEnterpriseEntity != null) {
                            if (RelationshipType.ATTENTION.equals(makerEnterpriseEntity.getRelationshipType())) {
                                makerEnterpriseEntity.setRelationshipType(RelationshipType.NORELATION);
                                makerEnterpriseEntity.setRelMemo("");
                                updateById(makerEnterpriseEntity);
                            }
                        }
                    } catch (Exception e) {
                        log.error("取消创客关注异常", e);
                    }
                });
                break;

            case RELEVANCE:
                //取消创客关联
                makerIds.forEach(makerId -> {
                    try {
                        MakerEnterpriseEntity makerEnterpriseEntity = getEnterpriseIdAndMakerId(enterpriseId, makerId);
                        if (makerEnterpriseEntity != null) {
                            if (RelationshipType.RELEVANCE.equals(makerEnterpriseEntity.getRelationshipType()) && CooperateStatus.COOPERATING.equals(makerEnterpriseEntity.getCooperateStatus())) {
                                makerEnterpriseEntity.setRelationshipType(RelationshipType.NORELATION);
                                makerEnterpriseEntity.setRelMemo("");
                                makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATESTOP);
                                updateById(makerEnterpriseEntity);
                            }
                        }
                    } catch (Exception e) {
                        log.error("取消创客关联异常", e);
                    }
                });
                break;

            default:
                return R.fail("创客商户关系有误");
        }

        return R.success("操作成功");
    }

    @Override
    public List<MakerEnterpriseEntity> getEnterpriseId(Long enterpriseId) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getRelationshipType, RelationshipType.RELEVANCE)
                .eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId)
                .eq(MakerEnterpriseEntity::getCooperateStatus, CooperateStatus.COOPERATING);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public R<IPage<MakerEnterpriseWebVO>> selectEnterpriseMaker(IPage<MakerEnterpriseWebVO> page, Long enterpriseId) {
        List<MakerEnterpriseWebVO> makerEnterpriseWebVOS = baseMapper.selectEnterpriseMaker(enterpriseId, page);
        for (MakerEnterpriseWebVO makerEnterpriseWebVO : makerEnterpriseWebVOS) {
            if (SignState.SIGNED.equals(makerEnterpriseWebVO.getEmpowerSignState()) && SignState.SIGNED.equals(makerEnterpriseWebVO.getJoinSignState())) {
                makerEnterpriseWebVO.setProtocolAuthentication(CertificationState.CERTIFIED);
            }
        }
        return R.data(page.setRecords(makerEnterpriseWebVOS));
    }

    @Override
    public R<IPage<SelfHelpInvoiceDetailProviderVO>> getSelfHelpInvoiceDetails(IPage<SelfHelpInvoiceDetailProviderVO> page, Long selfHelpvoiceId) {
        return R.data(page.setRecords(baseMapper.getSelfHelpInvoiceDetails(selfHelpvoiceId, page)));
    }

    @Override
    public R<IPage<SelfHelpInvoiceSerProVO>> getSelfHelpInvoiceByServiceProviderId(IPage<SelfHelpInvoiceSerProVO> page, String keyword, Long serviceProviderId) {
        return R.data(page.setRecords(baseMapper.getSelfHelpInvoiceByServiceProviderId(serviceProviderId, keyword, page)));
    }

}
