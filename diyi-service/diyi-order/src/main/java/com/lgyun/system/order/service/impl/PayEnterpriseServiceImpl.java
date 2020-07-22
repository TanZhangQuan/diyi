package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.mapper.PayEnterpriseMapper;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.vo.InvoiceEnterpriseVO;
import com.lgyun.system.order.vo.WorksheetXiaoVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayEnterpriseServiceImpl extends BaseServiceImpl<PayEnterpriseMapper, PayEnterpriseEntity> implements IPayEnterpriseService {

    @Override
    public R getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterpriseAll(makerId,page)));
    }

    @Override
    public R getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterpriseMakerIdAll(makerId,enterpriseId,page)));
    }

    @Override
    public R getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId,Long payMakerId, IPage<InvoiceEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterpriseMakerIdDetail(makerId,enterpriseId,payMakerId,page)));
    }
}
