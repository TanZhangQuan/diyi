package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.WorksheetMakerDetailsVO;
import com.lgyun.system.order.vo.WorksheetMakerListVO;

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
     * 根据工单id查询抢单条数
     *
     * @param worksheetId
     * @return
     */
    int getWorksheetCount(Long worksheetId);

    /**
     * 提交工作成果
     *
     * @param worksheetMakerEntity
     * @param achievementDesc
     * @param achievementFiles
     * @return
     */
    R<String> submitAchievement(WorksheetMakerEntity worksheetMakerEntity, String achievementDesc, String achievementFiles,IWorksheetService worksheetService);

    /**
     * 验收工作成果
     *
     * @param worksheetMakerId
     * @param checkMoney
     * @param enterpriseId
     * @param bool
     * @return
     */
    R<String> checkAchievement(Long worksheetMakerId, BigDecimal checkMoney, Long enterpriseId, Boolean bool);

    /**
     * 查询创客有没有抢单
     *
     * @param makerId
     * @param worksheetId
     * @return
     */
    Boolean isMakerId(Long makerId, Long worksheetId);

    /**
     * 根据工单id查询所有的创客明细
     *
     * @param worksheetId
     * @param page
     * @return
     */
    IPage<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId, IPage<WorksheetMakerDetailsVO> page);


    /**
     * 查询创客有没有抢单
     *
     * @param makerId
     * @param worksheetId
     * @return
     */
    WorksheetMakerEntity getmakerIdAndWorksheetId(Long makerId, Long worksheetId);

    /**
     * 根据支付清单ID查询创客工单关联
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    R<IPage<WorksheetMakerListVO>> getByPayEnterpriseId(Long payEnterpriseId, IPage<WorksheetMakerListVO> page);


    /**
     * 根据工单id查询所有的创客明细
     *
     * @param worksheetId
     * @return
     */
    List<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId);
}

