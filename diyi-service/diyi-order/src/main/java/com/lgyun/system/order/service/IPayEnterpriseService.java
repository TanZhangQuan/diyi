package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.PayEnterpriseTotalListDto;
import com.lgyun.system.order.dto.PayEnterpriseUploadDto;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.InvoiceEnterpriseVO;
import com.lgyun.system.order.vo.PayEnterpriseStatisticalVO;
import com.lgyun.system.order.vo.PayEnterpriseTotalListVO;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
public interface IPayEnterpriseService extends BaseService<PayEnterpriseEntity> {

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseAll(Long makerId,IPage<InvoiceEnterpriseVO> page);


    /**
     * 根据创客id和商户id查询创客在商户下所开的票
     *
     * @param makerId
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseMakerIdAll(Long makerId,Long enterpriseId,IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id,商户id和创客支付id查询票的详情
     *
     * @param makerId
     * @param enterpriseId
     * @param payMakerId
     * @return
     */
    R<InvoiceEnterpriseVO> getEnterpriseMakerIdDetail(Long makerId,Long enterpriseId,Long payMakerId);

    /**
     * 获取交付清单统计数据
     *
     * @param enterpriseId
     * @return
     */
    R<PayEnterpriseStatisticalVO> statistical(Long enterpriseId);

    /**
     * 上传支付清单
     *
     * @param payEnterpriseUploadDto
     * @param enterpriseId
     * @return
     */
    R<String> upload(PayEnterpriseUploadDto payEnterpriseUploadDto, Long enterpriseId);

    /**
     * 查询当前商户所有总包支付清单
     *
     * @param enterpriseId
     * @param payEnterpriseTotalListDto
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseTotalListVO>> getByDtoEnterprise(Long enterpriseId, PayEnterpriseTotalListDto payEnterpriseTotalListDto, IPage<PayEnterpriseTotalListVO> page);
}

