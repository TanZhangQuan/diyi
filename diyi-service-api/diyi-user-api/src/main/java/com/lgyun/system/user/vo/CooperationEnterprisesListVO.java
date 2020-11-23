package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.CooperateType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author .
 * @date 2020/7/28.
 * @time 14:55.
 */
@Data
public class CooperationEnterprisesListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 合作次数
     */
    private Integer cooperationNum;

    /**
     * 合作金额
     */
    private BigDecimal cooperationMoney;

    /**
     * 合作类型
     */
    private CooperateType cooperateType;

    /**
     * 合作状态
     */
    private CooperateStatus cooperateStatus;

    /**
     * 开始合作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
