package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.PayEnterpriseMakerListDto;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.vo.PayEnterpriseMakersListVO;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public interface IPayMakerService extends BaseService<PayMakerEntity> {

    /**
     * 查询当前商户所有分包支付清单
     *
     * @param enterpriseId
     * @param payEnterpriseMakerListDto
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseMakersListVO>> getPayMakersByEnterprise(Long enterpriseId, PayEnterpriseMakerListDto payEnterpriseMakerListDto, IPage<PayEnterpriseMakersListVO> page);
}

