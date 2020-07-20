package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.vo.EnterprisesByWorksheetListVO;
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

    @Override
    public List<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName) {
        List<MakerEnterpriseRelationVO> makerEnterpriseRelationVOs = baseMapper.getEnterpriseName("%"+enterpriseName+"%");
        return makerEnterpriseRelationVOs;
    }

    @Override
    public MakerEnterpriseRelationVO getEnterpriseId(Long enterpriseId,Integer difference) {
        MakerEnterpriseRelationVO makerEnterpriseRelationVO = baseMapper.getEnterpriseId(enterpriseId,difference);
        if(null == makerEnterpriseRelationVO){
            return null;
        }
        if(difference == 0){
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

    @Override
    public R<IPage<EnterprisesByWorksheetListVO>> getEnterprisesByWorksheet(IPage<EnterprisesByWorksheetListVO> page, Long makerId) {
        return R.data(page.setRecords(baseMapper.getEnterprisesByWorksheet(makerId, page)));
    }
}
