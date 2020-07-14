package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.RelType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.mapper.MakerEnterpriseMapper;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class MakerEnterpriseServiceImpl extends BaseServiceImpl<MakerEnterpriseMapper, MakerEnterpriseEntity> implements IMakerEnterpriseService {

    @Override
    public List<MakerEnterpriseEntity> getMakerId(Long makerId) {
        return baseMapper.getMakerId(makerId);
    }

    @Override
    public IPage<MakerEnterpriseRelationVO> selectMakerEnterprisePage(IPage page, Long makerId, Integer relationshipType) {
        return  page.setRecords(baseMapper.selectMakerEnterprisePage(page, makerId,relationshipType));
    }

    @Override
    public R addOrCancelfollow(Long enterpriseId, Long makerId,Integer attribute) {
        MakerEnterpriseEntity makerEnterpriseEntity = baseMapper.selectCancelfollow(enterpriseId, makerId);
        if(attribute == 1 && null == makerEnterpriseEntity){
            return R.fail("取消成功1");
        }
        if(null == makerEnterpriseEntity){
            makerEnterpriseEntity = new MakerEnterpriseEntity();
            makerEnterpriseEntity.setMakerId(makerId);
            makerEnterpriseEntity.setEnterpriseId(enterpriseId);
            makerEnterpriseEntity.setRelationshipType(1);
            makerEnterpriseEntity.setRelDate(new Date());
            makerEnterpriseEntity.setRelType(RelType.MAKERREL);
            makerEnterpriseEntity.setCooperationStartTime(new Date());
            makerEnterpriseEntity.setRelMemo("关注");
        }
        if(attribute == 1){
            makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATESTOP);

        }
        if(attribute == 2){
            makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
        }
        boolean b = saveOrUpdate(makerEnterpriseEntity);
        System.out.println(b);

        return R.success("成功");
    }
}
