package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.vo.EnterpriseWorksheetDetailVo;
import com.lgyun.system.order.vo.WorksheetXiaoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Mapper
public interface WorksheetMapper extends BaseMapper<WorksheetEntity> {
    /**
     * 小程序查询工单
     *
     * @param page
     * @param makerId
     * @return
     */
    List<WorksheetXiaoVo> findXiaoPage(IPage<WorksheetXiaoVo> page,Long makerId);

    /**
     * 小程序查询工单
     *
     * @param page
     * @param makerId
     * @return
     */
    List<WorksheetXiaoVo> findXiaoPage2(IPage<WorksheetXiaoVo> page, Long makerId);

    /**
     * 小程序查询工单
     *
     * @param page
     * @param makerId
     * @return
     */
    List<WorksheetXiaoVo> findXiaoPage3(IPage<WorksheetXiaoVo> page, Long makerId);

    /**
     * 查询详情
     *
     * @param worksheetMakerId
     * @return
     */
    WorksheetXiaoVo getWorksheetDetails(Long worksheetMakerId);

    /**
     * 根据创客ID查询工单
     *
     * @param enterpriseId
     * @param makerId
     * @param page
     * @return
     */
    List<EnterpriseWorksheetDetailVo> getWorksheetDetailsByMaker(Long enterpriseId, Long makerId, IPage<EnterpriseWorksheetDetailVo> page);
}

