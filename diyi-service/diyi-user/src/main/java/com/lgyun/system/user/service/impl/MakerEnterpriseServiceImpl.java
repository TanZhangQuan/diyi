package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.exception.CustomException;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.mapper.MakerEnterpriseMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;
import com.lgyun.system.user.vo.MakerEnterpriseDetailYearMonthVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.MakerEnterpriseWebVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
public class MakerEnterpriseServiceImpl extends BaseServiceImpl<MakerEnterpriseMapper, MakerEnterpriseEntity> implements IMakerEnterpriseService {

    @Autowired
    @Lazy
    private IEnterpriseService enterpriseService;

    @Autowired
    @Lazy
    private IMakerService makerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void makerEnterpriseEntitySave(Long enterpriseId, Long makerId) {

        int enterpriseNum = enterpriseService.queryCountById(enterpriseId);
        if (enterpriseNum <= 0) {
            throw new CustomException("商户不存在");
        }

        int makerNum = makerService.queryCountById(makerId);
        if (makerNum <= 0) {
            throw new CustomException("创客不存在");
        }

        MakerEnterpriseEntity makerEnterpriseEntity = queryMakerEnterprise(enterpriseId, makerId);
        if (makerEnterpriseEntity != null) {
            if (!(RelationshipType.RELEVANCE.equals(makerEnterpriseEntity.getRelationshipType()))) {
                makerEnterpriseEntity.setRelationshipType(RelationshipType.RELEVANCE);
                makerEnterpriseEntity.setRelType(EnterpriseMakerRelType.ENTERPRISEREL);
                makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                makerEnterpriseEntity.setFirstCooperation(false);
                makerEnterpriseEntity.setRelMemo("关联");
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
        makerEnterpriseEntity.setCooperationStartTime(new Date());
        makerEnterpriseEntity.setFirstCooperation(true);
        makerEnterpriseEntity.setRelMemo("首次关联");
        save(makerEnterpriseEntity);
    }

    @Override
    public R<IPage<MakerEnterpriseRelationVO>> selectMakerEnterprisePage(Long makerId, RelationshipType relationshipType, IPage<MakerEnterpriseRelationVO> page) {
        return R.data(page.setRecords(baseMapper.selectMakerEnterprisePage(makerId, relationshipType, page)));
    }

    @Override
    public R<IPage<EnterprisesIdNameListVO>> queryRelevanceEnterpriseIdAndNameList(Long makerId, IPage<EnterprisesIdNameListVO> page) {
        return R.data(page.setRecords(baseMapper.queryRelevanceEnterpriseIdAndNameList(makerId, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addOrCancelfollow(Long enterpriseId, Long makerId, Integer attribute) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getMakerId, makerId)
                .eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId);
        MakerEnterpriseEntity makerEnterpriseEntity = baseMapper.selectOne(queryWrapper);

        if (attribute == 1 && null == makerEnterpriseEntity) {
            return R.success("取消成功");
        }
        if(attribute == 1){
            makerEnterpriseEntity.setRelationshipType(RelationshipType.NORELATION);

            saveOrUpdate(makerEnterpriseEntity);
            return R.success("取消成功");
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
        }else{
            makerEnterpriseEntity.setRelationshipType(RelationshipType.ATTENTION);
            makerEnterpriseEntity.setIsDeleted(0);
        }
        saveOrUpdate(makerEnterpriseEntity);

        return R.success("成功");
    }

    @Override
    public MakerEnterpriseEntity queryMakerEnterprise(Long enterpriseId, Long makerId) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getMakerId, makerId)
                .eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int queryMakerEnterpriseNum(Long enterpriseId, Long makerId, RelationshipType relationshipType) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId)
                .eq(MakerEnterpriseEntity::getMakerId, makerId)
                .eq(MakerEnterpriseEntity::getRelationshipType, relationshipType);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> relevanceMakerList(Set<Long> makerIds, Long enterpriseId) {

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
    @Transactional(rollbackFor = Exception.class)
    public R<String> cancelRelevanceOrAttentionMakerList(Set<Long> makerIds, Long enterpriseId) {
        //取消创客关联或关注
        makerIds.forEach(makerId -> {
            try {
                MakerEnterpriseEntity makerEnterpriseEntity = queryMakerEnterprise(enterpriseId, makerId);
                if (makerEnterpriseEntity != null) {
                    makerEnterpriseEntity.setRelationshipType(RelationshipType.NORELATION);
                    makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATESTOP);
                    makerEnterpriseEntity.setCooperationEndTime(new Date());
                    makerEnterpriseEntity.setRelMemo("取消关联关注");
                    updateById(makerEnterpriseEntity);
                }
            } catch (Exception e) {
                log.error("取消创客关联关注异常", e);
            }
        });

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
    public R<IPage<MakerEnterpriseWebVO>> selectEnterpriseMaker(IPage<MakerEnterpriseWebVO> page, Long enterpriseId,String makerName) {
        List<MakerEnterpriseWebVO> makerEnterpriseWebVOS = baseMapper.selectEnterpriseMaker(enterpriseId,makerName, page);
        for (MakerEnterpriseWebVO makerEnterpriseWebVO : makerEnterpriseWebVOS) {
            if (SignState.SIGNED.equals(makerEnterpriseWebVO.getJoinSignState())) {
                makerEnterpriseWebVO.setProtocolAuthentication(CertificationState.CERTIFIED);
            }
        }
        return R.data(page.setRecords(makerEnterpriseWebVOS));
    }

    @Override
    public R<IPage<MakerEnterpriseDetailYearMonthVO>> getMakerDetailed(IPage<MakerEnterpriseDetailYearMonthVO> page, Long makerId, Long enterpriseId, WorksheetType worksheetType) {
        if (worksheetType.equals(WorksheetType.CROWDSOURCED)) {
            return R.success("成功");
        }
        return R.data(page.setRecords(baseMapper.getMakerDetailed(makerId, enterpriseId, page)));
    }

}
