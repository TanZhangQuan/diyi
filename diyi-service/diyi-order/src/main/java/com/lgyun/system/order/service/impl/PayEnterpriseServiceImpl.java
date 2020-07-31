package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.mapper.PayEnterpriseMapper;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.vo.InvoiceEnterpriseVO;
import com.lgyun.system.order.vo.PayEnterpriseStatisticalVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public R<IPage<InvoiceEnterpriseVO>> getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterpriseAll(makerId,page)));
    }

    @Override
    public R<IPage<InvoiceEnterpriseVO>> getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterpriseMakerIdAll(makerId,enterpriseId,page)));
    }

    @Override
    public R<InvoiceEnterpriseVO> getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId,Long payMakerId) {
        InvoiceEnterpriseVO enterpriseMakerIdDetail = baseMapper.getEnterpriseMakerIdDetail(makerId, enterpriseId, payMakerId);
        if(null != enterpriseMakerIdDetail.getMakerNum() && enterpriseMakerIdDetail.getMakerNum() > 1){
            return R.fail("抱歉，由于此发票人数过多，你没有权限观看");
        }
        return R.data(enterpriseMakerIdDetail);
    }

    @Override
    public R<PayEnterpriseStatisticalVO> statistical(Long enterpriseId) {
        return R.data(baseMapper.statistical(enterpriseId));
    }
}
