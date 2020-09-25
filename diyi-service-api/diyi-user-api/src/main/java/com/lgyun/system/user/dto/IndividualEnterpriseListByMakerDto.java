package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.Ibstate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tzq
 * @Description 查询创客的个体户DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class IndividualEnterpriseListByMakerDto {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    @ApiModelProperty(hidden = true)
    private Long makerId;

    /**
     * 个体工商户状态
     */
    private Ibstate ibstate;

}
