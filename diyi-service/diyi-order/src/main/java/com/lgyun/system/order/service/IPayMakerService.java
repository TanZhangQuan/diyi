package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.PayEnterpriseMakerListDto;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.excel.PayEnterpriseExcel;
import com.lgyun.system.order.vo.PayEnterpriseMakersListVO;
import com.lgyun.system.order.vo.PayMakerVO;

import java.util.List;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public interface IPayMakerService extends BaseService<PayMakerEntity> {

    /**
     * 根据条件查询所有分包
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param payEnterpriseMakerListDto
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseMakersListVO>> getPayMakersByEnterprise(Long enterpriseId, Long serviceProviderId, PayEnterpriseMakerListDto payEnterpriseMakerListDto, IPage<PayEnterpriseMakersListVO> page);

    /**
     * 上传分包支付清单
     *
     * @param list
     * @param payEnterpriseEntity
     * @param payReceiptUrl
     */
    void importMaker(List<PayEnterpriseExcel> list, PayEnterpriseEntity payEnterpriseEntity, String payReceiptUrl);

    /**
     * 根据商户支付清单id查询创客支付明细
     */
    List<PayMakerVO> getPayEnterpriseId(Long payEnterpriseId);

    /**
     * 根据商户支付清单id查询创客支付明细和门征发票信息
     */
    List<PayMakerVO> getPayEnterprise(Long payEnterpriseId);
}

