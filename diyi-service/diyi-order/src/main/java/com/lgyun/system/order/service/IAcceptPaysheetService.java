package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AcceptPayListDto;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.vo.AcceptPayListVO;
import com.lgyun.system.order.vo.AcceptPayMakerListVO;
import com.lgyun.system.order.vo.AcceptPaysheetByEnterpriseListVO;
import com.lgyun.system.order.vo.AcceptPaysheetWorksheetVO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
public interface IAcceptPaysheetService extends BaseService<AcceptPaysheetEntity> {

    /**
     * 查询创客对应某商户的所有交付支付验收单
     *
     * @param page
     * @param enterpriseId
     * @param makerId
     * @return
     */
    R<IPage<AcceptPaysheetByEnterpriseListVO>> getAcceptPaysheetsByEnterprise(IPage<AcceptPaysheetByEnterpriseListVO> page, Long enterpriseId, Long makerId);

    /**
     * 根据ID查询交付支付验收单
     *
     * @param makerId
     * @param acceptPaysheetId
     * @return
     */
    R<AcceptPaysheetWorksheetVO> getAcceptPaysheetWorksheet(Long makerId, Long acceptPaysheetId);

    /**
     * 查询创客所有交付支付验收单的商户
     *
     * @param page
     * @param makerId
     * @return
     */
    R<IPage<EnterprisesIdNameListVO>> getEnterprisesByWorksheet(IPage<EnterprisesIdNameListVO> page, Long makerId);

    /**
     * 上传交付支付验收单
     *
     * @param acceptPaysheetSaveDto
     * @param enterpriseWorkerEntity
     * @return
     */
    R<String> upload(AcceptPaysheetSaveDto acceptPaysheetSaveDto, EnterpriseWorkerEntity enterpriseWorkerEntity);

    /**
     * 查询当前商户所有总包交付支付验收单
     *
     * @param enterpriseId
     * @param acceptPayListDto
     * @param page
     * @return
     */
    R<IPage<AcceptPayListVO>> getAcceptPaySheetsByEnterprise(Long enterpriseId, AcceptPayListDto acceptPayListDto, IPage<AcceptPayListVO> page);

    /**
     * 根据总包总包交付支付验收单ID查询关联创客
     *
     * @param acceptPaysheetId
     * @param page
     * @return
     */
    R<IPage<AcceptPayMakerListVO>> getMakerList(Long acceptPaysheetId, IPage<AcceptPayMakerListVO> page);

    /**
     * 根据支付清单ID, 创客ID查询交付支付验收单
     *
     * @param payEnterpriseId
     * @param makerId
     * @return
     */
    AcceptPaysheetEntity getAcceptPaysheet(Long payEnterpriseId, Long makerId);

}

