package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.WorksheetMakerVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
public class WorksheetMakerWrapper extends BaseEntityWrapper<WorksheetMakerEntity, WorksheetMakerVO> {

    public static WorksheetMakerWrapper build() {
        return new WorksheetMakerWrapper();
    }

	@Override
	public WorksheetMakerVO entityVO(WorksheetMakerEntity worksheetMaker) {

		if (worksheetMaker == null){
			return null;
		}

		return BeanUtil.copy(worksheetMaker, WorksheetMakerVO.class);
	}

}
