package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.WorksheetMakerDetailsVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
public interface IWorksheetMakerService extends BaseService<WorksheetMakerEntity> {

    /**
     * 提交工作成果
     *
     * @param worksheetMakerId
     * @param achievementDesc
     * @param achievementFiles
     * @return
     */
    R<String> submitAchievement(Long worksheetMakerId, String achievementDesc, String achievementFiles,IWorksheetService worksheetService);

    /**
     * 验收工作成果
     *
     * @param worksheetMakerId
     * @param checkMoney
     * @param enterpriseWorkerId
     * @param bool
     * @return
     */
    R<String> checkAchievement(Long worksheetMakerId, BigDecimal checkMoney, Long enterpriseWorkerId, Boolean bool);

    /**
     * 根据工单id查询所有的创客明细
     *
     * @param worksheetId
     * @param page
     * @return
     */
    IPage<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId, IPage<WorksheetMakerDetailsVO> page);

    /**
     * 根据工单id查询所有的创客明细
     *
     * @param worksheetId
     * @return
     */
    List<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId);

    /**
     * 查询工单已抢的数量
     */
    Integer getOrderGrabbingCount(Long worksheetId);
}

