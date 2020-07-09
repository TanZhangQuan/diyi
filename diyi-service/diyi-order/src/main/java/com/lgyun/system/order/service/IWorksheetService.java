package com.lgyun.system.order.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.system.order.dto.ReleaseWorksheetDTO;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.vo.WorksheetXiaoVo;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
public interface IWorksheetService extends IService<WorksheetEntity> {

    /**
     * 发布工单
     */
    R releaseWorksheet(ReleaseWorksheetDTO releaseWorksheetDTO);


    /**
     * 抢单
     */
    R orderGrabbing(Long worksheetId,Long makerId);

    /**
     * 小程序查询工单
     */
    R<IPage<WorksheetXiaoVo>> findXiaoPage(IPage<WorksheetXiaoVo> page, Integer worksheetState);
}

