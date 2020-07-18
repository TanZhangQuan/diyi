package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.WorksheetAttentionEntity;
import com.lgyun.system.order.vo.WorksheetAttentionVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public class WorksheetAttentionWrapper extends BaseEntityWrapper<WorksheetAttentionEntity, WorksheetAttentionVO> {

    public static WorksheetAttentionWrapper build() {
        return new WorksheetAttentionWrapper();
    }

    @Override
    public WorksheetAttentionVO entityVO(WorksheetAttentionEntity worksheetAttention) {
        WorksheetAttentionVO worksheetAttentionVO = BeanUtil.copy(worksheetAttention, WorksheetAttentionVO.class);
        return worksheetAttentionVO;
    }

}
