package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDTO;
import com.lgyun.system.order.dto.AcceptSheetAndCsListDTO;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.vo.*;

/**
 * Service 接口
 *
 * @author tzq
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
     * @param enterpriseId
     * @param serviceProviderId
     * @param acceptPaysheetSaveDto
     * @param uploadSource
     * @param uploadPerson
     * @return
     */
    R<String> uploadAcceptPaysheet(Long enterpriseId, Long serviceProviderId, AcceptPaysheetSaveDTO acceptPaysheetSaveDto, String uploadSource, String uploadPerson);

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
     * 查询当前商户所有总包+分包交付支付验收单详情
     *
     * @param acceptPaysheetId
     * @return
     */
    R<AcceptPaysheetDetailEnterpriseVO> queryTotalSubAcceptPaysheetDetailEnterprise(Long acceptPaysheetId);

    /**
     * 查询分包支付明细是否已开交付支付验收单
     *
     * @param payMakerId
     * @return
     */
    boolean isAcceptPaysheet(Long payMakerId);

    /**
     * 删除交付支付验收单
     *
     * @param payEnterpriseId
     */
    void deleteAcceptPaysheet(Long payEnterpriseId);

    /**
     * 跟支付清单查询所有的支付交付验收单
     */
    String findPayEnterpriseAll(String payEnterpriseIds);

}

