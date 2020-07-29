package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.exception.ServiceException;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
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

    @Override
    public EnterpriseEntity current(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            throw new ServiceException("商户未登陆");
        }

        EnterpriseEntity enterpriseEntity = findByUserId(bladeUser.getUserId());
        if (enterpriseEntity == null) {
            throw new ServiceException("商户未注册");
        }

        if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
            throw new ServiceException("创客账户状态非正常，请联系客服");
        }

        return enterpriseEntity;
    }

    @Override
    public EnterpriseEntity findByUserId(Long userId) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public MakerEnterpriseRelationVO getEnterpriseName(String enterpriseName) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getEnterpriseName, enterpriseName);

        EnterpriseEntity enterpriseEntity = baseMapper.selectOne(queryWrapper);

        return EnterpriseWrapper.build().makerEnterpriseRelationVO(enterpriseEntity);
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId) {
        MakerEnterpriseEntity enterpriseIdAndMakerIdLian = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, 0);
        MakerEnterpriseEntity enterpriseIdAndMakerIdZhu = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, 1);

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
            makerEnterpriseRelationVO.setRelationshipType(1);
            return R.data(makerEnterpriseRelationVO);
        } else if (null != enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu) {
            makerEnterpriseRelationVO.setRelationshipType(0);
            return R.data(makerEnterpriseRelationVO);
        } else if (null == enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu) {
            makerEnterpriseRelationVO.setRelationshipType(2);
            return R.data(makerEnterpriseRelationVO);
        } else {
            makerEnterpriseRelationVO.setRelationshipType(0);
            return R.data(makerEnterpriseRelationVO);
        }
    }

}
