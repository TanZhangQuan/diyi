package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.Ibstate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @Description 个体户或个独查询DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class IndividualBusinessEnterpriseListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 个体户或个独名称
     */
    private String ibname;

    /**
     * 个体户或个独状态
     */
    private Ibstate ibstate;

    /**
     * 个体户或个独或个独注册开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    /**
     * 个体户或个独注册结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
