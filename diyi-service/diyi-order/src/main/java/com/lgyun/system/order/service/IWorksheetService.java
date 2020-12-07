package com.lgyun.system.order.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.ReleaseWorksheetDTO;
import com.lgyun.system.order.dto.WorksheetFinishedListDTO;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.vo.*;

/**
 * Service 接口
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
    R<String> releaseWorksheet(ReleaseWorksheetDTO releaseWorksheetDTO);

    /**
     * 抢单
     *
     * @param worksheetId
     * @param makerId
     * @return
     */
    R<String> orderGrabbing(Long worksheetId, Long makerId);

    /**
     * 小程序查询工单
     *
     * @param page
     * @param worksheetState
     * @param makerId
     * @return
     */
    R<IPage<WorksheetXiaoVO>> findXiaoPage(IPage<WorksheetXiaoVO> page, Integer worksheetState, Long makerId);

    /**
     * 查询详情
     *
     * @param worksheetMakerId
     * @return
     */
    R<WorksheetXiaoVO> getWorksheetDetails(Long worksheetMakerId);

    /**
     * 根据工单状态和商户id查询
     *
     * @param page
     * @param enterpriseId
     * @param worksheetState
     * @param worksheetNo
     * @param worksheetName
     * @param startTime
     * @param endTime
     * @return
     */
    R getEnterpriseWorksheet(IPage<WorksheetXiaoVO> page, Long enterpriseId, WorksheetState worksheetState, String worksheetNo, String worksheetName, String startTime, String endTime);

    /**
     * 后台查询订单详情
     *
     * @param page
     * @param worksheetId
     * @return
     */
    R getWorksheetWebDetails(IPage<WorksheetMakerDetailsVO> page, Long worksheetId);

    /**
     * 开启或关闭工单
     *
     * @param worksheetId
     * @param variable
     * @return
     */
    R closeOrOpen(Long worksheetId, Integer variable);

    /**
     * 踢出创客
     *
     * @param worksheetMakerId
     * @return
     */
    R kickOut(Long worksheetMakerId);

    /**
     * 根据创客查询工单详情
     *
     * @param enterpriseId
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<EnterpriseWorksheetDetailVO>> getWorksheetDetailsByMaker(Long enterpriseId, Long makerId, IPage<EnterpriseWorksheetDetailVO> page);

    /**
     * 查询当前商户所有已完毕的类型工单
     *
     * @param enterpriseId
     * @param worksheetFinishedListDTO
     * @param page
     * @return
     */
    R<IPage<WorksheetListVO>> queryWorksheetListByEnterprise(Long enterpriseId, WorksheetFinishedListDTO worksheetFinishedListDTO, IPage<WorksheetListVO> page);

    /**
     * 根据工单编号查询工单
     *
     * @param worksheetNo
     * @return
     */
    R<WorksheetNoIdVO> getByWorksheetNo(String worksheetNo);

    /**
     * 根据工单ID查询工单
     *
     * @param worksheetId
     * @return
     */
    R<WorksheetNoIdVO> getByWorksheetId(Long worksheetId);

    /**
     * 批量开启或关闭工单
     *
     * @param worksheetIds
     * @param variable
     * @return
     */
    R closeOrOpenList(String worksheetIds, Integer variable);

    /**
     * 批量删除工单
     *
     * @param worksheetIds
     * @return
     */
    R deleteWorksheetList(String worksheetIds);

    /**
     * 整体验收工单
     *
     * @param worksheetId
     * @return
     */
    R wholeWorksheetCheck(Long worksheetId);
}

