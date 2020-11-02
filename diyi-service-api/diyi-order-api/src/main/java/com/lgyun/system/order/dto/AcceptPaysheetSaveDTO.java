package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.AcceptPaysheetType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 上传交付支付验收单
 *
 * @author tzq
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class AcceptPaysheetSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单ID
     */
    @NotNull(message = "请选择支付清单")
    private Long payEnterpriseId;

    /**
     * 清单式创客支付明细ID集合
     */
    private List<Long> payMakerIdList;

    /**
     * 单人单张创客支付明细ID
     */
    private Long payMakerId;

    /**
     * 交付支付验收单类型：清单式，单人单张
     */
    @NotNull(message = "请选择交付支付验收单类型")
    private AcceptPaysheetType acceptPaysheetType;

    /**
     * 交付支付验收单服务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请选择服务开始时间")
    private Date serviceTimeStart;

    /**
     * 交付支付验收单服务结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请选择服务结束时间")
    private Date serviceTimeEnd;

    /**
     * 验收单URL
     */
    @NotBlank(message = "请上传交付支付验收单")
    private String acceptPaysheetUrl;

}
