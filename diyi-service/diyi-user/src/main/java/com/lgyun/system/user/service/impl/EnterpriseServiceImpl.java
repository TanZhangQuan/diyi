package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, EnterpriseEntity> implements IEnterpriseService {
    private Logger logger = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

    @Override
    public List<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName) {
        List<MakerEnterpriseRelationVO> makerEnterpriseRelationVOs = baseMapper.getEnterpriseName(enterpriseName);
        return makerEnterpriseRelationVOs;
    }

    @Override
    public MakerEnterpriseRelationVO getEnterpriseId(Long enterpriseId,Integer difference) {
        MakerEnterpriseRelationVO makerEnterpriseRelationVO = baseMapper.getEnterpriseId(enterpriseId);
        if(difference == 1){
            return makerEnterpriseRelationVO;
        }else{
            makerEnterpriseRelationVO.setContact1Phone("138********");
            makerEnterpriseRelationVO.setBizLicenceUrl("*");
            makerEnterpriseRelationVO.setLegalPerson("***");
            makerEnterpriseRelationVO.setLegalPersonCard("*********");
            makerEnterpriseRelationVO.setSocialCreditNo("*******");
            makerEnterpriseRelationVO.setContact1Position("********");
            makerEnterpriseRelationVO.setShopUserName("*****");
            return makerEnterpriseRelationVO;
        }


    }
}
