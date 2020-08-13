package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.vo.TransactionMonthVO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.service.IEnterpriseProviderService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.EnterpriseStatisticalVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.wrapper.EnterpriseWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseMapper, EnterpriseEntity> implements IEnterpriseService {

    private IMakerEnterpriseService makerEnterpriseService;
    private IEnterpriseProviderService enterpriseProviderService;

    @Override
    public MakerEnterpriseRelationVO getEnterpriseName(String enterpriseName) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getEnterpriseName, enterpriseName);
        EnterpriseEntity enterpriseEntity = baseMapper.selectOne(queryWrapper);
        return EnterpriseWrapper.build().makerEnterpriseRelationVO(enterpriseEntity);
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId) {
        MakerEnterpriseEntity enterpriseIdAndMakerIdLian = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, RelationshipType.RELEVANCE);
        MakerEnterpriseEntity enterpriseIdAndMakerIdZhu = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, RelationshipType.ATTENTION);
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getId, enterpriseId);

        EnterpriseEntity enterpriseEntity = baseMapper.selectOne(queryWrapper);

        MakerEnterpriseRelationVO makerEnterpriseRelationVO = EnterpriseWrapper.build().makerEnterpriseRelationVO(enterpriseEntity);
        if (makerEnterpriseRelationVO == null) {
            return R.fail("商户不存在");
        }

        if (null == enterpriseIdAndMakerIdLian && null != enterpriseIdAndMakerIdZhu) {
            //TODO
            makerEnterpriseRelationVO.setContact1Phone("138********");
            makerEnterpriseRelationVO.setBizLicenceUrl("*");
            makerEnterpriseRelationVO.setLegalPerson("***");
            makerEnterpriseRelationVO.setLegalPersonCard("*********");
            makerEnterpriseRelationVO.setSocialCreditNo("*******");
            makerEnterpriseRelationVO.setContact1Position("********");
            makerEnterpriseRelationVO.setShopUserName("*****");
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.ATTENTION);
            return R.data(makerEnterpriseRelationVO);
        } else if (null != enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu) {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        } else if (null == enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu) {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.NORELATION);
            return R.data(makerEnterpriseRelationVO);
        } else {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        }
    }

    @Override
    public R<EnterpriseStatisticalVO> statistical(Long enterpriseId) {
        return R.data(baseMapper.statistical(enterpriseId));
    }

    @Override
    public R<IPage<ServiceProviderIdNameListVO>> getServiceProviders(Query query, Long enterpriseId) {
        return enterpriseProviderService.getServiceProviderByEnterpriseId(Condition.getPage(query.setDescs("create_time")), enterpriseId, null);
    }

    @Override
    public R<TransactionMonthVO> queryEnterprisePayMoney(Long enterpriseId) {
        return R.data(baseMapper.queryEnterprisePayMoney(enterpriseId));
    }

}
