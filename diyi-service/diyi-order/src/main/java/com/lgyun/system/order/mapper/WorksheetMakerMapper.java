package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.WorksheetMakerDetailsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Mapper
public interface WorksheetMakerMapper extends BaseMapper<WorksheetMakerEntity> {

    /**
     * 根据工单id查询所有的创客明细
     *
     * @param worksheetId
     * @param page
     * @return
     */
    List<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId,IPage<WorksheetMakerDetailsVO> page);


    /**
     * 根据工单id查询所有的创客明细
     *
     * @param worksheetId
     * @return
     */
    List<WorksheetMakerDetailsVO> getWorksheetMakerDetailList(Long worksheetId);
}

