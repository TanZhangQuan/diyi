package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.vo.WorksheetVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
public class WorksheetWrapper extends BaseEntityWrapper<WorksheetEntity, WorksheetVO> {

    public static WorksheetWrapper build() {
        return new WorksheetWrapper();
    }

	@Override
	public WorksheetVO entityVO(WorksheetEntity worksheet) {

		if (worksheet == null){
			return null;
		}

		return BeanUtil.copy(worksheet, WorksheetVO.class);
	}

}
