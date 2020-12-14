package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.ConfirmPaymentDTO;
import com.lgyun.system.order.entity.SelfHelpInvoiceFeeEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceFeeMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceFeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceFeeServiceImpl extends BaseServiceImpl<SelfHelpInvoiceFeeMapper, SelfHelpInvoiceFeeEntity> implements ISelfHelpInvoiceFeeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> confirmPayment(ConfirmPaymentDTO confirmPaymentDto) {
        if(null == confirmPaymentDto.getHandPayId()){
            return R.fail("参数错误");
        }
        SelfHelpInvoiceFeeEntity selfHelpInvoiceFeeEntity = getById(confirmPaymentDto.getHandPayId());
        if(null == selfHelpInvoiceFeeEntity){
            return R.fail("没有此订单");
        }
        selfHelpInvoiceFeeEntity.setPayDesc(confirmPaymentDto.getPayDesc());
        selfHelpInvoiceFeeEntity.setPayCertificate(confirmPaymentDto.getPayCertificate());
        selfHelpInvoiceFeeEntity.setPayType(confirmPaymentDto.getPaymentType());
        updateById(selfHelpInvoiceFeeEntity);
        return R.success("确认支付成功");
    }

    @Override
    public SelfHelpInvoiceFeeEntity findBySelfHelpInvoiceId(Long selfHelpInvoiceId) {
        QueryWrapper<SelfHelpInvoiceFeeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceFeeEntity::getSelfHelpInvoiceId, selfHelpInvoiceId);
        return baseMapper.selectOne(queryWrapper);
    }
}
