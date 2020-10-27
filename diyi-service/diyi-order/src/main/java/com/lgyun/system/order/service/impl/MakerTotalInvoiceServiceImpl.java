package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.MakerTotalInvoiceEntity;
import com.lgyun.system.order.mapper.MakerTotalInvoiceMapper;
import com.lgyun.system.order.service.IMakerTotalInvoiceService;
import com.lgyun.system.order.vo.MakerTotalInvoiceVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 20:49:12
 */
@Slf4j
@Service
@AllArgsConstructor
public class MakerTotalInvoiceServiceImpl extends BaseServiceImpl<MakerTotalInvoiceMapper, MakerTotalInvoiceEntity> implements IMakerTotalInvoiceService {

    @Override
    public MakerTotalInvoiceVO getPayEnterpriseId(Long payEnterpriseId) {
        QueryWrapper<MakerTotalInvoiceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerTotalInvoiceEntity::getPayEnterpriseId,payEnterpriseId);
        List<MakerTotalInvoiceEntity> makerTotalInvoiceEntitie = baseMapper.selectList(queryWrapper);
        List<MakerTotalInvoiceVO> payMakerVOList = new ArrayList<>();
        for (MakerTotalInvoiceEntity makerTotalInvoiceEntity: makerTotalInvoiceEntitie) {
            payMakerVOList.add(BeanUtil.copy(makerTotalInvoiceEntity, MakerTotalInvoiceVO.class));
        }
        if(payMakerVOList.size() > 0){
            return payMakerVOList.get(0);
        }
        return new MakerTotalInvoiceVO();
    }

    @Override
    public MakerTotalInvoiceEntity findPayEnterpriseId(Long payEnterpriseId) {
        QueryWrapper<MakerTotalInvoiceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerTotalInvoiceEntity::getPayEnterpriseId,payEnterpriseId);
        List<MakerTotalInvoiceEntity> makerTotalInvoiceEntitie = baseMapper.selectList(queryWrapper);
        if(makerTotalInvoiceEntitie.size() < 0){
            return null;
        }
        return makerTotalInvoiceEntitie.get(0);
    }

}
