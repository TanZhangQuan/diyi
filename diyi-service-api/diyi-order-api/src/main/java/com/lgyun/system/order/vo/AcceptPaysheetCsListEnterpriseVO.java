package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
public class AcceptPaysheetCsListEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 众包交付支付验收单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 开票清单URL
     */
    private String listFile;

    /**
     * 创客/开票人名称(多个逗号隔开)
     */
    private String names;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceTimeStart;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceTimeEnd;

    /**
     * 验收单URL
     */
    private String acceptPaysheetCsUrl;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
