package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AcceptSheetAndCsListDTO;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDTO;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.vo.PayEnterpriseMakerDetailListVO;
import com.lgyun.system.order.vo.enterprise.AcceptPaysheetListEnterpriseVO;
import com.lgyun.system.order.vo.enterprise.AcceptPaysheetDetailEnterpriseVO;
import com.lgyun.system.order.vo.enterprise.AcceptPaysheetSingleListEnterpriseVO;
import com.lgyun.system.order.vo.maker.AcceptPaysheetAndCsListMakerVO;
import com.lgyun.system.order.vo.maker.AcceptPaysheetDetailMakerVO;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
public interface IAcceptPaysheetService extends BaseService<AcceptPaysheetEntity> {

    /**
     * 查询总包+分包交付支付验收单
     *
     * @param enterpriseId
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<AcceptPaysheetAndCsListMakerVO>> queryTotalSubAcceptPaysheetListMaker(Long enterpriseId, Long makerId, IPage<AcceptPaysheetAndCsListMakerVO> page);

    /**
     * 查询总包+分包交付支付验收单详情
     *
     * @param makerId
     * @param acceptPaysheetId
     * @return
     */
    R<AcceptPaysheetDetailMakerVO> queryTotalSubAcceptPaysheetDetailMaker(Long makerId, Long acceptPaysheetId);

    /**
     * 上传交付支付验收单
     *
     * @param acceptPaysheetSaveDto
     * @param enterpriseId
     * @param uploadSource
     * @param uploadPerson
     * @return
     */
    R<String> upload(AcceptPaysheetSaveDTO acceptPaysheetSaveDto, Long enterpriseId, String uploadSource, String uploadPerson);

    /**
     * 查询当前商户所有总包+分包交付支付验收单
     *
     * @param enterpriseId
     * @param acceptSheetAndCsListDto
     * @param page
     * @return
     */
    R<IPage<AcceptPaysheetListEnterpriseVO>> queryTotalSubAcceptPaysheetListEnterprise(Long enterpriseId, AcceptSheetAndCsListDTO acceptSheetAndCsListDto, IPage<AcceptPaysheetListEnterpriseVO> page);

    /**
     * 查询当前商户所有总包+分包交付支付验收单明细
     *
     * @param acceptPaysheetId
     * @return
     */
    R<AcceptPaysheetDetailEnterpriseVO> queryTotalSubAcceptPaysheetDetailEnterprise(Long acceptPaysheetId);

    /**
     * 查询单人单张的总包+分包交付支付验收单
     *
     * @param payEnterpriseId
     * @param payMakerId
     * @param page
     * @return
     */
    R<IPage<AcceptPaysheetSingleListEnterpriseVO>> queryTotalSubAcceptPaysheetSingleList(Long payEnterpriseId, Long payMakerId, IPage<AcceptPaysheetSingleListEnterpriseVO> page);

    /**
     * 根据总包总包交付支付验收单ID查询关联创客
     *
     * @param acceptPaysheetId
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseMakerDetailListVO>> getMakerList(Long acceptPaysheetId, IPage<PayEnterpriseMakerDetailListVO> page);

    /**
     * 根据支付清单ID, 创客ID查询交付支付验收单
     *
     * @param payEnterpriseId
     * @param makerId
     * @return
     */
    AcceptPaysheetEntity getAcceptPaysheet(Long payEnterpriseId, Long makerId);

}

