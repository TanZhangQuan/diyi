package com.lgyun.system.user.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service 实现
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
    public List<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName) {
        List<MakerEnterpriseRelationVO> makerEnterpriseRelationVOs = baseMapper.getEnterpriseName(enterpriseName);
        return makerEnterpriseRelationVOs;
    }

    @Override
    public R getEnterpriseId(Long enterpriseId,Long makerId) {
        MakerEnterpriseEntity enterpriseIdAndMakerIdLian = makerEnterpriseService.getEnterpriseIdAndMakerId(enterpriseId, makerId, 0);
        MakerEnterpriseEntity enterpriseIdAndMakerIdZhu = makerEnterpriseService.getEnterpriseIdAndMakerId(enterpriseId, makerId, 1);
        MakerEnterpriseRelationVO makerEnterpriseRelationVO = baseMapper.getEnterpriseId(enterpriseId);
        if(null == enterpriseIdAndMakerIdLian && null != enterpriseIdAndMakerIdZhu){
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
        } else if(null != enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu){
           makerEnterpriseRelationVO.setRelationshipType(0);
           return R.data(makerEnterpriseRelationVO);
       } else if(null == enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu){
           makerEnterpriseRelationVO.setRelationshipType(2);
           return R.data(makerEnterpriseRelationVO);
       }else {
            makerEnterpriseRelationVO.setRelationshipType(0);
            return R.data(makerEnterpriseRelationVO);
        }
    }

}
