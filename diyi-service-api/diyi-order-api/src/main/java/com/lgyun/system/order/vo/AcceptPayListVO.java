package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "AcceptPayListVO对象", description = "AcceptPayListVO对象")
public class AcceptPayListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //交付支付清单编号
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //商户名称
    private String enterpriseName;

    //服务商名称
    private String serviceProviderName;

    //支付清单ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    //创客名称(多个逗号隔开)
    private String makerNames;

    //工单ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetId;

    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceTimeStart;

    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceTimeEnd;

    //验收单URL
    private String acceptPaysheetUrl;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
