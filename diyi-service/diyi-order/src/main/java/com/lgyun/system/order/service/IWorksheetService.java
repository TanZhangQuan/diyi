package com.lgyun.system.order.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.ReleaseWorksheetDto;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.vo.WorksheetXiaoVo;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
public interface IWorksheetService extends BaseService<WorksheetEntity> {

    /**
     * 发布工单
     */
    R releaseWorksheet(ReleaseWorksheetDto releaseWorksheetDTO);


    /**
     * 抢单
     */
    R orderGrabbing(Long worksheetId,Long makerId);

    /**
     * 小程序查询工单
     */
    R<IPage<WorksheetXiaoVo>> findXiaoPage(IPage<WorksheetXiaoVo> page, Integer worksheetState);
}

