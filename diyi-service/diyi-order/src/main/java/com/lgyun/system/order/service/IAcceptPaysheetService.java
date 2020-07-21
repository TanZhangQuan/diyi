package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.vo.AcceptPaysheetByEnterpriseListVO;
import com.lgyun.system.order.vo.AcceptPaysheetWorksheetVO;
import com.lgyun.system.user.vo.EnterprisesByWorksheetListVO;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
public interface IAcceptPaysheetService extends BaseService<AcceptPaysheetEntity> {

    R<IPage<AcceptPaysheetByEnterpriseListVO>> getAcceptPaysheetsByEnterprise(IPage<AcceptPaysheetByEnterpriseListVO> page, Long enterpriseId, Long makerId);

    R<AcceptPaysheetWorksheetVO> getAcceptPaysheetWorksheet(Long makerId, Long acceptPaysheetId);

    /**
     * 查询创客所有交付支付验收单的商户
     */
    R<IPage<EnterprisesByWorksheetListVO>> getEnterprisesByWorksheet(IPage<EnterprisesByWorksheetListVO> page, Long makerId);

}

