package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
     */
    List<WorksheetXiaoVo> findXiaoPage(IPage page, String worksheetState);

    /**
     * 小程序查询工单
     */
    List<WorksheetXiaoVo> findXiaoPage2(IPage page, String worksheetMakerState, Long makerId);

    /**
     * 小程序查询工单
     */
    List<WorksheetXiaoVo> findXiaoPage3(IPage page);
}

