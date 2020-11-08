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
public class AcceptSheetAndCsListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客/开票人名称
     */
    private String name;

    /**
     * 交付支付验收单开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    /**
     * 交付支付验收单结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
