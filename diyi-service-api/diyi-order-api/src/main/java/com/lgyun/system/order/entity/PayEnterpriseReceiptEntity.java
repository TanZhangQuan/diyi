package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Data
@NoArgsConstructor
@TableName("diyi_pay_enterprise_receipt")
public class PayEnterpriseReceiptEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 支付ID
     */
    private Long enterprisePayId;

    /**
     * 支付回单图片URL地址
     */
    private String enterprisePayReceiptUrl;

    /**
     * 上传日期时间
     */
    private Date uploadDateTime;

}
