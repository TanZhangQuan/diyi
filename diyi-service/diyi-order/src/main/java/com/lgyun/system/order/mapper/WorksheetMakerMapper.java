package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.common.api.R;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Mapper
public interface WorksheetMakerMapper extends BaseMapper<WorksheetMakerEntity> {

    /**
     * 根据工单id查询抢单条数
     */
    int getWorksheetCount(Long worksheetId);

    /**
     * 查询创客有没有抢单
     */
    int isMakerId(Long makerId,Long worksheetId);

}

