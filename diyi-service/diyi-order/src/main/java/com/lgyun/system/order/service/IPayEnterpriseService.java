package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.InvoiceEnterpriseVO;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
public interface IPayEnterpriseService extends BaseService<PayEnterpriseEntity> {

    /**
     * 根据创客id查询所有商户
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseAll(Long makerId,IPage<InvoiceEnterpriseVO> page);


    /**
     * 根据创客id和商户id查询创客在商户下所开的票
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseMakerIdAll(Long makerId,Long enterpriseId,IPage<InvoiceEnterpriseVO> page);

    /**
     *  根据创客id,商户id和创客支付id查询票的详情
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseMakerIdDetail(Long makerId,Long enterpriseId,Long payMakerId,IPage<InvoiceEnterpriseVO> page);
}
