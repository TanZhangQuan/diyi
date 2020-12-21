package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author .
 * @date 2020/12/16.
 * @time 9:40.
 */
@Data
public class ConfirmModificationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "请输入自助开票id")
    private Long selfHelpInvoiceId;

    private List<ModificationDTO> list;
}
