package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.vo.AcceptPaysheetByEnterpriseListVO;
import com.lgyun.system.order.vo.AcceptPaysheetWorksheetVO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.vo.EnterprisesByWorksheetListVO;

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
    R<IPage<EnterprisesByWorksheetListVO>> getEnterprisesByWorksheet(IPage<EnterprisesByWorksheetListVO> page, Long makerId);

    /**
     * 上传交付支付验收单
     *
     * @param acceptPaysheetSaveDto
     * @param enterpriseWorkerEntity
     * @return
     */
    R<String> upload(AcceptPaysheetSaveDto acceptPaysheetSaveDto, EnterpriseWorkerEntity enterpriseWorkerEntity);
}

