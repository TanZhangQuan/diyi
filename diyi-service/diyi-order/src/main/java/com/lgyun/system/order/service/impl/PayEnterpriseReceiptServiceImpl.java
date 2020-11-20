package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import com.lgyun.system.order.mapper.PayEnterpriseReceiptMapper;
import com.lgyun.system.order.service.IPayEnterpriseReceiptService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-17 20:01:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayEnterpriseReceiptServiceImpl extends BaseServiceImpl<PayEnterpriseReceiptMapper, PayEnterpriseReceiptEntity> implements IPayEnterpriseReceiptService {

    @Override
    public String findEnterprisePayReceiptUrl(Long payEnterpriseId) {
        QueryWrapper<PayEnterpriseReceiptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayEnterpriseReceiptEntity::getPayEnterpriseId, payEnterpriseId);
        List<PayEnterpriseReceiptEntity> payEnterpriseReceiptEntities = baseMapper.selectList(queryWrapper);
        if (null == payEnterpriseReceiptEntities || payEnterpriseReceiptEntities.size() <= 0) {
            return "";
        }
        StringBuilder enterprisePayReceiptUrls = new StringBuilder();
        for (PayEnterpriseReceiptEntity payEnterpriseReceiptEntity : payEnterpriseReceiptEntities) {
            enterprisePayReceiptUrls.append(payEnterpriseReceiptEntity.getEnterprisePayReceiptUrl()).append(",");
        }
        return enterprisePayReceiptUrls.toString();
    }

    @Override
    public void deleteByPayEnterpriseId(Long payEnterpriseId) {
        baseMapper.deleteByPayEnterpriseId(payEnterpriseId);
    }

}
