package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.common.enumeration.PayMakerPayState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.entity.PayMakerReceiptEntity;
import com.lgyun.system.order.mapper.PayMakerReceiptMapper;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IPayMakerReceiptService;
import com.lgyun.system.order.service.IPayMakerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-04 14:20:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayMakerReceiptServiceImpl extends BaseServiceImpl<PayMakerReceiptMapper, PayMakerReceiptEntity> implements IPayMakerReceiptService {

    private IPayMakerService payMakerService;
    private IPayEnterpriseService payEnterpriseService;

    @Override
    public void deleteByPayMakerId(Long payMakerId) {
        baseMapper.deleteByPayMakerId(payMakerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> uploadPayMakerReceipt(Long serviceProviderId, Long payMakerId, String makerPayReceiptUrls) {

        PayMakerEntity payMakerEntity = payMakerService.getById(payMakerId);
        if (payMakerEntity == null){
            return R.fail("支付明细不存在");
        }

        PayEnterpriseEntity payEnterpriseEntity = payEnterpriseService.getById(payMakerEntity.getPayEnterpriseId());
        if (payEnterpriseEntity == null){
            return R.fail("支付清单不存在");
        }

        if (!(payEnterpriseEntity.getServiceProviderId().equals(serviceProviderId))){
            return R.fail("支付清单不属于服务商");
        }

        if (!(PayEnterpriseAuditState.APPROVED.equals(payEnterpriseEntity.getAuditState()))){
            return R.fail("支付清单未审核通过，不可上传支付回单");
        }

        //删除旧的分包支付回单
        deleteByPayMakerId(payMakerId);

        //支付回单拆分
        String[] split = makerPayReceiptUrls.split(",");
        for (int i = 0; i < split.length; i++) {
            if (StringUtils.isNotBlank(split[i])) {
                PayMakerReceiptEntity payMakerReceiptEntity = new PayMakerReceiptEntity();
                payMakerReceiptEntity.setPayMakerId(payMakerId);
                payMakerReceiptEntity.setMakerPayReceiptUrl(split[i]);
                save(payMakerReceiptEntity);
            }
        }

        //编辑支付明细的支付状态
        if (!(PayMakerPayState.PLATFORMPAID.equals(payEnterpriseEntity.getPayState()))){
            payMakerEntity.setPayState(PayMakerPayState.PLATFORMPAID);
            payMakerService.save(payMakerEntity);
        }

        return R.success("上传支付回单成功");
    }

}
