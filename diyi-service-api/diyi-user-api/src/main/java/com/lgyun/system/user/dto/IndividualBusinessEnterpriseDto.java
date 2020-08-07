package com.lgyun.system.user.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @Description 个体户或个独查询dto
 * @return
 * @date 2020.06.27
 */
@Data
public class IndividualBusinessEnterpriseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //个体户或个独编号
    private Long individualBusinessEnterpriseId;

    //个体户或个独名称
    private String ibname;

    //个体户或个独或个独注册开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    //个体户或个独注册结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
