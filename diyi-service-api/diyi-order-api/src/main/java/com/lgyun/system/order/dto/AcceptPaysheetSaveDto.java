package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.AcceptPaysheetType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 上传交付支付验收单
 * @author jun.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class AcceptPaysheetSaveDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单ID
     */
    @NotNull(message = "请选择支付清单")
    private Long payEnterpriseId;

    /**
     * 交付支付验收单类型：清单式，单人单张
     */
    @NotNull(message = "请选择交付支付验收单类型")
    private AcceptPaysheetType acceptPaysheetType;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 验收单URL
     */
    @NotBlank(message = "请上传交付支付验收单")
    private String acceptPaysheetUrl;

}
