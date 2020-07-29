package com.lgyun.system.order.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.ReleaseWorksheetDto;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.vo.EnterpriseWorksheetDetailVo;
import com.lgyun.system.order.vo.WorksheetMakerDetailsVO;
import com.lgyun.system.order.vo.WorksheetXiaoVo;

import java.math.BigDecimal;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
public interface IWorksheetService extends BaseService<WorksheetEntity> {

    /**
     * 发布工单
     *
     * @param releaseWorksheetDTO
     * @return
     */
    R<String> releaseWorksheet(ReleaseWorksheetDto releaseWorksheetDTO);


    /**
     * 抢单
     *
     * @param worksheetId
     * @param makerId
     * @return
     */
    R orderGrabbing(Long worksheetId,Long makerId);

    /**
     * 小程序查询工单
     *
     * @param page
     * @param worksheetState
     * @param makerId
     * @return
     */
    R<IPage<WorksheetXiaoVo>> findXiaoPage(IPage<WorksheetXiaoVo> page, Integer worksheetState,Long makerId);

    /**
     * 查询详情
     *
     * @param worksheetMakerId
     * @return
     */
    R<WorksheetXiaoVo> getWorksheetDetails(Long worksheetMakerId);


    /**
     *根据工单状态和商户id查询
     */
    R getEnterpriseWorksheet(IPage<WorksheetXiaoVo> page, Long enterpriseId, WorksheetState worksheetState,String worksheetNo,String worksheetName,String startTime,String endTime);


    /**
     * 后台查询订单详情
     */
    R getWorksheetWebDetails(IPage<WorksheetMakerDetailsVO> page, Long worksheetId);

    /**
     * 开启或关闭
     */
    R closeOrOpen(Long worksheetId,Integer variable);

    /**
     * 踢出创客
     */
    R kickOut(Long worksheetId,Long makerId);

    /**
     * 验收
     */
    R checkAccept(Long worksheetMakerId, BigDecimal checkMoney);

    /**
     * 根据创客查询工单详情
     *
     * @param page
     * @param enterpriseId
     * @param makerId
     * @return
     */
    R<IPage<EnterpriseWorksheetDetailVo>> getWorksheetDetailsByMaker(IPage<EnterpriseWorksheetDetailVo> page, Long enterpriseId, Long makerId);
}

