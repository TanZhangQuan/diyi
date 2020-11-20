package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AcceptSheetAndCsListDTO;
import com.lgyun.system.order.entity.AcceptPaysheetCsEntity;
import com.lgyun.system.order.vo.*;

/**
 * 众包交付支付验收单表 Service 接口
 *
 * @author tzq
 * @since 2020-08-05 10:43:29
 */
public interface IAcceptPaysheetCsService extends BaseService<AcceptPaysheetCsEntity> {

    /**
     * 查询众包交付支付验收单
     *
     * @param enterpriseId
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<AcceptPaysheetAndCsListMakerVO>> queryCrowdAcceptPaysheetListMaker(Long enterpriseId, Long makerId, IPage<AcceptPaysheetAndCsListMakerVO> page);

    /**
     * 查询众包交付支付验收单详情
     *
     * @param makerId
     * @param acceptPaysheetId
     * @return
     */
    R<AcceptPaysheetCsDetailMakerVO> queryCrowdAcceptPaysheetDetailMaker(Long makerId, Long acceptPaysheetId);

    /**
     * 查询当前商户所有众包交付支付验收单
     *
     * @param enterpriseId
     * @param acceptSheetAndCsListDto
     * @param page
     * @return
     */
    R<IPage<AcceptPaysheetCsListEnterpriseVO>> queryCrowdAcceptPaysheetListEnterprise(Long enterpriseId, AcceptSheetAndCsListDTO acceptSheetAndCsListDto, IPage<AcceptPaysheetCsListEnterpriseVO> page);

    /**
     * 查询众包交付支付验收单详情
     *
     * @param acceptPaysheetCsId
     * @return
     */
    R<AcceptPaysheetCsDetailEnterpriseVO> queryCrowdAcceptPaysheetDetailEnterprise(Long acceptPaysheetCsId);

}

