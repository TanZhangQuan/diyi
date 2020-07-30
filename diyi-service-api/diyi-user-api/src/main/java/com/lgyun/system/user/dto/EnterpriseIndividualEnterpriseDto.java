package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.Ibstate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @Description 个独查询dto
 * @return
 * @date 2020.06.27
 */
@Data
public class EnterpriseIndividualEnterpriseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //个独状态：注册中，税务登记中，运营中，已注销
    @NotBlank(message = "请选择个独状态")
    private Ibstate ibstate;

    //个独编号
    private Long individualEnterpriseId;

    //个独名称
    private String ibname;

    //个独注册开始时间
    private Date beginDate;

    //个独注册结束时间
    private Long endDate;

}
