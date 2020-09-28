package com.lgyun.system.order.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 交付支付验收单DTO
 *
 * @author tzq.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class AcceptPayListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 交付支付验收单ID
     */
    private Long acceptPayId;

    /**
     * 创客名称
     */
    private String makerName;

    /**
     * 交付支付验收单开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    /**
     * 交付支付验收单结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
