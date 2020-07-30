package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.Ibstate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @Description 个体户查询dto
 * @return
 * @date 2020.06.27
 */
@Data
public class EnterpriseIndividualBusinessDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //个体户状态：注册中，税务登记中，运营中，已注销
    @NotBlank(message = "请选择个体户状态")
    private Ibstate ibstate;

    //个体户编号
    private Long individualBusinessId;

    //个体户名称
    private String ibname;

    //个体户注册开始时间
    private Date beginDate;

    //个体户注册结束时间
    private Long endDate;

}
