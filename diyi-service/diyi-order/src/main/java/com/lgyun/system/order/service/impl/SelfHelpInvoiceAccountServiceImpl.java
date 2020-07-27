package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.SelfHelpInvoiceAccountEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceAccountMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceAccountService;
import com.lgyun.system.order.vo.SelfHelpInvoiceAccountVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceAccountServiceImpl extends BaseServiceImpl<SelfHelpInvoiceAccountMapper, SelfHelpInvoiceAccountEntity> implements ISelfHelpInvoiceAccountService {

    @Override
    public SelfHelpInvoiceAccountVO immediatePayment() {

        QueryWrapper<SelfHelpInvoiceAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceAccountEntity::getIsDefault, 0)
                .eq(SelfHelpInvoiceAccountEntity::getStatus, 1);

        SelfHelpInvoiceAccountEntity selfHelpInvoiceAccountEntity = baseMapper.selectOne(queryWrapper);
        if (selfHelpInvoiceAccountEntity == null) {
            return null;
        }

        return BeanUtil.copy(selfHelpInvoiceAccountEntity, SelfHelpInvoiceAccountVO.class);
    }
}
