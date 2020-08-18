package com.lgyun.system.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tzq
 * @Description 个体户列表dto
 * @return
 * @date 2020.06.27
 */
@Data
public class IndividualBusinessListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //创客ID
    private Long makerId;

}
