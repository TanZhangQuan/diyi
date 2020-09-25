package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tzq
 * @Description 个体户列表DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class IndividualEnterpriseListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    @NotNull(message = "请输入创客编号")
    private Long makerId;

}
