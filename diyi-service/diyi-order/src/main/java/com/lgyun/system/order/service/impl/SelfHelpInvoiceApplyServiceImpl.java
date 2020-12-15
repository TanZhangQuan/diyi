package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import com.lgyun.system.order.entity.MakerTaxRecordEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.SelfHelpInvoiceApplyMapper;
import com.lgyun.system.order.entity.SelfHelpInvoiceApplyEntity;
import com.lgyun.system.order.service.ISelfHelpInvoiceApplyService;

import java.util.List;

/**
 * 自助开票申请：记录自助开票主表的申请记录情况 Service 实现
 *
 * @author tzq
 * @since 2020-08-08 10:36:37
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceApplyServiceImpl extends BaseServiceImpl<SelfHelpInvoiceApplyMapper, SelfHelpInvoiceApplyEntity> implements ISelfHelpInvoiceApplyService {

    @Override
    public SelfHelpInvoiceApplyEntity getBySelfHelpInvoiceId(Long selfHelpInvoiceId) {
        QueryWrapper<SelfHelpInvoiceApplyEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceApplyEntity::getSelfHelpInvoiceId,selfHelpInvoiceId)
                .eq(SelfHelpInvoiceApplyEntity::getApplyState,SelfHelpInvoiceApplyState.AUDITING);
        List<SelfHelpInvoiceApplyEntity> selfHelpInvoiceApplyEntities = baseMapper.selectList(queryWrapper);
        if(null == selfHelpInvoiceApplyEntities || selfHelpInvoiceApplyEntities.size() <= 0){
            return null;
        }
        return selfHelpInvoiceApplyEntities.get(0);
    }
}
