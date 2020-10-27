package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.MakerTaxRecordEntity;
import com.lgyun.system.order.mapper.MakerTaxRecordMapper;
import com.lgyun.system.order.service.IMakerTaxRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class MakerTaxRecordServiceImpl extends BaseServiceImpl<MakerTaxRecordMapper, MakerTaxRecordEntity> implements IMakerTaxRecordService {

    @Override
    public MakerTaxRecordEntity findPayMakerId(Long payMakerId) {
        QueryWrapper<MakerTaxRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerTaxRecordEntity::getPayMakerId,payMakerId);
        List<MakerTaxRecordEntity> makerTaxRecordEntities = baseMapper.selectList(queryWrapper);
        if(makerTaxRecordEntities.size() < 0 ){
            return null;
        }
        return makerTaxRecordEntities.get(0);
    }
}
