package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.enumeration.WorksheetState;
import lombok.Data;

import java.io.Serializable;

/**
 * 工单列表DTO
 *
 * @author tzq
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class WorksheetFinishedListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单编号
     */
    private String worksheetNo;

    /**
     * 工单名称
     */
    private String worksheetName;

    /**
     * 工单类型
     */
    private WorkSheetType workSheetType;

    /**
     * 创客类型
     */
    private MakerType makerType;

    /**
     * 工单状态
     */
    private WorksheetState worksheetState;

}
